package org.loader.training;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MasterAddress {

    @Column(name = "CHAR_TYPE_CD", columnDefinition = "char", length = 8)
    public String commStatus;

    public MasterAddress(String commStatus) {
        this.commStatus = commStatus;
    }

    public MasterAddress() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MasterAddress)) return false;

        MasterAddress that = (MasterAddress) o;

        if (!commStatus.equals(that.commStatus)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return commStatus.hashCode();
    }
}
