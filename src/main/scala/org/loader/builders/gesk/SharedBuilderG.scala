package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.models.{SubjectModel, ObjectModel}
import org.loader.writer.SaSpObject

object SharedBuilderG {

  def buildSaSpObject(parentIdRec: String, obj: ObjectModel, m:Map[String,ObjectModel]):List[SaSpObject] =
    m.get(parentIdRec) match {
      case Some(parentSubj) => obj.spObjects.map((spObj) =>
        SaSpObject(
          saSpId = KeysBuilder.getSaSpId,
          saId = parentSubj.sa.saId,
          spId = spObj.sp.spId,
          startDttm = DateBuilder.addMinute(parentSubj.sa.startDt,1),
          startMrId = spObj.mrFirst.map(_.mrId),
          usageFlg = "-",
          usePct = 100))
      case _ => List.empty[SaSpObject]
    }

  def buildSaSpObject(obj:ObjectModel,m:Map[String,ObjectModel]):List[SaSpObject] = {
    //TODO fix convertion option to list
    obj.potr.parent.idRecI.map(
      (parentIdRec) => buildSaSpObject(parentIdRec = parentIdRec, obj = obj, m = m)).getOrElse(List.empty)
  }

  def buildSaSpObjectList(obj:ObjectModel,m:Map[String,ObjectModel]):List[SaSpObject] = {
    obj.potr.parent.parentIdRecList.flatMap((parentIdRec) => buildSaSpObject(parentIdRec = parentIdRec, obj = obj, m = m))
  }

  def buildListSaSpObject(subjects: List[SubjectModel]):List[SaSpObject] = {

    //create Map idRec -> Objects
    val m = Map.empty[String,ObjectModel] ++
      (subjects.flatMap((s)=>s.objects).map((o) => (o.potr.idRec,o)))
    m.values.flatMap(
      (obj) => buildSaSpObject(obj,m):::buildSaSpObjectList(obj,m)).toList
  }


}
