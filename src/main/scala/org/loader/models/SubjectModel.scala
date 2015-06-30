package org.loader.models

import org.loader.out.gesk.objects.{Plat, Potr}
import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.mr.MrEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.reg.RegEntity
import org.loader.pojo.sa.SaEntity
import org.loader.pojo.sp.SpEntity

case class ObjectModel(potr: Potr, sp: SpEntity, sa:SaEntity, mr:MrEntity, regList: List[(RegEntity,Potr)])

case class SubjectModel(plat: Plat, per: PerEntity, acct: AcctEntity, objects: List[ObjectModel])

