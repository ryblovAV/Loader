package org.loader.pojo.per;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PerPhoneEntity {

    @Column(name = "SEQ_NUM")
    public int seqNum;
    @Column(name = "PHONE_TYPE_CD", columnDefinition = "char", length = 12)
    public String phoneTypeCd = " ";
    @Column(name = "COUNTRY_CODE", columnDefinition = "char", length = 3)
    public String countryCode = " ";
    @Column(name = "PHONE", length = 24)
    public String phone = " ";
    @Column(name = "EXTENSION", columnDefinition = "char", length = 6)
    public String extension = " ";
    @Column(name = "VERSION")
    public int version = 1;

    public PerPhoneEntity() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof PerPhoneEntity))
            return false;

        PerPhoneEntity other = (PerPhoneEntity) object;

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