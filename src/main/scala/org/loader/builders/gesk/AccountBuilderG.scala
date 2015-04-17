package org.loader.builders.gesk

import org.loader.builders.general.AccountBuilderUtl.addAcctChar
import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acct.AcctEntity

object AccountBuilderG {

  def buildAccount(plat: Plat) = {

    val account = new AcctEntity(KeysBuilder.getEnvId)
    account.acctId = KeysBuilder.getAcctId
    account.billCycCd = "M-D1"
    account.setupDt = if (plat.dateConclusion != null) plat.dateConclusion else DateBuilder.getDefaultDt
    account.currencyCd = "RUR"
    account.cisDivision = "GESK"

    //char
    addAcctChar(account, Characteristic(charTypeCd = "NOM_DOG", adhocCharVal = plat.agreementNumberGESK))

    account
  }


}
