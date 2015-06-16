package org.loader.new.pojo;

import org.loader.new.pojo.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
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

    if (!this.spMtrHistId.equals(other.spMtrHistId)) {
      return false;
    }
    if (this.seqno != other.seqno) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + spMtrHistId.hashCode();
    hash = 31 * hash + seqno;

    return hash;
  }



  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MR_ID")
  public MrEntity mr;


  @Id
  @Column(name = "SEQNO")
  public int seqno;

  @Id
  @Column(name = "SP_MTR_HIST_ID", columnDefinition = "char", length = 10)
  public String spMtrHistId;


  @Column(name = "MTR_ON_OFF_FLG", columnDefinition = "char", length = 2)
  public String mtrOnOffFlg = " ";

  @Column(name = "SP_MTR_EVT_FLG", columnDefinition = "char", length = 2)
  public String spMtrEvtFlg = " ";

  @Column(name = "VERSION")
  public int version = 1;


}