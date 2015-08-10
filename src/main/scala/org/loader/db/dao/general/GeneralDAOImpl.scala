package org.loader.db.dao.general

import javax.persistence.EntityManager

import grizzled.slf4j.Logging
import org.loader.models.SubjectModel
import org.loader.pojo.mtrcfg.MtrConfigEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.sp.SpEntity
import org.loader.pojo.tndr.DepCtlStEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

@Repository("generalDAO")
class GeneralDAOImpl extends GeneralDAO with Logging{

  @Autowired
  var entityManager: EntityManager = _

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  override def save(subject: SubjectModel): Unit = {
    entityManager.persist(subject.per)
    subject.spObjects.foreach((spObj) => entityManager.persist(spObj.sp))
  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  override def saveList(perList: List[SubjectModel]): Unit = {
    info("--------- start persist")
    perList.foreach((subj) => {
      entityManager.persist(subj.per)
      subj.spObjects.foreach((spObj) => entityManager.persist(spObj.sp))
    })
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

  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  def findSp(spId:String):SpEntity = {
    entityManager.find(classOf[SpEntity],spId)
  }


  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  def removePer(perId:String) = {
    val per = findPer(perId)
    if (per != null) {
      entityManager.remove(per)
    }
  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  def removePerList(perList:List[String]) = {
    info(s"remove $perList")
    perList.foreach((perId) => {
      val per = findPer(perId)
      if (per != null) {
        entityManager.remove(per)
      }
      info(per.perId)
    })
  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  def removeSpList(spList:List[String]) = {
    spList.foreach((spId) => {
      val sp = findSp(spId)
      if (sp != null) {
        entityManager.remove(sp)
      }
    })
  }


  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  def saveDepCtlSt(depCtlSt: DepCtlStEntity) = {
    entityManager.persist(depCtlSt)
  }



}
