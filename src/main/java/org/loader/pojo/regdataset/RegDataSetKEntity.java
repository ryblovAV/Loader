package org.loader.pojo.regdataset;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RegDataSetKEntity {
  protected RegDataSetKEntity() {
  }
  public RegDataSetKEntity(int envId) {
    this.envId = envId;
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof RegDataSetKEntity))
      return false;

    RegDataSetKEntity other = (RegDataSetKEntity) object;

    if (this.envId != other.envId) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + envId;

    return hash;
  }




  @Column(name = "ENV_ID")
  public int envId;



}