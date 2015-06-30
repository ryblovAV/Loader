package org.loader.pojo.adj;

import org.loader.pojo.sa.SaEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_ADJ", schema = "RUSADM")
public class AdjEntity {
  protected AdjEntity() {
  }
  public AdjEntity(int envId) {
    adjKEntitySet.add(new AdjKEntity(envId));
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof AdjEntity))
      return false;

    AdjEntity other = (AdjEntity) object;

    if (!this.adjId.equals(other.adjId)) {
      return false;
    }

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + adjId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_ADJ_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "ADJ_ID"))
  public Set<AdjKEntity> adjKEntitySet = new HashSet<>();

//  @OneToMany(mappedBy = "adj", cascade = CascadeType.ALL)
//  public Set<FtProcEntity> ftProcAdjEntitySet = new HashSet<>();

//  @OneToMany(mappedBy = "adj", cascade = CascadeType.ALL)
//  public Set<PaySegEntity> paySegAdjEntitySet = new HashSet<>();

//  @OneToMany(mappedBy = "adj", cascade = CascadeType.ALL)
//  public Set<PayTndrEntity> payTndrAdjEntitySet = new HashSet<>();

//  @OneToMany(mappedBy = "xferAdj", cascade = CascadeType.ALL)
//  public Set<AdjEntity> adjXferAdjEntitySet = new HashSet<>();


//  @ManyToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "BEHALF_SA_ID")
//  public SaEntity behalfSa;

//  @ManyToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "XFER_ADJ_ID")
//  public AdjEntity xferAdj;

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "SA_ID")
  public SaEntity sa;

  @Id
  @Column(name = "ADJ_ID", columnDefinition = "char", length = 12)
  public String adjId;


  @Column(name = "ADJ_AMT")
  public double adjAmt = 0;

  @Column(name = "ADJ_STATUS_FLG", columnDefinition = "char", length = 2)
  public String adjStatusFlg = " ";

  @Column(name = "ADJ_TYPE_CD", columnDefinition = "char", length = 8)
  public String adjTypeCd = " ";

  @Column(name = "APPR_REQ_ID", columnDefinition = "char", length = 12)
  public String apprReqId = " ";

  @Column(name = "BASE_AMT")
  public double baseAmt = 0;

  @Column(name = "CAN_RSN_CD", columnDefinition = "char", length = 4)
  public String canRsnCd = " ";

  @Column(name = "COMMENTS", length = 254)
  public String comments = " ";

  @Column(name = "CRE_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date creDt;

  @Column(name = "CURRENCY_CD", columnDefinition = "char", length = 3)
  public String currencyCd = " ";

  @Column(name = "GEN_REF_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date genRefDt;

  @Column(name = "VERSION")
  public int version = 1;


}