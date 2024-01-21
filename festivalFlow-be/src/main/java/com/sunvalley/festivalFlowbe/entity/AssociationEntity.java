package com.sunvalley.festivalFlowbe.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
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

    @Column(name = "status")
    private int status;

    @Column(name = "request")
    private String request;

    @Column(name = "collaborator_friend_id")
    private Integer collaboratorFriendId;
}
