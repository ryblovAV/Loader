package org.loader.pojo.adj;

import javax.persistence.*;

@Embeddable
public class AdjKEntity {
  protected AdjKEntity() {
  }
  public AdjKEntity(int envId) {
    this.envId = envId;
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof AdjKEntity))
      return false;

    AdjKEntity other = (AdjKEntity) object;

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