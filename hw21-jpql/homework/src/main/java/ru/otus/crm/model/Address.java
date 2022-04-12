package ru.otus.crm.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Address() {
    }

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        client.setAddress(this);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
