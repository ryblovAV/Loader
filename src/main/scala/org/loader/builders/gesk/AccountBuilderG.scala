package org.loader.builders.gesk

import org.loader.builders.general.AccountBuilder.addAcctChar
import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acct.AcctEntity

object AccountBuilderG {

  def buildAccount(plat: Plat) = {

    val account = new AcctEntity(KeysBuilder.getEnvId)
    account.acctId = KeysBuilder.getAcctId
    account.billCycCd = "M-D1"
    account.custClCd = "COMM"
    account.accessGrpCd = "GESK_COMM"

    account.setupDt = plat.data match {
      case Some(data) => data
      case None => DateBuilder.getDefaultDt
    }
    account.currencyCd = "RUR"
    account.cisDivision = "GESK"

    //char
    plat.kontr match {
      case Some(kontr) => {
        addAcctChar(account, Characteristic(charTypeCd = "NOM_DOG", adhocCharVal = kontr))
        addAcctChar(account, Characteristic(charTypeCd = "VID_DOG", charVal = "7"))
      }
      case None => plat.ndGesk match {
        case Some(ndGesk) => {
          addAcctChar(account, Characteristic(charTypeCd = "NOM_DOG", adhocCharVal = ndGesk))
          addAcctChar(account, Characteristic(charTypeCd = "VID_DOG", charVal = "6"))
        }
        case None =>
      }
    }

    //TODO Расчетный счет (ГЭСК) (4 характеристики)
    //TODO Загрузка БИК

    account
  }


}
