package org.loader.pojo.spmtrevt;


import org.loader.pojo.mr.MrEntity;

import javax.persistence.*;

@Embeddable
@Table(name = "CI_SP_MTR_EVT", schema = "STGADM")
public class SpMtrEvtEntity {

  public SpMtrEvtEntity() {
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof SpMtrEvtEntity))
      return false;

    SpMtrEvtEntity other = (SpMtrEvtEntity) object;

    if (this.seqno != other.seqno) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + seqno;

    return hash;
  }

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MR_ID")
  public MrEntity mr;

  @Column(name = "SEQNO")
  public int seqno;

  @Column(name = "MTR_ON_OFF_FLG", columnDefinition = "char", length = 2)
  public String mtrOnOffFlg = " ";

  @Column(name = "SP_MTR_EVT_FLG", columnDefinition = "char", length = 2)
  public String spMtrEvtFlg = " ";

  @Column(name = "VERSION")
  public int version = 1;


}