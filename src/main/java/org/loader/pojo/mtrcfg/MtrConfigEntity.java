package org.loader.pojo.mtrcfg;

import org.loader.pojo.mr.MrEntity;
import org.loader.pojo.mtr.MtrEntity;
import org.loader.pojo.spmtrhist.SpMtrHistEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_MTR_CONFIG", schema = "RUSADM")
public class MtrConfigEntity {

    @ElementCollection
    @CollectionTable(name = "CI_MTR_CONFIG_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "MTR_CONFIG_ID"))
    public Set<MtrConfigKEntity> mtrConfigKEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "mtrConfig", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<MrEntity> mrMtrConfigEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "mtrCfg", cascade = CascadeType.ALL)
    public Set<SpMtrHistEntity> spMtrHistMtrConfigEntitySet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MTR_ID")
    public MtrEntity mtr;

    @Id
    @Column(name = "MTR_CONFIG_ID", columnDefinition = "char", length = 10)
    public String mtrConfigId;

    @Column(name = "EFF_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date effDttm;
    @Column(name = "MTR_CONFIG_TY_CD", columnDefinition = "char", length = 12)
    public String mtrConfigTyCd = " ";
    @Column(name = "VERSION")
    public int version = 1;


    protected MtrConfigEntity() {
    }


    public MtrConfigEntity(int envId) {
        mtrConfigKEntitySet.add(new MtrConfigKEntity(envId));
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof MtrConfigEntity))
            return false;

        MtrConfigEntity other = (MtrConfigEntity) object;

        if (!this.mtrConfigId.equals(other.mtrConfigId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + mtrConfigId.hashCode();

        return hash;
    }


}