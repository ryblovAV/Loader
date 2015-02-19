
package org.loader.pojo.acct;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class AcctKEntity {

  protected AcctKEntity() {
  }

  public AcctKEntity(
       int envId){

     this.envId = envId;
  }



  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof AcctKEntity))
       return false;

    AcctKEntity other = (AcctKEntity) object;
    
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