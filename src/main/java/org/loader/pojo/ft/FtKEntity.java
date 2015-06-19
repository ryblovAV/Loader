package org.loader.pojo.ft;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FtKEntity {

  @Column(name = "ENV_ID")
  public int envId;

  protected FtKEntity() {
  }

  public FtKEntity(int envId) {
    this.envId = envId;
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof FtKEntity))
      return false;

    FtKEntity other = (FtKEntity) object;

    if (this.envId != other.envId) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return  envId;
  }

}