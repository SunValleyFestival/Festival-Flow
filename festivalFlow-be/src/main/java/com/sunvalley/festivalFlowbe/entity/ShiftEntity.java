package com.sunvalley.festivalFlowbe.entity;

import java.sql.Time;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftEntity {
    private int id;
    private String description;
    private String name;
    private Integer locationId;
    private Time time;
    private int day;
    private int maxCollaborator;
}
