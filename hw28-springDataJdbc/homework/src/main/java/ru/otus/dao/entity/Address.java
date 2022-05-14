package ru.otus.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Address implements Persistable<Long> {

    @Id
    private final Long id;
    private final String street;

    @Transient
    private final boolean isNew;

    public Address(Long id, @Nonnull String street, @Nonnull boolean isNew) {
        this.id = id;
        this.street = street;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Address(Long id, @Nonnull String street) {
        this(id, street, false);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public String getStreet() {
        return street;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
