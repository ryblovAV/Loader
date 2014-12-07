package org.loader.generator

import grizzled.slf4j.{Logging, Logger}
import org.loader.reader.OutReader
import org.springframework.context.support.ClassPathXmlApplicationContext


import org.loader.reader.JdbcTemplatesUtl._

case class TableInfo(name: String, pkColumn: String, columns: List[ColumnInfo], embeddedTables: List[String])

case class ColumnInfo(name: String, dataType: String, dataLength: Int, dataPrecision: Int, dataScale: Int, defaultValue: String, isPrimary: Boolean)

case class FieldInfo(name: String, fieldType: String, defaultValue: String, annotation: String, isPrimary: Boolean)

case class EntityInfo(table: TableInfo, isEmbeddable: Boolean, name: String, embeddedEntities: List[EntityInfo])


object HGenerator extends App with Logging {

  implicit class MyString(s: String) {
    def removeLast = s.substring(0,s.length-1)
  }

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")
  val jdbcReader: OutReader = ctx.getBean(classOf[OutReader])

  val entityRegExpr = """CI_{1}(.+)""".r

  val sqlTables =  "select * from leskdata.test_transf_dependency"
  /*
    """|select distinct
      |       t.referenced_name as table_name,
      |       (select cc.column_name
      |          from all_cons_columns cc,
      |               all_constraints c
      |        where cc.table_name = t.referenced_name
      |          and cc.owner = t.referenced_owner
      |          and c.constraint_name = cc.constraint_name
      |          and c.owner = cc.owner
      |          and c.constraint_type = 'P'
      |          and exists (select *
      |                        from all_tables k
      |                       where k.table_name = t.referenced_name || '_K'
      |                         and k.owner = t.referenced_owner)) as pk_column
      |  from dba_dependencies t
      | where t.owner = 'LESKDATA'
      |   and t.type = 'PACKAGE BODY'
      |   and t.referenced_owner = 'STGADM'
      |   and t.referenced_type  = 'TABLE'""".stripMargin
  */

  val sqlColumns =
    """|select c.column_name,
      |       c.data_type,
      |       c.data_length,
      |       c.data_precision,
      |       c.data_scale,
      |       c.data_default,
      |       (select count(*)
      |          from all_cons_columns t,
      |               all_constraints cn
      |         where t.owner = c.owner
      |           and t.table_name = c.table_name
      |           and t.column_name = c.column_name
      |           and cn.constraint_name = t.constraint_name
      |           and cn.owner = t.owner
      |           and cn.constraint_type = 'P'
      |           and rownum = 1) as is_primary
      |  from all_tab_columns c
      | where c.data_type != 'CLOB'
      |   and c.table_name = :table_name
      |   and c.owner = 'STGADM'
      | order by c.column_id""".stripMargin

  val sqlEmbededTables =
    """|select cc.table_name
      |  from all_cons_columns cc,
      |       all_constraints c
      | where cc.column_name = :column_name
      |   and cc.owner = 'STGADM'
      |   and cc.table_name != :table_name
      |   and c.constraint_name = cc.constraint_name
      |   and c.owner = cc.owner
      |   and c.constraint_type = 'P'
      |   and (cc.table_name like '%_K'
      |     or cc.table_name like '%_CHAR'
      |     or cc.table_name like '%_ID'
      |     or cc.table_name like '%_NAME'
      |     or cc.table_name like '%_PHONE')
      |  """.stripMargin


  def buildColumns(tableName: String): List[ColumnInfo] = {
    val parameters = new java.util.HashMap[String, Object]()
    parameters.put("table_name", tableName)

    jdbcReader.queryWithParameters(sqlColumns, parameters) {
      (rs, rowNum) => ColumnInfo(
        rs.getString("column_name"),
        rs.getString("data_type"),
        rs.getInt("data_length"),
        rs.getInt("data_precision"),
        rs.getInt("data_scale"),
        rs.getString("data_default"),
        rs.getInt("is_primary") == 1
      )
    }
  }

