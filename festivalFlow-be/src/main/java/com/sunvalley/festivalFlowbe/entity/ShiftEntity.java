package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shift", schema = "FestivalFlow")
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;

    @Column(name = "startTime", nullable = false)
    private java.sql.Time startTime;

    @Column(name = "endTime", nullable = false)
    private java.sql.Time endTime;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "maxCollaborator", nullable = false)
    private int maxCollaborator;
}
