package ru.otus.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.Objects;

//@Table("PHONE")
public class Phone implements Persistable<Long> {

    @Id
    private final Long id;
    private final String number;

    @Transient
    private final boolean isNew;

    public Phone(Long id, @Nonnull String number, @Nonnull boolean isNew) {
        this.id = id;
        this.number = number;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Phone(Long id, @Nonnull String number) {
        this(id, number, false);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Nonnull
    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id.equals(phone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