  def defineEmbeddedTables(tableName: String, columnName: String): List[String] = {
    val parameters = new java.util.HashMap[String, Object]()
    parameters.put("table_name", tableName)
    parameters.put("column_name", columnName)

    jdbcReader.queryWithParameters(sqlEmbededTables, parameters) {
      (rs, rowNum) => rs.getString("table_name")
    }
  }

  def buildTables = {

    logger.info("start load tables")

    val tablesName = jdbcReader.query(sqlTables) {
      (rs, rowNum) =>
        (rs.getString("table_name"), rs.getString("pk_column"))
    }

    tablesName.foreach(logger.info(_))

    logger.info("start load columns for tables")

    val tablesWithColumns = tablesName.map(
      (table: (String, String)) => (table._1, table._2, buildColumns(table._1))
    )

    logger.info("start load embedded tables")

    val tables: List[TableInfo] = tablesWithColumns.map(
      (table: (String, String, List[ColumnInfo])) =>
        TableInfo(
          table._1,
          table._2,
          table._3,
          if (table._2 != null) defineEmbeddedTables(table._1, table._2) else List[String]())
    )

    logger.info("create map embeddable key -> parent key")

    val primaryKeyForEmbeddableTable: Map[String, String] = (
      for (t <- tables.filter(_.embeddedTables.isEmpty == false)) yield {
        for (embeddedTable <- t.embeddedTables) yield {
          embeddedTable -> t.pkColumn
        }
      }
    ).flatten.toMap

    logger.info("remove key column from embeddable table")
    tables.map(
      (table: TableInfo) => table.copy(
        columns = {
          val keyColumn:Option[String] = primaryKeyForEmbeddableTable.get(table.name)
          keyColumn match {
            case None => table.columns
            case Some(value) => for (column <- table.columns if column.name != value) yield column
          }
        }
      )
    )

  }


  def buildEntities = {

    val tables = buildTables

    val embeddedTables = tables.map(_.embeddedTables).flatten

    logger.info("------ embedded tables --------")
    embeddedTables.foreach(logger.info(_))
    logger.info("-------------------------------")


    logger.debug("start load entity")

    val entities = tables.map((table: TableInfo) =>
      EntityInfo(table,
        embeddedTables.exists(_ == table.name),
        transformEntityName(table.name),
        List[EntityInfo]()))

    logger.debug("start load embedded entity")

    entities.map(
      (entity: EntityInfo) =>
        entity.copy(
          embeddedEntities = entities.filter(
            (e: EntityInfo) => entity.table.embeddedTables.exists(_ == e.table.name)
          )
        )
    )

  }

  def transformToCamelCase(name: String) = {
    "_([a-z\\d])".r.replaceAllIn(
    name.toLowerCase(), {
      m => m.group(1).toUpperCase()
    }
    )
  }

  def defineAnnotationForCharColumn(length: Int)(dbColumnName: String) =
    s"""|@Column(name = "$dbColumnName", columnDefinition = "char", length = $length)""".stripMargin

  def defineAnnotationForVarcharColumn(length: Int)(dbColumnName: String) =
    s"""|@Column(name = \"$dbColumnName\", length = $length)""".stripMargin

  def defineAnnotationForColumn(dbColumnName: String) =
    s"""|@Column(name = \"$dbColumnName\")""".stripMargin

  def defineAnnotationForDateColumn(dbColumnName: String) =
    s"""|@Column(name = \"$dbColumnName\")
        |  @Temporal(TemporalType.TIMESTAMP)""".stripMargin

