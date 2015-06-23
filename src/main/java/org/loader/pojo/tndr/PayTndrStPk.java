package org.loader.pojo.tndr;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PayTndrStPk implements Serializable {

    public DepCtlStPk depCtlStPk;

    @Column(name = "EXT_BATCH_ID", columnDefinition = "char", length = 30)
    public String extBatchId;

    @Column(name = "EXT_REFERENCE_ID", columnDefinition = "char", length = 36)
    public String extReferenceId;

    protected PayTndrStPk() {
    }

    public PayTndrStPk(DepCtlStPk depCtlStPk, String extBatchId, String extReferenceId) {
        this.depCtlStPk = depCtlStPk;
        this.extBatchId = extBatchId;
        this.extReferenceId = extReferenceId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof PayTndrStPk))
            return false;

        PayTndrStPk other = (PayTndrStPk) object;

        if (!this.depCtlStPk.equals(other.depCtlStPk)) {
            return false;
        }

        if (!this.extBatchId.equals(other.extBatchId)) {
            return false;
        }

        if (!this.extReferenceId.equals(other.extReferenceId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash = 31 * hash + depCtlStPk.hashCode();
        hash = 31 * hash + extBatchId.hashCode();
        hash = 31 * hash + extReferenceId.hashCode();

        return hash;
    }

}
