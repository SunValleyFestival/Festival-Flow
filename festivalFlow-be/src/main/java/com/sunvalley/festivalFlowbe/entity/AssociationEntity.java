package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.*;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
