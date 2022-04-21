package ru.otus.crm.model;


import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(
            mappedBy = "client"
            , cascade = {CascadeType.MERGE, CascadeType.PERSIST}
            , fetch = FetchType.LAZY
    )
    private Address address;

    @OneToMany(
            mappedBy = "client"
            , cascade = {CascadeType.MERGE, CascadeType.PERSIST}
            , fetch = FetchType.LAZY
    )
    @BatchSize(size = 10)
    private List<Phone> phones;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
        this.phones = new ArrayList<>();
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.phones = new ArrayList<>();
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {

        this.id = id;
        this.name = name;

        this.address = address;
        if (address!=null) {
            address.setClient(this);
        }

        this.phones = new ArrayList<>();
        if (phones!=null) {
            for(Phone p: phones){
                p.setClient(this);
                this.phones.add(p);
            }
        }
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
