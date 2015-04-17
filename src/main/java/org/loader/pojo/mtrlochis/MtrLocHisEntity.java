package org.loader.pojo.mtrlochis;


import org.loader.pojo.mtr.MtrEntity;
import org.loader.pojo.spmtrhist.SpMtrHistEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_MTR_LOC_HIS", schema = "STGADM")
public class MtrLocHisEntity {

    @ElementCollection
    @CollectionTable(name = "CI_MTR_LOC_HIS_K", schema = "STGADM", joinColumns = @JoinColumn(name = "MTR_LOC_HIST_ID"))
    public Set<MtrLocHisKEntity> mtrLocHisKEntitySet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SP_MTR_HIST_ID")
    public SpMtrHistEntity spMtrHist;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MTR_ID")
    public MtrEntity mtr;

    @Id
    @Column(name = "MTR_LOC_HIST_ID", columnDefinition = "char", length = 12)
    public String mtrLocHistId;

    @Column(name = "LOC_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date locDttm;

    @Column(name = "LOC_HIST_TYP_FLG", columnDefinition = "char", length = 4)
    public String locHistTypFlg = " ";

    @Column(name = "STK_LOC_CD", columnDefinition = "char", length = 12)
    public String stkLocCd = " ";

    @Column(name = "VERSION")
    public int version = 1;


    protected MtrLocHisEntity() {
    }

    public MtrLocHisEntity(int envId) {
        mtrLocHisKEntitySet.add(new MtrLocHisKEntity(envId));
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof MtrLocHisEntity))
            return false;

        MtrLocHisEntity other = (MtrLocHisEntity) object;

        if (!this.mtrLocHistId.equals(other.mtrLocHistId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + mtrLocHistId.hashCode();

        return hash;
    }


}