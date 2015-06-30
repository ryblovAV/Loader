package org.loader.pojo.sasp;

import org.loader.pojo.mr.MrEntity;
import org.loader.pojo.sa.SaEntity;
import org.loader.pojo.sp.SpEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_SA_SP", schema = "RUSADM")
public class SaSpEntity {

    @ElementCollection
    @CollectionTable(name = "CI_SA_SP_CHAR", schema = "RUSADM", joinColumns = @JoinColumn(name = "SA_SP_ID"))
    public Set<SaSpCharEntity> saSpCharEntitySet = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "CI_SA_SP_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "SA_SP_ID"))
    public Set<SaSpKEntity> saSpKEntitySet = new HashSet<>();

    //TODO add stopMr
//    @ManyToOne
//    @JoinColumn(name = "STOP_MR_ID")
//    public MrEntity stopMr;

    @ManyToOne(cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "START_MR_ID")
    public MrEntity startMr;

    @ManyToOne
    @JoinColumn(name = "SA_ID")
    public SaEntity sa;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "SP_ID")
    public SpEntity sp;

    @Id
    @Column(name = "SA_SP_ID", columnDefinition = "char", length = 10)
    public String saSpId;

    @Column(name = "START_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date startDttm;

    @Column(name = "STOP_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date stopDttm;

    @Column(name = "USAGE_FLG", columnDefinition = "char", length = 2)
    public String usageFlg = " ";

    @Column(name = "USE_PCT")
    public int usePct = 0;

    @Column(name = "VERSION")
    public int version = 1;

    protected SaSpEntity() {
    }

    public SaSpEntity(int envId, String saSpId, SaEntity sa, SpEntity sp) {
        saSpKEntitySet.add(new SaSpKEntity(envId));
        this.saSpId = saSpId;
        this.sa = sa;
        this.sp = sp;
        sa.saSpEntitySet.add(this);
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof SaSpEntity))
            return false;

        SaSpEntity other = (SaSpEntity) object;

        if (!this.saSpId.equals(other.saSpId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + saSpId.hashCode();
        return hash;
    }


}