  def defineField(field:FieldInfo, isEmbeddable:Boolean) = {

    val fieldStr =
      (field.name, field.fieldType, field.defaultValue) match {
        case (_, _, null) | (_, "Date", _) =>
          s"""|  ${field.annotation}
              |  public ${field.fieldType} ${field.name};""".stripMargin
        case (_, _, _) =>
          s"""|  ${field.annotation}
              |  public ${field.fieldType} ${field.name} = ${field.defaultValue};""".stripMargin
        case _ => s"???? $field.name ${field.fieldType} ${field.defaultValue}"
      }

    if (field.isPrimary && !isEmbeddable)
      s"""|  @Id
          |$fieldStr""".stripMargin
    else
      s"$fieldStr"
  }

  def defineMethods(name: String, dataType: String, annotation: String) = {
    s"""|
        |  $annotation
        |  public $dataType get${name.capitalize}() {return $name;}
        |  private void set${name.capitalize}($dataType $name) {this.$name = $name;}
     """.stripMargin
  }

  //defineAnnotationForColumn(name)
  //defineColumnMethods(fieldName, "int")
  //defineAnnotationForVarcharColumn(name, dataLength)

  def createFieldInfo(columnName: String,
                      fieldType: String,
                      defaultValue: String,
                      createAnnotation: String => String,
                      isPrimary: Boolean) =
    FieldInfo(transformToCamelCase(columnName), fieldType, if (defaultValue != null) defaultValue.replace("'", "\"") else defaultValue, createAnnotation(columnName), isPrimary)

  def defineField(column: ColumnInfo): FieldInfo = {
    column match {
      case ColumnInfo(name, "NUMBER", _, _, 0, defaultValue, isPrimary) =>
        createFieldInfo(name, "int", defaultValue, defineAnnotationForColumn, isPrimary)
      case ColumnInfo(name, "NUMBER", _, _, _, defaultValue, isPrimary) =>
        createFieldInfo(name, "int", defaultValue, defineAnnotationForColumn, isPrimary)
      case ColumnInfo(name, "VARCHAR2", dataLength, _, _, defaultValue, isPrimary) =>
        createFieldInfo(name, "String", defaultValue, defineAnnotationForVarcharColumn(dataLength), isPrimary)
      case ColumnInfo(name, "CHAR", dataLength, _, _, defaultValue, isPrimary) =>
        createFieldInfo(name, "String", defaultValue, defineAnnotationForCharColumn(dataLength), isPrimary)
      case ColumnInfo(name, "DATE", _, _, _, defaultValue, isPrimary) =>
        createFieldInfo(name, "Date", defaultValue, defineAnnotationForDateColumn, isPrimary)
      case _ => FieldInfo(column.name, column.dataType, column.defaultValue, "????????", false)
    }
  }

  def transformEntityName(tableName: String) = {
    tableName match {
      case entityRegExpr(name) => transformToCamelCase(name) + "Entity"
    }
  }

  def buildTitleEntity(entity: EntityInfo): String = {
    if (entity.isEmbeddable) {
      "@Embeddable"
    } else {
      s"""|@Entity
          |@Table(name = "${entity.table.name}", schema = "STGADM")""".stripMargin
    }
  }

  def getEqualRow(field:FieldInfo):String = {
    field match {
      case FieldInfo(name, "String" | "Date", _, _, true) =>
        s"(${name} != null ? ${name}.hashCode() : 0);"
      case FieldInfo(name, _, _, _, true) =>
        s"${name};"
    }
  }

  def getCalcHashRow(field:FieldInfo):String = {
    field match {
      case FieldInfo(name, "String" | "Date", _, _, true) =>
        s"${name}.hashCode();"
      case FieldInfo(name, _, _, _, true) =>
        s"${name};"
    }
  }


  def createHashCodeMethod(fields:List[FieldInfo]):String = {

    val fieldBlock = fields.filter((field:FieldInfo) => field.isPrimary).foldLeft[String]("")(
      (str, field) => str + s"""
              |    hash += ${getEqualRow(field)}
              |""".stripMargin
    )

      s"""|  @Override
          |  public int hashCode() {
          |    int hash = 0;
          |    $fieldBlock
          |    return hash;
          |  }
       """.stripMargin

  }

