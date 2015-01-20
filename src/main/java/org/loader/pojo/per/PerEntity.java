package org.loader.pojo.per;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "CI_PER", schema = "STGADM")
public class PerEntity {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_PER_ID", schema = "STGADM", joinColumns = @JoinColumn(name = "PER_ID"))
    public Set<PerIdEntity> perIdEntitySet = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_PER_PHONE", schema = "STGADM", joinColumns = @JoinColumn(name = "PER_ID"))
    public Set<PerPhoneEntity> perPhoneEntitySet = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_PER_CHAR", schema = "STGADM", joinColumns = @JoinColumn(name = "PER_ID"))
    public Set<PerCharEntity> perCharEntitySet = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_PER_K", schema = "STGADM", joinColumns = @JoinColumn(name = "PER_ID"))
    public Set<PerKEntity> perKEntitySet = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_PER_NAME", schema = "STGADM", joinColumns = @JoinColumn(name = "PER_ID"))
    public Set<PerNameEntity> perNameEntitySet = new HashSet<>();
    @Id
    @Column(name = "PER_ID", columnDefinition = "char", length = 10)
    public String perId;
    @Column(name = "LANGUAGE_CD", columnDefinition = "char", length = 3)
    public String languageCd = " ";
    @Column(name = "PER_OR_BUS_FLG", columnDefinition = "char", length = 2)
    public String perOrBusFlg = " ";
    @Column(name = "LS_SL_FLG", columnDefinition = "char", length = 2)
    public String lsSlFlg = " ";
    @Column(name = "LS_SL_DESCR", length = 1000)
    public String lsSlDescr = " ";
    @Column(name = "EMAILID", length = 70)
    public String emailid = " ";
    @Column(name = "OVRD_MAIL_NAME1", length = 254)
    public String ovrdMailName1 = " ";
    @Column(name = "OVRD_MAIL_NAME2", length = 254)
    public String ovrdMailName2 = " ";
    @Column(name = "OVRD_MAIL_NAME3", length = 254)
    public String ovrdMailName3 = " ";
    @Column(name = "ADDRESS1", length = 254)
    public String address1 = " ";
    @Column(name = "ADDRESS2", length = 254)
    public String address2 = " ";
    @Column(name = "ADDRESS3", length = 254)
    public String address3 = " ";
    @Column(name = "ADDRESS4", length = 254)
    public String address4 = " ";
    @Column(name = "CITY", length = 90)
    public String city = " ";
    @Column(name = "NUM1", columnDefinition = "char", length = 6)
    public String num1 = " ";
    @Column(name = "NUM2", columnDefinition = "char", length = 4)
    public String num2 = " ";
    @Column(name = "COUNTY", length = 90)
    public String county = " ";
    @Column(name = "POSTAL", columnDefinition = "char", length = 12)
    public String postal = " ";
    @Column(name = "HOUSE_TYPE", columnDefinition = "char", length = 2)
    public String houseType = " ";
    @Column(name = "GEO_CODE", columnDefinition = "char", length = 11)
    public String geoCode = " ";
    @Column(name = "IN_CITY_LIMIT", columnDefinition = "char", length = 1)
    public String inCityLimit = " ";
    @Column(name = "STATE", columnDefinition = "char", length = 6)
    public String state = " ";
    @Column(name = "COUNTRY", columnDefinition = "char", length = 3)
    public String country = " ";
    @Column(name = "VERSION")
    public int version = 1;
    @Column(name = "RECV_MKTG_INFO_FLG", columnDefinition = "char", length = 4)
    public String recvMktgInfoFlg = "NRCV";
    @Column(name = "WEB_PASSWD", length = 30)
    public String webPasswd = " ";
    @Column(name = "WEB_PWD_HINT_FLG", columnDefinition = "char", length = 4)
    public String webPwdHintFlg = " ";
    @Column(name = "WEB_PASSWD_ANS", length = 60)
    public String webPasswdAns = " ";

    protected PerEntity() {
    }

    public PerEntity(int envId) {
        perKEntitySet.add(new PerKEntity(envId));
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof PerEntity))
            return false;

        PerEntity other = (PerEntity) object;

        if (!this.perId.equals(other.perId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = 31 * hash + perId.hashCode();
        return hash;
    }
}