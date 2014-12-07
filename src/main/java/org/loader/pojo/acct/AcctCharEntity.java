
package org.loader.pojo.acct;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class AcctCharEntity {

  public AcctCharEntity() {
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof AcctCharEntity))
       return false;

    AcctCharEntity other = (AcctCharEntity) object;
    
    if (!this.charTypeCd.equals(other.charTypeCd)) {
      return false;
    }

    if (!this.effdt.equals(other.effdt)) {
      return false;
    }

    return true;
  }
    

  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + charTypeCd.hashCode();hash = 31 * hash + effdt.hashCode();
    return hash;
  }
       


  @Column(name = "CHAR_TYPE_CD", columnDefinition = "char", length = 8)
  public String charTypeCd;

  @Column(name = "EFFDT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date effdt;

  @Column(name = "CHAR_VAL", columnDefinition = "char", length = 16)
  public String charVal = " ";

  @Column(name = "ADHOC_CHAR_VAL", length = 254)
  public String adhocCharVal = " ";

  @Column(name = "VERSION")
  public int version = 1;

  @Column(name = "CHAR_VAL_FK1", length = 50)
  public String charValFk1 = " ";

  @Column(name = "CHAR_VAL_FK2", length = 50)
  public String charValFk2 = " ";

  @Column(name = "CHAR_VAL_FK3", length = 50)
  public String charValFk3 = " ";

  @Column(name = "CHAR_VAL_FK4", length = 50)
  public String charValFk4 = " ";

  @Column(name = "CHAR_VAL_FK5", length = 50)
  public String charValFk5 = " ";

  @Column(name = "SRCH_CHAR_VAL", columnDefinition = "char", length = 50)
  public String srchCharVal = " "
   ;
}