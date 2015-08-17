package org.loader.pojo.billChg;


import org.loader.pojo.sa.SaEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_BILL_CHG", schema = "RUSADM")
public class BillChgEntity {
  
  protected BillChgEntity() {
  }
  
  public BillChgEntity(int envId) {
    billChgKEntitySet.add(new BillChgKEntity(envId));
  }
  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof BillChgEntity))
      return false;

    BillChgEntity other = (BillChgEntity) object;

    if (!this.billableChgId.equals(other.billableChgId)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + billableChgId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_BILL_CHG_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "BILLABLE_CHG_ID"))
  public Set<BillChgKEntity> billChgKEntitySet = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "CI_B_CHG_LINE", schema = "RUSADM", joinColumns = @JoinColumn(name = "BILLABLE_CHG_ID"))
  public Set<BChgLineEntity> billChgLineEntitySet = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "CI_BCHG_SQ", schema = "RUSADM", joinColumns = @JoinColumn(name = "BILLABLE_CHG_ID"))
  public Set<BchgSqEntity> billChgSeqEntitySet = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "SA_ID")
  public SaEntity sa;

  @Id
  @Column(name = "BILLABLE_CHG_ID", columnDefinition = "char", length = 12)
  public String billableChgId;


  @Column(name = "BILLABLE_CHG_STAT", columnDefinition = "char", length = 2)
  public String billableChgStat = " ";

  @Column(name = "DESCR_ON_BILL", length = 80)
  public String descrOnBill = " ";

  @Column(name = "END_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date endDt;

  @Column(name = "START_DT")
  @Temporal(TemporalType.TIMESTAMP)
  public Date startDt;

  @Column(name = "VERSION")
  public int version = 1;


}