package org.loader.out.lesk.objects

import org.loader.builders.Utils

/**
 * Created by a123 on 24/11/14.
 */
case class Client(id: String,
                  codeBase: String,
                  name: String,
                  email: String,
                  address: String,
                  houseU: String,
                  streetU: String,
                  postcodeU: String,
                  townU: String,
                  contract: String,
                  inn: String,
                  iku: String,
                  kpp: String,
                  budget: String,
                  phone: String,
                  phone_d: String,
                  codeDepartment: String,
                  department: String,
                  dateCancelling: java.util.Date,
                  dateConclusion: java.util.Date,
                  currentAccount:String,
                  codeBank:String) {

  def getFirstDt = {
    //TODO realize method getFirstDt
    Utils.getDefaultDt
  }

  def isTSO = {
    false
    //TODO realise method isTSO
    /*
          select count(*)
        into l_is_tso
        from v_cm_load_sp_y y
       where y.idklienta = q_rabo.id_kl
         and y.tar_gr like ('%ТСО%')
         and rownum = 1;
     */
  }


}
