
package org.loader.pojo.prem;

import org.loader.pojo.PremKEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_PREM", schema = "RUSADM")
public class PremEntity {

  protected PremEntity() {
  }

  public PremEntity(int envId){
     premKEntitySet.add(new PremKEntity(envId));
  }

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "CI_PREM_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "PREM_ID"))
  public Set<PremKEntity> premKEntitySet = new HashSet<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "CI_PREM_CHAR", schema = "RUSADM", joinColumns = @JoinColumn(name = "PREM_ID"))
  public Set<PremCharEntity> premCharEntitySet = new HashSet<>();

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof PremEntity))
       return false;

    PremEntity other = (PremEntity) object;
    
    if (!this.premId.equals(other.premId)) {
      return false;
    }


    return true;
  }
    
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + premId.hashCode();
    return hash;
  }

  @Id
  @Column(name = "PREM_ID", columnDefinition = "char", length = 10)
  public String premId;

  @Column(name = "PREM_TYPE_CD", columnDefinition = "char", length = 8)
  public String premTypeCd = " ";

  @Column(name = "CIS_DIVISION", columnDefinition = "char", length = 5)
  public String cisDivision = " ";

  @Column(name = "LL_ID", columnDefinition = "char", length = 10)
  public String llId = " ";

  @Column(name = "KEY_SW", columnDefinition = "char", length = 1)
  public String keySw = " ";

  @Column(name = "KEY_ID", columnDefinition = "char", length = 10)
  public String keyId = " ";

  @Column(name = "OK_TO_ENTER_SW", columnDefinition = "char", length = 1)
  public String okToEnterSw = " ";

  @Column(name = "MR_INSTR_CD", columnDefinition = "char", length = 4)
  public String mrInstrCd = " ";

  @Column(name = "MR_INSTR_DETAILS", length = 250)
  public String mrInstrDetails = " ";

  @Column(name = "MR_WARN_CD", columnDefinition = "char", length = 4)
  public String mrWarnCd = " ";

  @Column(name = "TREND_AREA_CD", columnDefinition = "char", length = 8)
  public String trendAreaCd = " ";

  @Column(name = "ADDRESS1", length = 254)
  public String address1 = " ";

  @Column(name = "ADDRESS2", length = 254)
  public String address2 = " ";

  @Column(name = "ADDRESS3", length = 254)
  public String address3 = " ";

  @Column(name = "ADDRESS4", length = 254)
  public String address4 = " ";

  @Column(name = "MAIL_ADDR_SW", columnDefinition = "char", length = 1)
  public String mailAddrSw = " ";

  @Column(name = "CITY", length = 90)
  public String city = " ";

  @Column(name = "NUM1", columnDefinition = "char", length = 6)
  public String num1 = " ";

  @Column(name = "NUM2", columnDefinition = "char", length = 4)
  public String num2 = " ";

  @Column(name = "COUNTY", length = 90)
  public String county = " ";

  @Column(name = "POSTAL", columnDefinition = "char", length = 12)
  public String postal = " ";

  @Column(name = "HOUSE_TYPE", columnDefinition = "char", length = 2)
  public String houseType = " ";

  @Column(name = "GEO_CODE", columnDefinition = "char", length = 11)
  public String geoCode = " ";

  @Column(name = "IN_CITY_LIMIT", columnDefinition = "char", length = 1)
  public String inCityLimit = " ";

  @Column(name = "STATE", columnDefinition = "char", length = 6)
  public String state = " ";

  @Column(name = "COUNTRY", columnDefinition = "char", length = 3)
  public String country = " ";

  @Column(name = "VERSION")
  public int version = 1;

  @Column(name = "ADDRESS1_UPR", length = 254)
  public String address1Upr = " ";

  @Column(name = "CITY_UPR", length = 90)
  public String cityUpr = " ";

  @Column(name = "TIME_ZONE_CD", columnDefinition = "char", length = 10)
  public String timeZoneCd = " ";

  @Column(name = "LS_SL_FLG", columnDefinition = "char", length = 2)
  public String lsSlFlg = " ";

  @Column(name = "LS_SL_DESCR", length = 1000)
  public String lsSlDescr = " ";

  @Column(name = "PRNT_PREM_ID", columnDefinition = "char", length = 10)
  public String prntPremId = " ";
}