package org.loader.pojo.sa;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class SaRsHistEntity {

  public SaRsHistEntity() {
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof SaRsHistEntity))
      return false;

    SaRsHistEntity other = (SaRsHistEntity) object;

    return this.effdt.equals(other.effdt);
  }

  @Override
  public int hashCode() {
    return effdt.hashCode();
  }

  @Column(name = "EFFDT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date effdt;

  @Column(name = "RS_CD", columnDefinition = "char", length = 8)
  public String rsCd = " ";

  @Column(name = "VERSION")
  public int version = 1;

}