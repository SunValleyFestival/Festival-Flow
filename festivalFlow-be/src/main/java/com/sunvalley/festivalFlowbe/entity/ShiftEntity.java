package com.sunvalley.festivalFlowbe.entity;

import java.sql.Time;
import java.util.Objects;

public class ShiftEntity {
    private int id;
    private String description;
    private String name;
    private Integer locationId;
    private Time time;
    private int day;
    private int maxCollaborator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMaxCollaborator() {
        return maxCollaborator;
    }

    public void setMaxCollaborator(int maxCollaborator) {
        this.maxCollaborator = maxCollaborator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftEntity that = (ShiftEntity) o;
        return id == that.id && day == that.day && maxCollaborator == that.maxCollaborator && Objects.equals(description, that.description) && Objects.equals(name, that.name) && Objects.equals(locationId, that.locationId) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, name, locationId, time, day, maxCollaborator);
    }
}
