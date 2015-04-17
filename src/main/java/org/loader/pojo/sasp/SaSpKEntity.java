package org.loader.pojo.sasp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SaSpKEntity {

    @Column(name = "ENV_ID")
    public int envId;

    protected SaSpKEntity() {
    }

    public SaSpKEntity(int envId) {
        this.envId = envId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof SaSpKEntity))
            return false;

        SaSpKEntity other = (SaSpKEntity) object;

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
