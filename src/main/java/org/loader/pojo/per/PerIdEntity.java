package org.loader.pojo.per;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PerIdEntity {

    @Column(name = "ID_TYPE_CD", columnDefinition = "char", length = 8)
    public String idTypeCd;
    @Column(name = "PER_ID_NBR", length = 100)
    public String perIdNbr = " ";
    @Column(name = "PRIM_SW", columnDefinition = "char", length = 1)
    public String primSw = " ";
    @Column(name = "VERSION")
    public int version = 1;
    @Column(name = "ENCR_PER_ID_NBR", length = 172)
    public String encrPerIdNbr;
    @Column(name = "HASH_PER_ID_NBR", length = 88)
    public String hashPerIdNbr;

    protected PerIdEntity() {
    }

    public PerIdEntity(
            String idTypeCd,
            String perIdNbr,
            String primSw) {

        this.idTypeCd = idTypeCd;
        this.perIdNbr = perIdNbr;
        this.primSw = primSw;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof PerIdEntity))
            return false;

        PerIdEntity other = (PerIdEntity) object;

        if (!this.idTypeCd.equals(other.idTypeCd)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + idTypeCd.hashCode();
        return hash;
    }
}