  def createHashCodeMethodWithoutCheck(fields:List[FieldInfo]):String = {

    val fieldBlock = fields.filter((field:FieldInfo) => field.isPrimary).foldLeft[String]("")(
      (str, field) => str +
        s"hash = 31 * hash + ${getCalcHashRow(field)}"
    )

    s"""|  @Override
          |  public int hashCode() {
          |    int hash = 0;
          |    $fieldBlock
          |    return hash;
          |  }
       """.stripMargin

  }

  def createEqualRow(field:FieldInfo) = {
    field match {
      case FieldInfo(name, "String" | "Date", _, _, true) =>
        s"if ((this.${name} == null && other.${name} != null) || (this.${name} != null && !this.${name}.equals(other.${name}))) {"
      case FieldInfo(name, _, _, _, true) => s"if (this.${name} != other.${name}) {"
    }
  }

  def createEqualRowWithoutCheck(field:FieldInfo) = {
    field match {
      case FieldInfo(name, "String" | "Date", _, _, true) =>
        s"if (!this.${name}.equals(other.${name})) {"
      case FieldInfo(name, _, _, _, true) => s"if (this.${name} != other.${name}) {"
    }
  }


  def createEqualsMethod(entityClassName:String,
                        fields:List[FieldInfo]) = {

    val fieldBlock = fields.filter((field:FieldInfo) => field.isPrimary).foldLeft[String]("")(
        (str, field) => str + s"""
              |    ${createEqualRowWithoutCheck(field)}
              |      return false;
              |    }
              |""".stripMargin
      )


    s"""|  @Override
        |  public boolean equals(Object object) {
        |
        |    if (this == object)
        |      return true;
        |
        |    if (!(object instanceof $entityClassName))
        |       return false;
        |
        |    $entityClassName other = ($entityClassName) object;
        |    $fieldBlock
        |
        |    return true;
        |  }
    """.stripMargin
  }

  def createConstructorArgsBlock(fieldsInfo:List[FieldInfo], embeddedEntities:List[EntityInfo]) = {
    (
      fieldsInfo.foldLeft[String]("")(
        (str, field) => str +
          s"""|
              |       ${field.fieldType} ${field.name},""".stripMargin
      ) +
      embeddedEntities.foldLeft[String]("")(
        (str, embeddedEntity) => str +
          s"""|
              |       Set<${embeddedEntity.name.capitalize}> ${embeddedEntity.name}Set,""".stripMargin
      )
    ).removeLast
  }

  def createConstructorBodyBlock(fieldsInfo:List[FieldInfo], embeddedEntities:List[EntityInfo]) = {
      fieldsInfo.foldLeft[String]("")(
        (str, field) => str +
          s"""|     this.${field.name} = ${field.name};
              |""".stripMargin
      ) +
      embeddedEntities.foldLeft[String]("")(
        (str, embeddedEntity) => str +
          s"""|     this.${embeddedEntity.name}Set = this.${embeddedEntity.name}Set;
              |""".stripMargin
      )
  }

  def createEmbeddedCollectionBlock(entity:EntityInfo) = {
    entity.embeddedEntities.foldLeft[String]("")(
      (str, embeddedEntity) => str + s"""
            |  @ElementCollection(fetch = FetchType.EAGER)
            |  @CollectionTable(name = "${embeddedEntity.table.name}", schema = "STGADM", joinColumns = @JoinColumn(name = "${entity.table.pkColumn}"))
            |  public Set<${embeddedEntity.name.capitalize}> ${embeddedEntity.name}Set = new HashSet<>();
            |""".stripMargin
    )
  }

  def createImportBlock(fields:List[FieldInfo], isEmbeddedEntitiesExists:Boolean) = {
    (
      if (fields.exists((field:FieldInfo)=>(field.fieldType == "Date"))){
      "import java.util.Date;"
      }  else {
        ""
      }
    ) +
    (
      if (isEmbeddedEntitiesExists) {
        """
          |import java.util.HashSet;
          |import java.util.Set;
        """.stripMargin
      } else ""
    )
  }

