package org.loader.pojo.mtrlochis;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MtrLocHisKEntity {
    @Column(name = "ENV_ID")
    public int envId;

    protected MtrLocHisKEntity() {
    }

    public MtrLocHisKEntity(int envId) {
        this.envId = envId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof MtrLocHisKEntity))
            return false;

        MtrLocHisKEntity other = (MtrLocHisKEntity) object;

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