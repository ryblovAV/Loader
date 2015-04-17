package org.loader.db.dao.general

import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.PerEntity
import org.loader.pojo.prem.PremEntity


trait GeneralDAO {

  def save(per:PerEntity):Unit

  def findPer(perId:String):PerEntity
  def removePer(perId:String)
}
