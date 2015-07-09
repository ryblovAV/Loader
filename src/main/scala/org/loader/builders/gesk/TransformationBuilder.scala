package org.loader.builders.gesk

import org.loader.models.{SubjectModel, ObjectModel}

object TransformationBuilder {

  def addToParent(parIdRec: String, obj: ObjectModel, m:Map[String,ObjectModel]) = {
    for (parentSubj <- m.get(obj.potr.idRecI)) {
      SaSpBuilderG.buildSaSp(sa = parentSubj.sa, sp = obj.sp, mr = obj.mr, isMinus = true)
    }
  }

  def linkToParent(obj: ObjectModel, m:Map[String,ObjectModel]) = {
    if (obj.potr.idRecI != "0") {
      addToParent(parIdRec = obj.potr.idRecI,obj = obj, m = m)
    }

    for (parentIdRec <- obj.potr.parentIdRec) {
      addToParent(parIdRec = parentIdRec,obj = obj, m = m)
    }
  }

  def transforEntity(subjects: List[SubjectModel]) = {
    val m = Map.empty[String,ObjectModel] ++
      (subjects.flatMap((s)=>s.objects).map((o) => (o.potr.idRec,o)))
    m.values.foreach((o) => linkToParent(o,m))
  }

}
