package org.loader.builders.gesk


import org.loader.builders.Utills
import org.loader.builders.general.AccountBuilderUtl.addAcctChar
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acct.AcctEntity

object AccountBuilderG {

  def buildAccount(plat: Plat) = {

    val account = new AcctEntity(Utills.getEnvId)
    account.acctId = Utills.getAcctId
    account.billCycCd = "M-D1"
    account.setupDt = if (plat.ndGesk != null) plat.data else Utills.getDefaultDt
    account.currencyCd = "RUR"
    account.cisDivision = "GESK"

    //char
    addAcctChar(account, Characteristic(charTypeCd = "NOM_DOG", adhocCharVal = plat.ndGesk))

    account
  }
}
