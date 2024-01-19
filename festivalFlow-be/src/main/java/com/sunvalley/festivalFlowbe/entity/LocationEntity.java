package com.sunvalley.festivalFlowbe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    private int id;
    private String name;
    private String description;
    private Integer dayId;
}
