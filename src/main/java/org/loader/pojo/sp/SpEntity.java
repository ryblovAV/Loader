package org.loader.pojo.sp;

import org.loader.pojo.prem.PremEntity;
import org.loader.pojo.sasp.SaSpEntity;
import org.loader.pojo.spmtrhist.SpMtrHistEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "CI_SP", schema = "STGADM")
public class SpEntity {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_SP_CHAR", schema = "STGADM", joinColumns = @JoinColumn(name = "SP_ID"))
    public Set<SpCharEntity> spCharEntitySet = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_SP_K", schema = "STGADM", joinColumns = @JoinColumn(name = "SP_ID"))
    public Set<SpKEntity> spKEntitySet = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_SP_OP_AREA", schema = "STGADM", joinColumns = @JoinColumn(name = "SP_ID"))
    public Set<SpOpAreaEntity> spOpAreaEntitySet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PREM_ID", nullable = false, updatable = false)
    public PremEntity prem;

    @OneToMany(mappedBy="sp",cascade = CascadeType.REMOVE)
    public Set<SaSpEntity> saSpEntitySet = new HashSet<>();

    @OneToMany(mappedBy="sp",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<SpMtrHistEntity> spMtrHistEntity = new HashSet<>();

    @Id
    @Column(name = "SP_ID", columnDefinition = "char", length = 10)
    public String spId;
    @Column(name = "DISCON_LOC_CD", columnDefinition = "char", length = 4)
    public String disconLocCd = " ";
    @Column(name = "SP_TYPE_CD", columnDefinition = "char", length = 8)
    public String spTypeCd = " ";
    @Column(name = "SP_STATUS_FLG", columnDefinition = "char", length = 2)
    public String spStatusFlg = "R";
    @Column(name = "INSTALL_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date installDt;
    @Column(name = "SP_SRC_STATUS_FLG", columnDefinition = "char", length = 2)
    public String spSrcStatusFlg = " ";
    @Column(name = "MR_CYC_CD", columnDefinition = "char", length = 16)
    public String mrCycCd = "C1";
    @Column(name = "MR_RTE_CD", columnDefinition = "char", length = 16)
    public String mrRteCd = "1";
    @Column(name = "MTR_LOC_CD", columnDefinition = "char", length = 4)
    public String mtrLocCd = "OLD";
    @Column(name = "MR_CYC_RTE_SEQ")
    public int mrCycRteSeq = 0;
    @Column(name = "FAC_LVL_1_CD", columnDefinition = "char", length = 8)
    public String facLvl1Cd = " ";
    @Column(name = "MTR_LOC_DETAILS", length = 4000)
    public String mtrLocDetails = " ";
    @Column(name = "FAC_LVL_2_CD", columnDefinition = "char", length = 8)
    public String facLvl2Cd = " ";
    @Column(name = "FAC_LVL_3_CD", columnDefinition = "char", length = 8)
    public String facLvl3Cd = " ";
    @Column(name = "VERSION")
    public int version = 1;
    @Column(name = "DESCRLONG", length = 4000)
    public String descrlong = " ";
    @Column(name = "ABOLISH_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date abolishDt;
    @Column(name = "OVRD_PREM_FLD_INFO_FLG", columnDefinition = "char", length = 4)
    public String ovrdPremFldInfoFlg = "N";
    @Column(name = "MR_INSTR_CD", columnDefinition = "char", length = 4)
    public String mrInstrCd = " ";
    @Column(name = "MR_WARN_CD", columnDefinition = "char", length = 4)
    public String mrWarnCd = " ";
    @Column(name = "KEY_SW", columnDefinition = "char", length = 1)
    public String keySw = "N";
    @Column(name = "KEY_ID", columnDefinition = "char", length = 10)
    public String keyId = " ";
    @Column(name = "OK_TO_ENTER_SW", columnDefinition = "char", length = 1)
    public String okToEnterSw = "N";
    @Column(name = "MR_INSTR_DETAILS", length = 250)
    public String mrInstrDetails = " ";

    protected SpEntity() {
    }

    public SpEntity(int envId) {
        spKEntitySet.add(new SpKEntity(envId));
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof SpEntity))
            return false;

        SpEntity other = (SpEntity) object;

        if (!this.spId.equals(other.spId)) {
            return false;
        }


        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + spId.hashCode();
        return hash;
    }
}