//package org.logger.db

package scalatest

import java.util.Date

import org.junit.runner.RunWith
import org.loader.builders._
import org.loader.builders.lesk.{AccountBuilderL, AccountPersonBuilderL, PersonBuilderL}
import org.loader.db.dao.general.GeneralDAO
import org.loader.db.dao.training.MasterDAO
import org.loader.db.utl.DBUtl
import org.loader.out.lesk.objects.{ObjectWithDate, ValueObject}
import org.loader.out.lesk.reader.TestReader
import org.loader.training.{Master, MasterAddress}
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.springframework.context.support.ClassPathXmlApplicationContext

case class PersonS(address1: String)

@RunWith(classOf[JUnitRunner])
class SimpleTest extends FunSuite {

  val ctx = new ClassPathXmlApplicationContext("application-context.xml")


  //test("jdbc read") {
  //    val a: OutReader = ctx.getBean(classOf[OutReader])
  //
  //    val l = a.query("select address1 from stgadm.ci_per p where p.per_id = '0132074977'") { (rs, rowNum) =>
  //      PersonS(address1 = rs.getString("address1"))
  //    }
  //
  //    println("JDBC template")
  //    println("l* = " + l)
  //    assert(true)
  //  }

  ignore("jpa person write") {
    val generalDAO = ctx.getBean(classOf[GeneralDAO])

    val client = TestReader.getClient

    val per = PersonBuilderL.buildPerson(client)
    
    val acct = AccountBuilderL.buildAccount(client)

    //TODO correct check
    if (client.currentAccount != null && client.codeBank != null) {
      val acctApay = AcctApayBuilder.buildAcctApay(client)
      acct.acctApayEntitySet.add(acctApay)
    }

    val acctPer = AccountPersonBuilderL.linkAccoutPerson(per,acct)

    generalDAO.save(per,acct, acctPer)

    assert(true)
  }
/*
  ignore("jpa person read") {
    val personDAO = ctx.getBean(classOf[PersonDAO])
    val personOpt = personDAO.find("0123456789")

    personOpt match {
      case Some(person) => {
        println(
          s"""person
             |id = ${person.perId}
             |address1 = ${person.address1}
             |chars.size ${person.perCharEntitySet.toArray().length}""".stripMargin)
      }
      case None => println("NONE")
    }
    assert(true)
  }
*/
  val id = "9990000009"

  ignore("jpa master save") {

    val master = new Master()

    master.id = id

    master.addresses.add(new MasterAddress("TYPEA"))
    master.addresses.add(new MasterAddress("TYPEB"))

    val masterDAO = ctx.getBean(classOf[MasterDAO])
    masterDAO.save(master)

    assert(true)
  }

  ignore("jpa master read") {
    val masterDAO = ctx.getBean(classOf[MasterDAO])
    val masterOpt = masterDAO.find(id)

    masterOpt match {
      case Some(master) => {
        println(
          s"""master
             |id = ${master.id}
             |address = ${master.toString}""".stripMargin)
      }
      case None => println("NONE")
    }
    assert(true)
  }

  ignore("patter match") {

    case class Val(dt:Date,value:Int) {

      override def equals(obj: scala.Any): Boolean =
        if (obj == null) false
        else
          if (!obj.isInstanceOf[Val])
            false
          else
            value == obj.asInstanceOf[Val].value

    }

    def dt(i:Int) = DBUtl.getDate(2014,i,1)

    val list = List(
      Val(dt(1), 0),
      Val(dt(2), 0),
      Val(dt(3), 2),
      Val(dt(4), 2),
      Val(dt(5), 3),
      Val(dt(6), 4)
    )

    val mergeList = ValueObject.deleteRepeat(list,(a:Object,b:Object) => a.equals(b))

    assert(mergeList.size == 4)

    println(mergeList)

  }

  test("zip") {


    import org.loader.out.lesk.objects.{ObjectWithDateZip, TypeA, TypeB}

    val a: List[TypeA] = List(
      TypeA(DBUtl.getDate(2014, 3, 1)),
      TypeA(DBUtl.getDate(2014, 1, 1)),
      TypeA(DBUtl.getDate(2014, 4, 1)),
      TypeA(DBUtl.getDate(2014, 5, 1)),
      TypeA(DBUtl.getDate(2014, 1, 1)),
      TypeA(DBUtl.getDate(2014, 2, 1))
    )

    val b: List[TypeB] = List(
      TypeB(DBUtl.getDate(2013, 12, 1)),
      TypeB(DBUtl.getDate(2014, 12, 4)),
      TypeB(DBUtl.getDate(2014, 1, 5)),
      TypeB(DBUtl.getDate(2014, 5, 11)),
      TypeB(DBUtl.getDate(2014, 3, 7)),
      TypeB(DBUtl.getDate(2014, 2, 10))
    )


    val lUnion = ObjectWithDateZip.zip(a, b).sortWith((a:ObjectWithDate,b:ObjectWithDate) => a < b)

    println("------lUnion -------------")
    lUnion.foreach(println(_))

    val pair = ObjectWithDateZip.createPair(lUnion,None,None,List[(TypeA,TypeB)]())

    println("-----pair-----------")
    pair.foreach(println(_))
    println("-----deleteRepeat-------")

    val equaFunc = (first:(ObjectWithDate,ObjectWithDate),second:(ObjectWithDate,ObjectWithDate)) => {
      (first._1.equalDate(second._1)) && (first._2.equalDate(second._2))
    }

    ValueObject.deleteRepeat(pair,equaFunc).foreach(println(_))

  }

  test("Key")  {
    println("id = " + Keys.spId("12"))
    assert(true)
  }


}
