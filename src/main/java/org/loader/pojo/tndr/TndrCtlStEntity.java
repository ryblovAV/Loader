package org.loader.pojo.tndr;

import javax.persistence.*;

@Entity
@Table(name = "CI_TNDR_CTL_ST", schema = "STGADM")
public class TndrCtlStEntity {

  @EmbeddedId
  TndrCtlStPk id;

  protected TndrCtlStEntity() {
  }

  public TndrCtlStEntity(TndrCtlStPk id) {
      this.id = id;
  }

  @MapsId("depCtlStId")
  @JoinColumns({
          @JoinColumn(name = "EXT_SOURCE_ID", referencedColumnName = "EXT_SOURCE_ID"),
          @JoinColumn(name = "EXT_TRANSMIT_ID", referencedColumnName = "EXT_TRANSMIT_ID")
  })
  @OneToOne DepCtlStEntity depCtlSt;

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof TndrCtlStEntity))
      return false;

    TndrCtlStEntity other = (TndrCtlStEntity) object;

    if (!this.id.equals(other.id)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Column(name = "TNDR_CTL_ID", columnDefinition = "char", length = 10)
  public String tndrCtlId = " ";

  @Column(name = "TND_CTL_STG_ST_FLG", columnDefinition = "char", length = 2)
  public String tndCtlStgStFlg = " ";

  @Column(name = "TOT_TNDR_AMT")
  public double totTndrAmt = 0;

  @Column(name = "TOT_TNDR_CNT")
  public int totTndrCnt = 0;

  @Column(name = "VERSION")
  public int version = 1;

}