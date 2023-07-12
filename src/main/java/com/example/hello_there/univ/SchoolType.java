package com.example.hello_there.univ;

import lombok.Getter;

@Getter
public enum SchoolType {
    MIDDLE_SCHOOL(1),
    HIGH_SCHOOL(2),
    UNIVERSITY(3);

    private int value;

    SchoolType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}