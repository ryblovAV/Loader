package org.loader.db.dao.general

import org.loader.models.SubjectModel
import org.loader.pojo.mtrcfg.MtrConfigEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.tndr.DepCtlStEntity


trait GeneralDAO {

  def save(per:PerEntity):Unit
  def save(mtrCfg: MtrConfigEntity): Unit
  def saveList(perList: List[SubjectModel]): Unit

  def findPer(perId:String):PerEntity
  def removePer(perId:String)

  def saveDepCtlSt(depCtlSt: DepCtlStEntity)

}
