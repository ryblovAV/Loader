package org.loader.pojo.spmtrhist;

import org.loader.pojo.mr.MrEntity;
import org.loader.pojo.mtrcfg.MtrConfigEntity;
import org.loader.pojo.mtrlochis.MtrLocHisEntity;
import org.loader.pojo.sp.SpEntity;
import org.loader.pojo.spmtrevt.SpMtrEvtEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_SP_MTR_HIST", schema = "RUSADM")

public class SpMtrHistEntity {
  protected SpMtrHistEntity() {
  }
  public SpMtrHistEntity(int envId) {
    spMtrHistKEntitySet.add(new SpMtrHistKEntity(envId));
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof SpMtrHistEntity))
      return false;

    SpMtrHistEntity other = (SpMtrHistEntity) object;

    if (!this.spMtrHistId.equals(other.spMtrHistId)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + spMtrHistId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_SP_MTR_HIST_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "SP_MTR_HIST_ID"))
  public Set<SpMtrHistKEntity> spMtrHistKEntitySet = new HashSet<>();
     
  @ElementCollection
  @CollectionTable(name = "CI_SP_MTR_EVT", schema = "RUSADM", joinColumns = @JoinColumn(name = "SP_MTR_HIST_ID"))
  public Set<SpMtrEvtEntity> spMtrEvtSpMtrHistEntitySet = new HashSet<>();

//  @OneToMany(mappedBy = "spMtrHist", cascade = CascadeType.ALL)
//  public Set<MtrLocHisEntity> mtrLocHisSpMtrHistEntitySet = new HashSet<>();


//  @ManyToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "REMOVAL_MR_ID")
//  public MrEntity removalMr;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "SP_ID")
  public SpEntity sp;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MTR_CONFIG_ID")
  public MtrConfigEntity mtrCfg;


  @Id
  @Column(name = "SP_MTR_HIST_ID", columnDefinition = "char", length = 10)
  public String spMtrHistId;


  @Column(name = "INSTALL_CONST")
  public double installConst = 1;

  @Column(name = "REMOVAL_DTTM")
  @Temporal(TemporalType.TIMESTAMP)
  public Date removalDttm;

  @Column(name = "VERSION")
  public int version = 1;


}