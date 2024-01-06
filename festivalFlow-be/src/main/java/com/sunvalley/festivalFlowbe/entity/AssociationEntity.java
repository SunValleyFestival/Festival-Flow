package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "association", schema = "FestivalFlow", catalog = "")
@IdClass(AssociationEntityPK.class)
public class AssociationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shift_id")
    private int shiftId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "collaborator_id")
    private int collaboratorId;
    @Basic
    @Column(name = "status")
    private int status;
    @Basic
    @Column(name = "request")
    private String request;
    @Basic
    @Column(name = "collaborator_friend_id")
    private Integer collaboratorFriendId;

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(int collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getCollaboratorFriendId() {
        return collaboratorFriendId;
    }

    public void setCollaboratorFriendId(Integer collaboratorFriendId) {
        this.collaboratorFriendId = collaboratorFriendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssociationEntity that = (AssociationEntity) o;
        return shiftId == that.shiftId && collaboratorId == that.collaboratorId && status == that.status && Objects.equals(request, that.request) && Objects.equals(collaboratorFriendId, that.collaboratorFriendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftId, collaboratorId, status, request, collaboratorFriendId);
    }
}
