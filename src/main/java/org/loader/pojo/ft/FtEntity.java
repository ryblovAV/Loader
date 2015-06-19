package org.loader.pojo.ft;

import org.loader.pojo.sa.SaEntity;
import org.loader.pojo.sa.SaKEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_FT", schema = "STGADM")
public class FtEntity {

    @ElementCollection
    @CollectionTable(name = "CI_FT_K", schema = "STGADM", joinColumns = @JoinColumn(name = "FT_ID"))
    public Set<FtKEntity> ftKEntitySet = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_FT_GL", schema = "STGADM", joinColumns = @JoinColumn(name = "FT_ID"))
    public Set<SaKEntity> ftGlEntitySet = new HashSet<>();

    @Id
    @Column(name = "FT_ID", columnDefinition = "char", length = 12)
    public String ftId;

    protected FtEntity() {
    }

    public FtEntity(int envId) {
        ftKEntitySet.add(new FtKEntity(envId));
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof FtEntity))
            return false;

        FtEntity other = (FtEntity) object;

        if (!this.ftId.equals(other.ftId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ftId.hashCode();
    }

//  @ManyToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "BILL_ID")
//  public BillEntity bill;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "SA_ID")
    public SaEntity sa;

    @Column(name = "ACCOUNTING_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date accountingDt;

    @Column(name = "ARS_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date arsDt;

    @Column(name = "BAL_CTL_GRP_ID")
    public int balCtlGrpId = 0;

    @Column(name = "CIS_DIVISION", columnDefinition = "char", length = 5)
    public String cisDivision = " ";

    @Column(name = "CORRECTION_SW", columnDefinition = "char", length = 1)
    public String correctionSw = " ";

    @Column(name = "CRE_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date creDttm;

    @Column(name = "CURRENCY_CD", columnDefinition = "char", length = 3)
    public String currencyCd = " ";

    @Column(name = "CUR_AMT")
    public double curAmt = 0;

    @Column(name = "FREEZE_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date freezeDttm;

    @Column(name = "FREEZE_SW", columnDefinition = "char", length = 1)
    public String freezeSw = " ";

    @Column(name = "FREEZE_USER_ID", columnDefinition = "char", length = 8)
    public String freezeUserId = " ";

    @Column(name = "FT_TYPE_FLG", columnDefinition = "char", length = 2)
    public String ftTypeFlg = " ";

    @Column(name = "GL_DISTRIB_STATUS", columnDefinition = "char", length = 1)
    public String glDistribStatus = " ";

    @Column(name = "GL_DIVISION", columnDefinition = "char", length = 5)
    public String glDivision = " ";

    @Column(name = "MATCH_EVT_ID", columnDefinition = "char", length = 12)
    public String matchEvtId = " ";

    @Column(name = "NEW_DEBIT_SW", columnDefinition = "char", length = 1)
    public String newDebitSw = " ";

    @Column(name = "NOT_IN_ARS_SW", columnDefinition = "char", length = 1)
    public String notInArsSw = " ";

    @Column(name = "PARENT_ID", columnDefinition = "char", length = 12)
    public String parentId = " ";

    @Column(name = "REDUNDANT_SW", columnDefinition = "char", length = 1)
    public String redundantSw = " ";

    @Column(name = "SCHED_DISTRIB_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date schedDistribDt;

    @Column(name = "SHOW_ON_BILL_SW", columnDefinition = "char", length = 1)
    public String showOnBillSw = " ";

    @Column(name = "SIBLING_ID", columnDefinition = "char", length = 12)
    public String siblingId = " ";

    @Column(name = "TOT_AMT")
    public double totAmt = 0;

    @Column(name = "VERSION")
    public int version = 1;

    @Column(name = "XFERRED_OUT_SW", columnDefinition = "char", length = 1)
    public String xferredOutSw = " ";

    @Column(name = "XFER_TO_GL_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date xferToGlDt;


}