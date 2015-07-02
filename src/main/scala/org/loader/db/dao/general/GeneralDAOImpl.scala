package org.loader.db.dao.general

import javax.persistence.EntityManager

import grizzled.slf4j.Logging
import org.loader.models.SubjectModel
import org.loader.pojo.mtrcfg.MtrConfigEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.tndr.DepCtlStEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

@Repository("generalDAO")
class GeneralDAOImpl extends GeneralDAO with Logging{

  @Autowired
  var entityManager: EntityManager = _

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  override def save(per: PerEntity): Unit = {
    entityManager.persist(per)
  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  override def saveList(perList: List[SubjectModel]): Unit = {
    info("--------- start persist")
    perList.foreach((subj) => entityManager.persist(subj.per))
    info("--------- end persist")
  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  override def save(mtrCfg: MtrConfigEntity): Unit = {
    entityManager.persist(mtrCfg)
  }

  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  def findPer(perId:String):PerEntity = {
    entityManager.find(classOf[PerEntity],perId)
  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  def removePer(perId:String) = {
    val per = findPer(perId)
    if (per != null) {
      entityManager.remove(per)
    }
  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  def saveDepCtlSt(depCtlSt: DepCtlStEntity) = {
    entityManager.persist(depCtlSt)
  }



}
