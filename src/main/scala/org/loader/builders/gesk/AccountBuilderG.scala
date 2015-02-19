package org.loader.builders.gesk


import org.loader.builders.Utils
import org.loader.builders.general.AccountBuilderUtl.addAcctChar
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acct.AcctEntity

object AccountBuilderG {

  def buildAccount(plat: Plat) = {

    val account = new AcctEntity(Utils.getEnvId)
    account.acctId = Utils.getAcctId
    account.billCycCd = "M-D1"
    account.setupDt = if (plat.dateConclusion != null) plat.dateConclusion else Utils.getDefaultDt
    account.currencyCd = "RUR"
    account.cisDivision = "GESK"

    //char
    addAcctChar(account, Characteristic(charTypeCd = "NOM_DOG", adhocCharVal = plat.agreementNumberGESK))

    account
  }
}
