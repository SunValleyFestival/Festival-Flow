package com.sunvalley.festivalFlowbe.entity;

import java.sql.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorEntity {
    private int id;
    private String phone;
    private String firstName;
    private String lastName;
    private Date age;
    private String size;
}
