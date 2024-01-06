package com.sunvalley.festivalFlowbe.entity;

import java.util.Objects;

public class DayEntity {
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayEntity dayEntity = (DayEntity) o;
        return id == dayEntity.id && Objects.equals(name, dayEntity.name) && Objects.equals(description, dayEntity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
