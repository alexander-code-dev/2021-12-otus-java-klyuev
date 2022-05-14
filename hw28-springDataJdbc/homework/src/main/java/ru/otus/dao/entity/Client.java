package ru.otus.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

//@Table("CLIENT")
public class Client implements Persistable<Long> {

    @Id
    private final Long id;
    private final String name;

    @MappedCollection(idColumn = "CLIENT_ID")
    private Address address;

    @MappedCollection(idColumn = "CLIENT_ID")
    private Set<Phone> phones;

    @Transient
    private final boolean isNew;

    public Client(Long id, @Nonnull String name, Address address, Set<Phone> phones, @Nonnull boolean isNew) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Client(Long id, @Nonnull String name, Address address, Set<Phone> phones)     {
        this(id, name, address, phones, false);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
