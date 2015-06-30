package org.loader.training;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CI_PER", schema = "RUSADM")
public class Master {

    @Id
    @Column(name = "PER_ID", columnDefinition = "char", length = 10)
    public String id;
    @Access(AccessType.FIELD)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CI_PER_CHAR", schema = "RUSADM", joinColumns = @JoinColumn(name = "PER_ID"))
    public Set<MasterAddress> addresses = new HashSet<>();

    public Master() {
    }

    @Override
    public String toString() {

        StringBuilder addressesStr = new StringBuilder();
        for (MasterAddress address : addresses) {
            addressesStr.append(address.commStatus + "~");
        }

        return "Master{" +
                "id='" + id + '\'' +
                ", addresses=" + addressesStr.toString();
    }
}
