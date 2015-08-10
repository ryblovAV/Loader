package org.loader.db.dao.general

import org.loader.models.SubjectModel
import org.loader.pojo.mtrcfg.MtrConfigEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.tndr.DepCtlStEntity
import org.springframework.transaction.annotation.{Propagation, Transactional}


trait GeneralDAO {

  def save(subject:SubjectModel):Unit
  def save(mtrCfg: MtrConfigEntity): Unit
  def saveList(perList: List[SubjectModel]): Unit

  def findPer(perId:String):PerEntity
  def removePer(perId:String)

  def removePerList(perList:List[String])
  def removeSpList(spList:List[String])

  def saveDepCtlSt(depCtlSt: DepCtlStEntity)

}
