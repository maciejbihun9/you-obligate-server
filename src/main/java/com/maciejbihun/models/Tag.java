package com.maciejbihun.models;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Maciej Bihun
 */
@MappedSuperclass
public class Tag {

    @Basic(optional = false)
    @Column(name = "VALUE", nullable = false, updatable = false)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
