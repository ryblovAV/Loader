package org.loader.pojo.reg;

import org.loader.pojo.mtr.MtrEntity;
import org.loader.pojo.regdataset.RegDataSetEntity;
import org.loader.pojo.regread.RegReadEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_REG", schema = "STGADM")
public class RegEntity {
  protected RegEntity() {
  }
  public RegEntity(int envId) {
    regKEntitySet.add(new RegKEntity(envId));
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof RegEntity))
      return false;

    RegEntity other = (RegEntity) object;

    if (!this.regId.equals(other.regId)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + regId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_REG_K", schema = "STGADM", joinColumns = @JoinColumn(name = "REG_ID"))
  public Set<RegKEntity> regKEntitySet = new HashSet<>();
     
  @ElementCollection
  @CollectionTable(name = "CI_REG_CHAR", schema = "STGADM", joinColumns = @JoinColumn(name = "REG_ID"))
  public Set<RegCharEntity> regCharEntitySet = new HashSet<>();
     
  @OneToMany(mappedBy = "reg", cascade = CascadeType.ALL)
  public Set<RegDataSetEntity> regDataSetRegEntitySet = new HashSet<>();

  @OneToMany(mappedBy = "reg", cascade = CascadeType.ALL)
  public Set<RegReadEntity> regReadRegEntitySet = new HashSet<>();

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MTR_ID")
  public MtrEntity mtr;

  @Id
  @Column(name = "REG_ID", columnDefinition = "char", length = 10)
  public String regId;


  @Column(name = "CONSUM_SUB_FLG", columnDefinition = "char", length = 2)
  public String consumSubFlg = " ";

  @Column(name = "EFF_DTTM")
  @Temporal(TemporalType.TIMESTAMP)
  public Date effDttm;

  @Column(name = "FULL_SCALE")
  public double fullScale = 0;

  @Column(name = "HOW_TO_USE_FLG", columnDefinition = "char", length = 2)
  public String howToUseFlg = " ";

  @Column(name = "INTV_REG_TYPE_CD", columnDefinition = "char", length = 10)
  public String intvRegTypeCd = " ";

  @Column(name = "NBR_OF_DGTS_LFT")
  public int nbrOfDgtsLft = 0;

  @Column(name = "NBR_OF_DGTS_RGT")
  public int nbrOfDgtsRgt = 0;

  @Column(name = "PROTOCOL_CD", columnDefinition = "char", length = 8)
  public String protocolCd = " ";

  @Column(name = "READ_OUT_TYPE_CD", columnDefinition = "char", length = 8)
  public String readOutTypeCd = " ";

  @Column(name = "READ_SEQ")
  public int readSeq = 0;

  @Column(name = "REG_CONST")
  public double regConst = 0;

  @Column(name = "TOLERANCE")
  public double tolerance = 0;

  @Column(name = "TOU_CD", columnDefinition = "char", length = 8)
  public String touCd = " ";

  @Column(name = "UOM_CD", columnDefinition = "char", length = 4)
  public String uomCd = " ";

  @Column(name = "VERSION")
  public int version = 1;

  @Column(name = "VIRTUAL_CHAN_ID", length = 60)
  public String virtualChanId = " ";


}