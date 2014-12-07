package org.loader.db.dao.general

import javax.persistence.EntityManager

import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctapay.AcctApayEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.PerEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

@Repository("generalDAO")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class GeneralDAOImpl extends GeneralDAO {

  @Autowired
  var entityManager: EntityManager = _

  override def save(per: PerEntity, acct: AcctEntity, acctPer:AcctPerEntity): Unit = {
    entityManager.persist(per)
    entityManager.persist(acct)
    entityManager.persist(acctPer)
  }
}
