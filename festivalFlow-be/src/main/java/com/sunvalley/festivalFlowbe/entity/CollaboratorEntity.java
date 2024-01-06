package com.sunvalley.festivalFlowbe.entity;

import java.sql.Date;
import java.util.Objects;

public class CollaboratorEntity {
    private int id;
    private String phone;
    private String firstName;
    private String lastName;
    private Date age;
    private String size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollaboratorEntity that = (CollaboratorEntity) o;
        return id == that.id && Objects.equals(phone, that.phone) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(age, that.age) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, firstName, lastName, age, size);
    }
}
