package org.loader.pojo.tndr;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TndrCtlStPk implements Serializable {

    public DepCtlStPk depCtlStId;

    @Column(name = "EXT_BATCH_ID", columnDefinition = "char", length = 30)
    public String extBatchId;

    protected TndrCtlStPk() {
    }

    public TndrCtlStPk(DepCtlStPk depCtlStId, String extBatchId) {
        this.depCtlStId = depCtlStId;
        this.extBatchId = extBatchId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof TndrCtlStPk))
            return false;

        TndrCtlStPk other = (TndrCtlStPk) object;

        if (!this.depCtlStId.equals(other.depCtlStId)) {
            return false;
        }

        if (!this.extBatchId.equals(other.extBatchId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + depCtlStId.hashCode();
        hash = 31 * hash + extBatchId.hashCode();

        return hash;
    }

}
