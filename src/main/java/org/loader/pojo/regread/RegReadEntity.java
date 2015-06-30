package org.loader.pojo.regread;

import org.loader.pojo.mr.MrEntity;
import org.loader.pojo.reg.RegEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_REG_READ", schema = "RUSADM")
public class RegReadEntity {
  protected RegReadEntity() {
  }
  public RegReadEntity(int envId) {
    regReadKEntitySet.add(new RegReadKEntity(envId));
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof RegReadEntity))
      return false;

    RegReadEntity other = (RegReadEntity) object;

    if (!this.regReadId.equals(other.regReadId)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + regReadId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_REG_READ_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "REG_READ_ID"))
  public Set<RegReadKEntity> regReadKEntitySet = new HashSet<>();
     
//  @OneToMany(mappedBy = "endRegRead", cascade = CascadeType.ALL)
//  public Set<BsegReadEntity> bsegReadEndRegReadEntitySet = new HashSet<>();

//  @OneToMany(mappedBy = "startRegRead", cascade = CascadeType.ALL)
//  public Set<BsegReadEntity> bsegReadStartRegReadEntitySet = new HashSet<>();

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "REG_ID")
  public RegEntity reg;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MR_ID")
  public MrEntity mr;

  @Id
  @Column(name = "REG_READ_ID", columnDefinition = "char", length = 12)
  public String regReadId;


  @Column(name = "HI_LIMIT")
  public double hiLimit = 0;

  @Column(name = "LO_LIMIT")
  public double loLimit = 0;

  @Column(name = "READ_SEQ")
  public int readSeq = 0;

  @Column(name = "READ_TYPE_FLG", columnDefinition = "char", length = 2)
  public String readTypeFlg = " ";

  @Column(name = "REG_READING")
  public double regReading = 0;

  @Column(name = "REVIEW_HILO_SW", columnDefinition = "char", length = 1)
  public String reviewHiloSw = " ";

  @Column(name = "TRENDED_SW", columnDefinition = "char", length = 1)
  public String trendedSw = " ";

  @Column(name = "VERSION")
  public int version = 1;


}