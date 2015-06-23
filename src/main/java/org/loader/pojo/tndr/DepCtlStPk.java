package org.loader.pojo.tndr;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DepCtlStPk implements Serializable {

    @Column(name = "EXT_SOURCE_ID", columnDefinition = "char", length = 30)
    public String extSourceId;

    @Column(name = "EXT_TRANSMIT_ID", columnDefinition = "char", length = 30)
    public String extTransmitId;

    protected DepCtlStPk() {

    }

    public DepCtlStPk(String extSourceId, String extTransmitId) {
        this.extSourceId = extSourceId;
        this.extTransmitId = extTransmitId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof DepCtlStPk))
            return false;

        DepCtlStPk other = (DepCtlStPk) object;

        if (!this.extSourceId.equals(other.extSourceId)) {
            return false;
        }
        if (!this.extTransmitId.equals(other.extTransmitId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + extSourceId.hashCode();
        hash = 31 * hash + extTransmitId.hashCode();
        return hash;
    }

}
