package org.loader.pojo.billChg;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillChgKEntity {

  protected BillChgKEntity() {
  }

  public BillChgKEntity(int envId) {
    this.envId = envId;
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof BillChgKEntity))
      return false;

    BillChgKEntity other = (BillChgKEntity) object;

    return this.envId != other.envId;
  }

  @Override
  public int hashCode() {
    return envId;
  }

  @Column(name = "ENV_ID")
  public int envId;

}