  def createEntityBody(entity: EntityInfo): String = {

    val packageName = "org.loader.pojo.person;"

    logger.debug(s"create entity ${entity.name} for table ${entity.table.name}")

    val fields = entity.table.columns.map(defineField(_))

    val fieldsPath = fields.foldLeft[String]("")(
      (str, field) => str +
        s"""
        |  ${defineField(field, entity.isEmbeddable)}""".stripMargin)

    val methodsPath = fields.foldLeft[String]("")(
      (str, field) => str +
        s"""|  ${defineMethods(field.name, field.fieldType, field.annotation)}
            |""".stripMargin)

    s"""
    |package org.loader.pojo;
    |
    |import javax.persistence.*;
    |${createImportBlock(fields, !entity.embeddedEntities.isEmpty)}
    |
    |${buildTitleEntity(entity)}
    |public class ${entity.name.capitalize} {
    |
    |  protected ${entity.name.capitalize}() {
    |  }
    |
    |  public ${entity.name.capitalize}(${createConstructorArgsBlock(fields, entity.embeddedEntities)}){
    |
    |${createConstructorBodyBlock(fields, entity.embeddedEntities)}  }
    |
    |${createEmbeddedCollectionBlock(entity)}
    |
    |${createEqualsMethod(entity.name.capitalize,fields)}
    |
    |${createHashCodeMethod(fields)}
    |
    |$fieldsPath
    |$methodsPath
    |}""".stripMargin
  }

  def createEntityBodySimple(entity: EntityInfo): String = {

    val packageName = "org.loader.pojo;"

    logger.debug(s"create entity ${entity.name} for table ${entity.table.name}")

    val fields = entity.table.columns.map(defineField(_))

    val fieldsBlock = fields.foldLeft[String]("")(
      (str, field) => str +
        s"""
        |
        |${defineField(field, entity.isEmbeddable)}""".stripMargin)

    s"""
    |package org.loader.pojo;
    |
    |import javax.persistence.*;
    |${createImportBlock(fields, !entity.embeddedEntities.isEmpty)}
    |
    |${buildTitleEntity(entity)}
    |public class ${entity.name.capitalize} {
    |
    |  protected ${entity.name.capitalize}() {
    |  }
    |
    |  public ${entity.name.capitalize}(${createConstructorArgsBlock(fields, entity.embeddedEntities)}){
    |
    |${createConstructorBodyBlock(fields, entity.embeddedEntities)}  }
    |
    |${createEmbeddedCollectionBlock(entity)}
    |
    |${createEqualsMethod(entity.name.capitalize,fields)}
    |
    |${createHashCodeMethodWithoutCheck(fields)}
    |$fieldsBlock
    |}""".stripMargin
  }

  def printEntity(fileName:String, text:String) = {
    import java.io._

    val file = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(text)
    bw.close()
  }


  val entities = buildEntities
  entities.foreach(
    (entity) => {
      logger.info("==== entity =========")
      logger.info("entity = " + entity.name + "(" + ")")
      if (!entity.embeddedEntities.isEmpty) {
        logger.info("---- embeddable -----")
        entity.embeddedEntities.foreach((e: EntityInfo) => logger.info("embeddable = " + e.name))
      }

    }
  )

  entities
    //.filter((entity: EntityInfo) => entity.table.name == "CI_PER")
    .foreach((entity) => {
        logger.info(entity.name)
        printEntity(s"//Users//a123//0_projects//Loader//src//main//java//org//loader//pojo//${entity.name.capitalize}.java",createEntityBodySimple(entity))
      }
    )

//  entities
//    .filter((entity: EntityInfo) => entity.table.name == "CI_PER")
//    .foreach((entity) => {
//    logger.info(entity.name)
//    logger.info(createEntityBodySimple(entity))
//  }
//    )


  //    printEntity("test.txt","test")


}
