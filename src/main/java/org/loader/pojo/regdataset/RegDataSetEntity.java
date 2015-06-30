package org.loader.pojo.regdataset;

import org.loader.pojo.reg.RegEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_REG_DATA_SET", schema = "RUSADM")
public class RegDataSetEntity {
  protected RegDataSetEntity() {
  }
  public RegDataSetEntity(int envId) {
    regDataSetKEntitySet.add(new RegDataSetKEntity(envId));
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof RegDataSetEntity))
      return false;

    RegDataSetEntity other = (RegDataSetEntity) object;

    if (!this.regDataSetId.equals(other.regDataSetId)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + regDataSetId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_REG_DATA_SET_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "REG_DATA_SET_ID"))
  public Set<RegDataSetKEntity> regDataSetKEntitySet = new HashSet<>();
     
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "REG_ID")
  public RegEntity reg;

  @Id
  @Column(name = "REG_DATA_SET_ID", columnDefinition = "char", length = 12)
  public String regDataSetId;


  @Column(name = "REG_DS_EXT_ID", length = 60)
  public String regDsExtId = " ";

  @Column(name = "REG_DS_STATUS_FLG", columnDefinition = "char", length = 2)
  public String regDsStatusFlg = " ";

  @Column(name = "REG_DS_TYPE_FLG", columnDefinition = "char", length = 4)
  public String regDsTypeFlg = " ";

  @Column(name = "SET_DTTM")
  @Temporal(TemporalType.TIMESTAMP)
  public Date setDttm;

  @Column(name = "VERSION")
  public int version = 1;


}