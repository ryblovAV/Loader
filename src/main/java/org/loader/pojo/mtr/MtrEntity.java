package org.loader.pojo.mtr;

import org.loader.pojo.mtrcfg.MtrConfigEntity;
import org.loader.pojo.mtrlochis.MtrLocHisEntity;
import org.loader.pojo.reg.RegEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_MTR", schema = "RUSADM")
public class MtrEntity {

  protected MtrEntity() {
  }
  public MtrEntity(int envId) {
    mtrKEntitySet.add(new MtrKEntity(envId));
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof MtrEntity))
      return false;

    MtrEntity other = (MtrEntity) object;

    if (!this.mtrId.equals(other.mtrId)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + mtrId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_MTR_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "MTR_ID"))
  public Set<MtrKEntity> mtrKEntitySet = new HashSet<>();
     
  @ElementCollection
  @CollectionTable(name = "CI_MTR_CHAR", schema = "RUSADM", joinColumns = @JoinColumn(name = "MTR_ID"))
  public Set<MtrCharEntity> mtrCharEntitySet = new HashSet<>();
     

  @OneToMany(mappedBy = "mtr", cascade = CascadeType.ALL)
  public Set<MtrConfigEntity> mtrConfigMtrEntitySet = new HashSet<>();

  @OneToMany(mappedBy = "mtr", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<RegEntity> regMtrEntitySet = new HashSet<>();

  @OneToMany(mappedBy = "mtr", cascade = CascadeType.ALL)
  public Set<MtrLocHisEntity> mtrLocHisMtrEntitySet = new HashSet<>();

  @Id
  @Column(name = "MTR_ID", columnDefinition = "char", length = 10)
  public String mtrId;

  @Column(name = "BADGE_NBR", columnDefinition = "char", length = 30)
  public String badgeNbr = " ";

  @Column(name = "DESCRLONG", length = 4000)
  public String descrlong = " ";

  @Column(name = "MFG_CD", columnDefinition = "char", length = 8)
  public String mfgCd = " ";

  @Column(name = "MODEL_CD", columnDefinition = "char", length = 8)
  public String modelCd = " ";

  @Column(name = "MTR_STATUS_FLG", columnDefinition = "char", length = 2)
  public String mtrStatusFlg = " ";

  @Column(name = "MTR_TYPE_CD", columnDefinition = "char", length = 8)
  public String mtrTypeCd = " ";

  @Column(name = "RECEIVE_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date receiveDt;

  @Column(name = "RETIRE_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date retireDt;

  @Column(name = "RETIRE_RSN_CD", columnDefinition = "char", length = 10)
  public String retireRsnCd = " ";

  @Column(name = "SERIAL_NBR", length = 16)
  public String serialNbr = " ";

  @Column(name = "VERSION")
  public int version = 1;


}