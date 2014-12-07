package org.loader.db.dao.person

import org.loader.pojo.per.PerEntity

trait PersonDAO {

  def save(personEntity: PerEntity): Unit

  def find(id: String): Option[PerEntity]

}
