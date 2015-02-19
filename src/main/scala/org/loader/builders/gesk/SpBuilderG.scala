package org.loader.builders.gesk

import org.loader.builders.Utils
import org.loader.out.gesk.objects.Potr
import org.loader.pojo.sp.SpEntity

/*
run(p_sp_id             => null,
                                              p_prem_id           => l_local_prem_id,
                                              p_install_dt        => l_install_dt,
                                              p_sp_src_status_flg => 'C',
                                              p_lvl1              => null,
                                              p_lvl2              => null,
                                              p_lvl3              => null,
                                              p_naim_tu           => q_rtu.naimp,
                                              p_kod               => q_rtu.kelsch,
                                              p_voltage_str       => q_rtu.volt,
                                              p_max_voltage_max   => null,
                                              p_mtr_loc_details   => q_rtu.naimp,
                                              p_postr_or          => null);
 */


object SpBuilderG {
  def build(potr:Potr):SpEntity = {
    val sp = new SpEntity(Utils.getEnvId)
    sp.installDt = Utils.getDefaultDt
    sp.spSrcStatusFlg = "C"
    sp.mtrLocDetails = potr.naimp

    sp
  }
}
