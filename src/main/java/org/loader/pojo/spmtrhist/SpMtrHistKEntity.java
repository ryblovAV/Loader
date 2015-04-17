package org.loader.pojo.spmtrhist;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SpMtrHistKEntity {
  protected SpMtrHistKEntity() {
  }
  public SpMtrHistKEntity(int envId) {
    this.envId = envId;
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof SpMtrHistKEntity))
      return false;

    SpMtrHistKEntity other = (SpMtrHistKEntity) object;

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