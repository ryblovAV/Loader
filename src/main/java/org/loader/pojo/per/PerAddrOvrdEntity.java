package org.loader.pojo.per;

import org.loader.pojo.acct.AcctEntity;

import javax.persistence.*;

@Embeddable
public class PerAddrOvrdEntity {
  public PerAddrOvrdEntity() {
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof PerAddrOvrdEntity))
      return false;

    PerAddrOvrdEntity other = (PerAddrOvrdEntity) object;

    if (!this.acct.acctId.equals(other.acct.acctId)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return acct.acctId.hashCode();
  }

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ACCT_ID")
  public AcctEntity acct;

  @Column(name = "ADDRESS1", length = 254)
  public String address1 = " ";

  @Column(name = "ADDRESS2", length = 254)
  public String address2 = " ";

  @Column(name = "ADDRESS3", length = 254)
  public String address3 = " ";

  @Column(name = "ADDRESS4", length = 254)
  public String address4 = " ";

  @Column(name = "CITY", length = 90)
  public String city = " ";

  @Column(name = "COUNTRY", columnDefinition = "char", length = 3)
  public String country = " ";

  @Column(name = "COUNTY", length = 90)
  public String county = " ";

  @Column(name = "GEO_CODE", columnDefinition = "char", length = 11)
  public String geoCode = " ";

  @Column(name = "HOUSE_TYPE", columnDefinition = "char", length = 2)
  public String houseType = " ";

  @Column(name = "IN_CITY_LIMIT", columnDefinition = "char", length = 1)
  public String inCityLimit = " ";

  @Column(name = "NUM1", columnDefinition = "char", length = 6)
  public String num1 = " ";

  @Column(name = "NUM2", columnDefinition = "char", length = 4)
  public String num2 = " ";

  @Column(name = "POSTAL", columnDefinition = "char", length = 12)
  public String postal = " ";

  @Column(name = "STATE", columnDefinition = "char", length = 6)
  public String state = " ";

  @Column(name = "VERSION")
  public int version = 1;


}