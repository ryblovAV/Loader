
package org.loader.pojo.acctapay;

import org.loader.pojo.acct.AcctEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_ACCT_APAY", schema = "RUSADM")
public class AcctApayEntity {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_ACCT_APAY_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "ACCT_APAY_ID"))
    public Set<AcctApayKEntity> acctApayKEntitySet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ACCT_ID")
    public AcctEntity account;

    @Id
    @Column(name = "ACCT_APAY_ID", columnDefinition = "char", length = 10)
    public String acctApayId;

    @Column(name = "START_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date startDt;

    @Column(name = "END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date endDt;

    @Column(name = "APAY_SRC_CD", columnDefinition = "char", length = 12)
    public String apaySrcCd = "ALL";

    @Column(name = "EXT_ACCT_ID", length = 50)
    public String extAcctId = " ";

    @Column(name = "EXPIRE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date expireDt;

    @Column(name = "ENTITY_NAME", length = 254)
    public String entityName = " ";

    @Column(name = "COMMENTS", length = 254)
    public String comments = " ";

    @Column(name = "VERSION")
    public int version = 1;

    @Column(name = "APAY_MAX_WDRWL_AMT")
    public int apayMaxWdrwlAmt = 0;

    @Column(name = "APAY_METHOD_FLG", columnDefinition = "char", length = 4)
    public String apayMethodFlg = "C1DD";

    @Column(name = "ENCR_EXT_ACCT_ID", length = 108)
    public String encrExtAcctId;

    protected AcctApayEntity() {
    }

    public AcctApayEntity(int envId) {
        acctApayKEntitySet.add(new AcctApayKEntity(envId));
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof AcctApayEntity))
            return false;

        AcctApayEntity other = (AcctApayEntity) object;

        if (!this.acctApayId.equals(other.acctApayId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + acctApayId.hashCode();
        return hash;
    }

}