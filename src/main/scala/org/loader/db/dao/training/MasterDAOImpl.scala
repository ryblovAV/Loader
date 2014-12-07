package org.loader.db.dao.training

import javax.persistence.EntityManager

import org.loader.training.Master
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

@Repository("masterDAO")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class MasterDAOImpl extends MasterDAO {

  @Autowired
  var entityManager: EntityManager = _

  override def save(master: Master): Unit = {
    entityManager.persist(master)
  }

  override def find(id: String): Option[Master] = {
    Option(entityManager.find(classOf[Master],id))
  }
}
