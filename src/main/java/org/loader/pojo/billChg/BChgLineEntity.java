package org.loader.pojo.billChg;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BChgLineEntity {

  public BChgLineEntity() {
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof BChgLineEntity))
      return false;

    BChgLineEntity other = (BChgLineEntity) object;

    if (this.lineSeq != other.lineSeq) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return lineSeq;
  }

  @Column(name = "LINE_SEQ")
  public int lineSeq;

  @Column(name = "APP_IN_SUMM_SW", columnDefinition = "char", length = 1)
  public String appInSummSw = " ";

  @Column(name = "CHARGE_AMT")
  public double chargeAmt = 0;

  @Column(name = "CURRENCY_CD", columnDefinition = "char", length = 3)
  public String currencyCd = " ";

  @Column(name = "DESCR_ON_BILL", length = 80)
  public String descrOnBill = " ";

  @Column(name = "DST_ID", columnDefinition = "char", length = 10)
  public String dstId = " ";

  @Column(name = "MEMO_SW", columnDefinition = "char", length = 1)
  public String memoSw = " ";

  @Column(name = "SHOW_ON_BILL_SW", columnDefinition = "char", length = 1)
  public String showOnBillSw = " ";

  @Column(name = "VERSION")
  public int version = 1;


}