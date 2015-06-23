package org.loader.pojo.tndr;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CI_DEP_CTL_ST", schema = "STGADM")
public class DepCtlStEntity {

  protected DepCtlStEntity() {
  }

  public DepCtlStEntity(DepCtlStPk id) {
    this.id = id;
  }

  @EmbeddedId
  public DepCtlStPk id;

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof DepCtlStEntity))
      return false;

    DepCtlStEntity other = (DepCtlStEntity) object;

    if (!this.id.equals(other.id)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Column(name = "CURRENCY_CD", columnDefinition = "char", length = 3)
  public String currencyCd = " ";

  @Column(name = "DEP_CTL_ID", columnDefinition = "char", length = 10)
  public String depCtlId = " ";

  @Column(name = "DEP_CTL_STG_ST_FLG", columnDefinition = "char", length = 2)
  public String depCtlStgStFlg = " ";

  @Column(name = "LAST_UPDATE_INST")
  public int lastUpdateInst = 0;

  @Column(name = "TOT_TNDR_CTL_AMT")
  public double totTndrCtlAmt = 0;

  @Column(name = "TOT_TNDR_CTL_CNT")
  public int totTndrCtlCnt = 0;

  @Column(name = "TRANSMIT_DTTM")
  @Temporal(TemporalType.TIMESTAMP)
  public Date transmitDttm;

  @Column(name = "VERSION")
  public int version = 1;


}