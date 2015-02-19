
package org.loader.pojo.acctper;

import org.loader.pojo.acct.AcctEntity;
import org.loader.pojo.per.PerEntity;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.io.Serializable;


@Entity
@Table(name = "CI_ACCT_PER", schema = "STGADM")
public class AcctPerEntity {

    @EmbeddedId
    public Id id = new Id();
    @Column(name = "ACCT_REL_TYPE_CD", columnDefinition = "char", length = 8)
    public String acctRelTypeCd = "PRIMARY";
    @Column(name = "BILL_ADDR_SRCE_FLG", columnDefinition = "char", length = 4)
    public String billAddrSrceFlg = " ";
    @Column(name = "MAIN_CUST_SW", columnDefinition = "char", length = 1)
    public String mainCustSw = "Y";
    @Column(name = "FIN_RESP_SW", columnDefinition = "char", length = 1)
    public String finRespSw = "Y";
    @Column(name = "THRD_PTY_SW", columnDefinition = "char", length = 1)
    public String thrdPtySw = "N";
    @Column(name = "RECEIVE_COPY_SW", columnDefinition = "char", length = 1)
    public String receiveCopySw = "Y";
    @Column(name = "BILL_RTE_TYPE_CD", columnDefinition = "char", length = 8)
    public String billRteTypeCd = "POSTAL";
    @Column(name = "BILL_FORMAT_FLG", columnDefinition = "char", length = 2)
    public String billFormatFlg = "D";
    @Column(name = "NBR_BILL_COPIES")
    public int nbrBillCopies = 1;
    @Column(name = "VERSION")
    public int version = 1;
    @Column(name = "CUST_PO_ID", length = 20)
    public String custPoId = " ";
    @Column(name = "NOTIFY_SW", columnDefinition = "char", length = 1)
    public String notifySw = "Y";
    @Column(name = "NAME_PFX_SFX", length = 50)
    public String namePfxSfx = " ";
    @Column(name = "PFX_SFX_FLG", columnDefinition = "char", length = 2)
    public String pfxSfxFlg = " ";
    @Column(name = "QTE_RTE_TYPE_CD", columnDefinition = "char", length = 12)
    public String qteRteTypeCd = " ";
    @Column(name = "RECV_QTE_SW", columnDefinition = "char", length = 1)
    public String recvQteSw = "N";
    @Column(name = "WEB_ACCESS_FLG", columnDefinition = "char", length = 4)
    public String webAccessFlg = "ALWD";

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PER_ID", insertable = false, updatable = false)
    public PerEntity per;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCT_ID", insertable = false, updatable = false)
    public AcctEntity acct;

    public AcctPerEntity() {
    }

    public AcctPerEntity(PerEntity per, AcctEntity acct) {
        this.per = per;
        this.acct = acct;
        this.id.acctId = acct.acctId;
        this.id.perId = per.perId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof AcctPerEntity))
            return false;

        AcctPerEntity other = (AcctPerEntity) object;

        if (!this.id.equals(other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return custPoId.hashCode();
    }

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "ACCT_ID", columnDefinition = "char", length = 10)
        public String acctId;

        @Column(name = "PER_ID", columnDefinition = "char", length = 10)
        public String perId;

        public Id() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id = (Id) o;

            if (!acctId.equals(id.acctId)) return false;
            if (!perId.equals(id.perId)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = acctId.hashCode();
            result = 31 * result + perId.hashCode();
            return result;
        }
    }

}