package br.com.bnck.beans;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "name_address")
@NamedQuery(name = "fetchAllRows", query = "select x from NameAddress x")
public class NameAddress {

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
}
