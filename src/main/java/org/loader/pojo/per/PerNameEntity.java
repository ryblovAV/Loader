package org.loader.pojo.per;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PerNameEntity {

    @Column(name = "SEQ_NUM")
    public int seqNum = 1;
    @Column(name = "ENTITY_NAME", length = 254)
    public String entityName = " ";
    @Column(name = "NAME_TYPE_FLG", columnDefinition = "char", length = 4)
    public String nameTypeFlg = "PRIM";
    @Column(name = "VERSION")
    public int version = 1;
    @Column(name = "PRIM_NAME_SW", columnDefinition = "char", length = 1)
    public String primNameSw = "Y";
    @Column(name = "ENTITY_NAME_UPR", length = 254)
    public String entityNameUpr = " ";

    protected PerNameEntity() {
    }

    public PerNameEntity(
            String entityName) {
        this.entityName = entityName;
        this.entityNameUpr = entityName.toUpperCase();
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof PerNameEntity))
            return false;

        PerNameEntity other = (PerNameEntity) object;

        if (this.seqNum != other.seqNum) {
            return false;
        }


        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + seqNum;
        return hash;
    }
}