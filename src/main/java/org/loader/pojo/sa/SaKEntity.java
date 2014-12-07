
package org.loader.pojo.sa;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class SaKEntity {

    protected SaKEntity() {
    }

    public SaKEntity(int envId) {
        this.envId = envId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof SaKEntity))
            return false;

        SaKEntity other = (SaKEntity) object;

        return this.envId != other.envId;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + envId;
        return hash;
    }


    @Column(name = "ENV_ID")
    public int envId;
}