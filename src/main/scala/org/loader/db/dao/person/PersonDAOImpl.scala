package org.loader.db.dao.person

import javax.persistence.EntityManager

import org.loader.pojo.per.PerEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

@Repository("personDAO")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class PersonDAOImpl extends PersonDAO{

  @Autowired
  var entityManager: EntityManager = _

  override def save(personEntity: PerEntity): Unit = {
    entityManager.persist(personEntity)
  }

  override def find(id: String): Option[PerEntity] = {
    Option(entityManager.find(classOf[PerEntity], id))
  }
}
