package com.kennymaness.apprenticeshipproject.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity {

//    @Id
//    @GeneratedValue
//    private int id;
//
//    public int getId() {
//        return id;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AbstractEntity entity = (AbstractEntity) o;
//        return id == entity.getId();
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }

    @PreUpdate
    public void preUpdate() {
        ZonedDateTime updatedAt = ZonedDateTime.now();
    }

}