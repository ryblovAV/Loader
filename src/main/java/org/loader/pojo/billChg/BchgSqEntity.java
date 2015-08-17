package org.loader.pojo.billChg;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BchgSqEntity {

  public BchgSqEntity() {
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof BchgSqEntity))
      return false;

    BchgSqEntity other = (BchgSqEntity) object;

    if (this.seqNum != other.seqNum) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    return seqNum;
  }

  @Column(name = "SEQ_NUM")
  public int seqNum;

  @Column(name = "SQI_CD", columnDefinition = "char", length = 8)
  public String sqiCd = " ";

  @Column(name = "SVC_QTY")
  public double svcQty = 0;

  @Column(name = "TOU_CD", columnDefinition = "char", length = 8)
  public String touCd = " ";

  @Column(name = "UOM_CD", columnDefinition = "char", length = 4)
  public String uomCd = " ";

  @Column(name = "VERSION")
  public int version = 1;


}