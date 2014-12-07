package org.loader.pojo.sp;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class SpKEntity {

    @Column(name = "ENV_ID")
    public int envId;

    protected SpKEntity() {
    }

    public SpKEntity(int envId) {
        this.envId = envId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof SpKEntity))
            return false;

        SpKEntity other = (SpKEntity) object;

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