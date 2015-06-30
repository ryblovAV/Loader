package org.loader.pojo.mr;

import org.loader.pojo.mtrcfg.MtrConfigEntity;
import org.loader.pojo.regread.RegReadEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_MR", schema = "RUSADM")
public class MrEntity {

  protected MrEntity() {
  }

  public MrEntity(int envId) {
    mrKEntitySet.add(new MrKEntity(envId));
  }

  @Override
  public boolean equals(Object object) {

    if (this == object)
      return true;

    if (!(object instanceof MrEntity))
      return false;

    MrEntity other = (MrEntity) object;

    if (!this.mrId.equals(other.mrId)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash = 31 * hash + mrId.hashCode();

    return hash;
  }

  @ElementCollection
  @CollectionTable(name = "CI_MR_CHAR", schema = "RUSADM", joinColumns = @JoinColumn(name = "MR_ID"))
  public Set<MrCharEntity> mrCharEntitySet = new HashSet<>();
     
  @ElementCollection
  @CollectionTable(name = "CI_MR_K", schema = "RUSADM", joinColumns = @JoinColumn(name = "MR_ID"))
  public Set<MrKEntity> mrKEntitySet = new HashSet<>();

  @OneToMany(mappedBy = "mr", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<RegReadEntity> regReadMrEntitySet = new HashSet<>();

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MTR_CONFIG_ID")
  public MtrConfigEntity mtrConfig;

  @Id
  @Column(name = "MR_ID", columnDefinition = "char", length = 12)
  public String mrId;

  @Column(name = "MR_SOURCE_CD", columnDefinition = "char", length = 12)
  public String mrSourceCd = " ";

  @Column(name = "MTR_READER_ID", length = 20)
  public String mtrReaderId = " ";

  @Column(name = "READ_DTTM")
  @Temporal(TemporalType.TIMESTAMP)
  public Date readDttm;

  @Column(name = "USE_ON_BILL_SW", columnDefinition = "char", length = 1)
  public String useOnBillSw = " ";

  @Column(name = "VERSION")
  public int version = 1;


}