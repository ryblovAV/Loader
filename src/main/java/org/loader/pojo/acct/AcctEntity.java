
package org.loader.pojo.acct;

import org.loader.pojo.acctapay.AcctApayEntity;
import org.loader.pojo.prem.PremEntity;
import org.loader.pojo.sa.SaEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
        

@Entity
@Table(name = "CI_ACCT", schema = "RUSADM")
public class AcctEntity {

  protected AcctEntity() {
  }

  public AcctEntity(int envId) {
    acctKEntitySet.add(new AcctKEntity(envId));
  }

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "CI_ACCT_CHAR", schema = "RUSADM", joinColumns = @JoinColumn(name = "ACCT_ID"))
  public Set<AcctCharEntity> acctCharEntitySet = new HashSet<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "CI_ACCT_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "ACCT_ID"))
  public Set<AcctKEntity> acctKEntitySet = new HashSet<>();

  @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
  public Set<AcctApayEntity> acctApayEntitySet = new HashSet<>();

  @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<SaEntity> saEntitySet = new HashSet<>();

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof AcctEntity))
       return false;

    AcctEntity other = (AcctEntity) object;
    
    if (!this.acctId.equals(other.acctId)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + acctId.hashCode();
    return hash;
  }

  @Id
  @Column(name = "ACCT_ID", columnDefinition = "char", length = 10)
  public String acctId;

  @Column(name = "BILL_CYC_CD", columnDefinition = "char", length = 4)
  public String billCycCd = "M-D1";

  @Column(name = "SETUP_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date setupDt;

  @Column(name = "CURRENCY_CD", columnDefinition = "char", length = 3)
  public String currencyCd = "RUR";

  @Column(name = "ACCT_MGMT_GRP_CD", columnDefinition = "char", length = 10)
  public String acctMgmtGrpCd = "NORMAL";

  @Column(name = "ALERT_INFO", length = 4000)
  public String alertInfo = " ";

  @Column(name = "BILL_AFTER_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date billAfterDt;

  @Column(name = "PROTECT_CYC_SW", columnDefinition = "char", length = 1)
  public String protectCycSw = "Y";

  @Column(name = "CIS_DIVISION", columnDefinition = "char", length = 5)
  public String cisDivision = " ";

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MAILING_PREM_ID", nullable = true, updatable = false)
  public PremEntity mailingPrem;

  @Column(name = "PROTECT_PREM_SW", columnDefinition = "char", length = 1)
  public String protectPremSw = "N";

  @Column(name = "COLL_CL_CD", columnDefinition = "char", length = 10)
  public String collClCd = "COM";

  @Column(name = "CR_REVIEW_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date crReviewDt;

  @Column(name = "POSTPONE_CR_RVW_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date postponeCrRvwDt;

  @Column(name = "INT_CR_REVIEW_SW", columnDefinition = "char", length = 1)
  public String intCrReviewSw = "N";

  @Column(name = "CUST_CL_CD", columnDefinition = "char", length = 8)
  public String custClCd = " ";

  @Column(name = "BILL_PRT_INTERCEPT", columnDefinition = "char", length = 8)
  public String billPrtIntercept = " ";

  @Column(name = "NO_DEP_RVW_SW", columnDefinition = "char", length = 1)
  public String noDepRvwSw = "N";

  @Column(name = "BUD_PLAN_CD", columnDefinition = "char", length = 8)
  public String budPlanCd = " ";

  @Column(name = "VERSION")
  public int version = 1;

  @Column(name = "PROTECT_DIV_SW", columnDefinition = "char", length = 1)
  public String protectDivSw = "Y";

  @Column(name = "ACCESS_GRP_CD", columnDefinition = "char", length = 12)
  public String accessGrpCd = " ";

  public void addSaEntity(SaEntity saEntity) {
      saEntitySet.add(saEntity);
      saEntity.account = this;
  }

}