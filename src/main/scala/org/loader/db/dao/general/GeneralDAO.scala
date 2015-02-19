package org.loader.db.dao.general

import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.PerEntity


trait GeneralDAO {
  def save(per:PerEntity, acct:AcctEntity, acctPer:AcctPerEntity):Unit
}
