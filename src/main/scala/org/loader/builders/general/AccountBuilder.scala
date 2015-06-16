package org.loader.builders.general


import org.loader.models.Characteristic
import org.loader.pojo.acct.{AcctCharEntity, AcctEntity, AcctKEntity}

object AccountBuilder {

  implicit def buildChar(char: Characteristic): AcctCharEntity = {

    val acctChar = new AcctCharEntity()
    acctChar.charTypeCd = char.charTypeCd
    acctChar.effdt = char.effDt
    acctChar.charVal = char.charVal
    acctChar.adhocCharVal = char.adhocCharVal

    acctChar
  }

  def addAcctChar(acct: AcctEntity,
                  char: Characteristic) = {

    if (!char.isEmpty)
      acct.acctCharEntitySet.add(char)
  }

  def addAcctKey(acct: AcctEntity) = {
    acct.acctKEntitySet.add(new AcctKEntity(KeysBuilder.getEnvId))
  }

}
