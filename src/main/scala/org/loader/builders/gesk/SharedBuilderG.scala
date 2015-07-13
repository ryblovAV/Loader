package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.models.{SubjectModel, ObjectModel}
import org.loader.writer.SaSpObject

object SharedBuilderG {



  def buildSaSpObject(parIdRec: String, obj: ObjectModel, m:Map[String,ObjectModel]):Option[SaSpObject] =
    m.get(obj.potr.idRecI) match {
      case Some(parentSubj) => Some(SaSpObject(saSpId = KeysBuilder.getSaSpId,
        saId = parentSubj.sa.saId,
        spId = obj.sp.spId,
        startDttm = DateBuilder.addMinute(parentSubj.sa.startDt,1),
        startMrId = obj.mr.mrId,
        usageFlg = "-",
        usePct = 100))
      case _ => None
    }

  def buildWithIdRecI(obj: ObjectModel,
                      m:Map[String,ObjectModel]):Option[SaSpObject] = {
    if (obj.potr.idRecI != "0")
      buildSaSpObject(parIdRec = obj.potr.idRecI, obj = obj, m = m)
    else None
  }

  def buildWithParentIdRec(obj: ObjectModel,
                           m:Map[String,ObjectModel]):Option[SaSpObject] = (obj.potr.parentIdRec, obj.potr.idRecI) match {
    case (Some(parentIdRec),idRecI) if parentIdRec == idRecI => None
    case (Some(parentIdRec),_) => buildSaSpObject(parIdRec = parentIdRec, obj = obj, m = m)
    case _ => None
  }

  def buildSaSpObjectList(o:ObjectModel,m:Map[String,ObjectModel]):List[SaSpObject] = {
    for {a <- List(buildWithIdRecI(o,m),buildWithParentIdRec(o,m)).distinct
         b <- a } yield b
  }

  def buildListSaSpObject(subjects: List[SubjectModel]):List[SaSpObject] = {
    val m = Map.empty[String,ObjectModel] ++
      (subjects.flatMap((s)=>s.objects).map((o) => (o.potr.idRec,o)))
    m.values.flatMap((o) => buildSaSpObjectList(o,m)).toList
  }


}
