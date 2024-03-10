package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.*;
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
    private Integer id;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;

    @Column(name = "start_time", nullable = false)
    private java.sql.Time startTime;

    @Column(name = "end_time", nullable = false)
    private java.sql.Time endTime;

    @Column(name = "max_collaborator", nullable = false)
    private int maxCollaborator;

    @Column(name = "adults_only", nullable = false)
    private boolean adultsOnly;

}
