
package org.loader.pojo.sa;

import org.loader.pojo.acct.AcctEntity;
import org.loader.pojo.acctper.AcctPerEntity;
import org.loader.pojo.adj.AdjEntity;
import org.loader.pojo.billChg.BillChgEntity;
import org.loader.pojo.ft.FtEntity;
import org.loader.pojo.prem.PremEntity;
import org.loader.pojo.sasp.SaSpEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_SA", schema = "RUSADM")
public class SaEntity {

    protected SaEntity() {
    }

    public SaEntity(int envId) {
        saKEntitySet.add(new SaKEntity(envId));
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_SA_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "SA_ID"))
    public Set<SaKEntity> saKEntitySet = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_SA_CHAR", schema = "RUSADM", joinColumns = @JoinColumn(name = "SA_ID"))
    public Set<SaCharEntity> saCharEntitySet = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_SA_RS_HIST", schema = "RUSADM", joinColumns = @JoinColumn(name = "SA_ID"))
    public Set<SaRsHistEntity> saRsHistEntitySet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CHAR_PREM_ID", nullable = false, updatable = false)
    public PremEntity charPrem;

    @OneToMany(mappedBy="sa",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<SaSpEntity> saSpEntitySet = new HashSet<>();

    @OneToMany(mappedBy="sa",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<AdjEntity> adjEntitySet = new HashSet<>();

    @OneToMany(mappedBy="sa",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<FtEntity> ftEntitySet = new HashSet<>();

    @OneToMany(mappedBy="sa",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<BillChgEntity> billChgEntitySet = new HashSet<>();

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof SaEntity))
            return false;

        SaEntity other = (SaEntity) object;

        return this.saId.equals(other.saId);
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + saId.hashCode();
        return hash;
    }


    @Id
    @Column(name = "SA_ID", columnDefinition = "char", length = 10)
    public String saId;

    @Column(name = "PROP_DCL_RSN_CD", columnDefinition = "char", length = 12)
    public String propDclRsnCd = " ";

    @Column(name = "PROP_SA_ID", columnDefinition = "char", length = 10)
    public String propSaId = " ";

    @Column(name = "CIS_DIVISION", columnDefinition = "char", length = 5)
    public String cisDivision = " ";

    @Column(name = "SA_TYPE_CD", columnDefinition = "char", length = 8)
    public String saTypeCd = " ";

    @Column(name = "START_OPT_CD", columnDefinition = "char", length = 12)
    public String startOptCd = " ";

    @Column(name = "START_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date startDt;

    @Column(name = "SA_STATUS_FLG", columnDefinition = "char", length = 2)
    public String saStatusFlg = " ";

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ACCT_ID")
    public AcctEntity account;

    @Column(name = "END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date endDt;

    @Column(name = "OLD_ACCT_ID", length = 36)
    public String oldAcctId = " ";

    @Column(name = "CUST_READ_FLG", columnDefinition = "char", length = 2)
    public String custReadFlg = " ";

    @Column(name = "ALLOW_EST_SW", columnDefinition = "char", length = 1)
    public String allowEstSw = " ";

    @Column(name = "SIC_CD", columnDefinition = "char", length = 8)
    public String sicCd = " ";

    @Column(name = "TOT_TO_BILL_AMT")
    public int totToBillAmt = 0;

    @Column(name = "CURRENCY_CD", columnDefinition = "char", length = 3)
    public String currencyCd = " ";

    @Column(name = "VERSION")
    public int version = 1;

    @Column(name = "SA_REL_ID", columnDefinition = "char", length = 10)
    public String saRelId = " ";

    @Column(name = "STRT_RSN_FLG", columnDefinition = "char", length = 4)
    public String strtRsnFlg = " ";

    @Column(name = "STOP_RSN_FLG", columnDefinition = "char", length = 4)
    public String stopRsnFlg = " ";

    @Column(name = "STRT_REQED_BY", length = 50)
    public String strtReqedBy = " ";

    @Column(name = "STOP_REQED_BY", length = 50)
    public String stopReqedBy = " ";

    @Column(name = "HIGH_BILL_AMT")
    public int highBillAmt = 0;

    @Column(name = "INT_CALC_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date intCalcDt;

    @Column(name = "CIAC_REVIEW_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date ciacReviewDt;

    @Column(name = "BUS_ACTIVITY_DESC", length = 250)
    public String busActivityDesc = " ";

    @Column(name = "IB_SA_CUTOFF_TM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date ibSaCutoffTm;

    @Column(name = "IB_BASE_TM_DAY_FLG", columnDefinition = "char", length = 2)
    public String ibBaseTmDayFlg = " ";

    @Column(name = "ENRL_ID", columnDefinition = "char", length = 12)
    public String enrlId = " ";

    @Column(name = "SPECIAL_USAGE_FLG", columnDefinition = "char", length = 4)
    public String specialUsageFlg = " ";

    @Column(name = "PROP_SA_STAT_FLG", columnDefinition = "char", length = 2)
    public String propSaStatFlg = " ";

    @Column(name = "NBR_PYMNT_PERIODS")
    public int nbrPymntPeriods = 0;

    @Column(name = "NB_RULE_CD", columnDefinition = "char", length = 8)
    public String nbRuleCd = " ";

    @Column(name = "EXPIRE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date expireDt;

    @Column(name = "RENEWAL_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date renewalDt;

    @Column(name = "NB_APAY_FLG", columnDefinition = "char", length = 4)
    public String nbApayFlg = " ";

}