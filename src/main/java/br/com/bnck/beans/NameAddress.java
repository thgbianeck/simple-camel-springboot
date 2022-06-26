package br.com.bnck.beans;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "name_address")
@NamedQuery(name = "fetchAllRows", query = "select x from NameAddress x")
public class NameAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "house_number")
    private String houseNumber;

    private String city;

    private String province;

    @Column(name = "postal_code")
    private String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NameAddress that = (NameAddress) o;
        return Objects.nonNull(id) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
