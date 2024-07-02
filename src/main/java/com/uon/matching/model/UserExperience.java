package com.uon.matching.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserExperience {

    ACTIVITY_EXPERIENCE(20),
    ACTIVITY_POINT(10);

    private final int value;


}
