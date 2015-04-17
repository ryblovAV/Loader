package org.loader.pojo.acctper;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AcctPerEntityK implements Serializable {

    @Column(name = "ACCT_ID", columnDefinition = "char", length = 10)
    public String acctId;

    @Column(name = "PER_ID", columnDefinition = "char", length = 10)
    public String perId;

    public AcctPerEntityK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcctPerEntityK)) return false;

        AcctPerEntityK id = (AcctPerEntityK) o;

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
