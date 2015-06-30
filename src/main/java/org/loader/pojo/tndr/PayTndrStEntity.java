package org.loader.pojo.tndr;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CI_PAY_TNDR_ST", schema = "RUSADM")
public class PayTndrStEntity {

  @EmbeddedId
  PayTndrStPk id;

  protected PayTndrStEntity() {
  }

  public PayTndrStEntity(PayTndrStPk id) {
      this.id = id;
  }

  @MapsId("depCtlStPk")
  @JoinColumns({
          @JoinColumn(name = "EXT_SOURCE_ID", referencedColumnName = "EXT_SOURCE_ID"),
          @JoinColumn(name = "EXT_TRANSMIT_ID", referencedColumnName = "EXT_TRANSMIT_ID")
  })
  @OneToOne DepCtlStEntity depCtlStEntity;

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof PayTndrStEntity))
      return false;

    PayTndrStEntity other = (PayTndrStEntity) object;

    if (!this.id.equals(other.id)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Column(name = "ACCOUNTING_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date accountingDt;

  @Column(name = "CHECK_NBR", columnDefinition = "char", length = 10)
  public String checkNbr = " ";

  @Column(name = "CUST_ID", columnDefinition = "char", length = 36)
  public String custId = " ";

  @Column(name = "ENCR_MICR_ID", length = 64)
  public String encrMicrId;

  @Column(name = "HASH_MICR_ID", length = 88)
  public String hashMicrId;

  @Column(name = "MICR_ID", length = 30)
  public String micrId = " ";

  @Column(name = "NAME1", length = 40)
  public String name1 = " ";

  @Column(name = "PAY_TND_STG_ST_FLG", columnDefinition = "char", length = 2)
  public String payTndStgStFlg = " ";

  @Column(name = "TENDER_AMT")
  public double tenderAmt = 0;

  @Column(name = "TENDER_TYPE_CD", columnDefinition = "char", length = 4)
  public String tenderTypeCd = " ";

  @Column(name = "VERSION")
  public int version = 1;


}