package org.loader.pojo.mr;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MrKEntity {
  protected MrKEntity() {
  }
  public MrKEntity(int envId) {
    this.envId = envId;
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof MrKEntity))
      return false;

    MrKEntity other = (MrKEntity) object;

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