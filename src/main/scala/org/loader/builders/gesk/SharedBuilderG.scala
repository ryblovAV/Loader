package org.loader.builders.gesk

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.models.{SubjectModel, ObjectModel}
import org.loader.writer.SaSpObject

object SharedBuilderG {

  def buildSaSpObject(parentIdRec: String, obj: ObjectModel, m:Map[String,ObjectModel]):Option[SaSpObject] =
    m.get(parentIdRec) match {
      case Some(parentSubj) => Some(
        SaSpObject(
          saSpId = KeysBuilder.getSaSpId,
          saId = parentSubj.sa.saId,
          spId = obj.sp.spId,
          startDttm = DateBuilder.addMinute(parentSubj.sa.startDt,1),
          startMrId = obj.mr.mrId,
          usageFlg = "-",
          usePct = 100))
      case _ => None
    }

  def buildSaSpObject(obj:ObjectModel,m:Map[String,ObjectModel]):List[SaSpObject] = {
    obj.potr.parent.getParentIdRec.map(
      (parentIdRec) => 
        buildSaSpObject(parentIdRec = parentIdRec, obj = obj, m = m)).flatten
  }
  
  def buildListSaSpObject(subjects: List[SubjectModel]):List[SaSpObject] = {
    val m = Map.empty[String,ObjectModel] ++
      (subjects.flatMap((s)=>s.objects).map((o) => (o.potr.idRec,o)))
    m.values.flatMap(
      (obj) => buildSaSpObject(obj = obj, m = m)).toList
  }


}
