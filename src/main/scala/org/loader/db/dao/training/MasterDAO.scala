package org.loader.db.dao.training

import org.loader.training.Master

/**
 * Created by a123 on 23/11/14.
 */
trait MasterDAO {
  def save(master:Master):Unit

  def find(id:String):Option[Master]
}
