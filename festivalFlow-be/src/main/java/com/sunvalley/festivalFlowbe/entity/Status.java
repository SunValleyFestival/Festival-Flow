package com.sunvalley.festivalFlowbe.entity;

import lombok.Getter;

@Getter
public enum Status {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2);

    private final int value;

    Status(int value) {
        this.value = value;
    }
}
