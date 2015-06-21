package org.loader.models

import org.loader.out.gesk.objects.Potr
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sp.SpEntity

case class ObjectModel(potr: Potr, sp: SpEntity, sa:SaEntity, mr:MrEntity)

case class SubjectModel(idPlat: String, per: PerEntity, objects: List[ObjectModel])

