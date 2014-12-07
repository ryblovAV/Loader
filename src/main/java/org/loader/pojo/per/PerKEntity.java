package org.loader.pojo.per;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PerKEntity {

    @Column(name = "ENV_ID")
    public int envId;

    protected PerKEntity() {
    }

    public PerKEntity(int envId) {
        this.envId = envId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof PerKEntity))
            return false;

        PerKEntity other = (PerKEntity) object;

        if (this.envId != other.envId) {
            return false;
        }


        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + envId;
        return hash;
    }
}