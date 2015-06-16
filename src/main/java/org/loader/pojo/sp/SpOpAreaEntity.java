package org.loader.pojo.sp;

import javax.persistence.*;

@Embeddable
public class SpOpAreaEntity {

  @Column(name = "FS_CL_CD", columnDefinition = "char", length = 5)
  public String fsClCd;
  @Column(name = "OP_AREA_CD", columnDefinition = "char", length = 8)
  public String opAreaCd = " ";
  @Column(name = "VERSION")
  public int version = 1;

  public SpOpAreaEntity() {
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof SpOpAreaEntity))
      return false;

    SpOpAreaEntity other = (SpOpAreaEntity) object;

    if (!this.fsClCd.equals(other.fsClCd)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + fsClCd.hashCode();
    return hash;
  }

}