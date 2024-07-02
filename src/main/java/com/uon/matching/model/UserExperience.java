package com.uon.matching.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserExperience {

    ACTIVITY_EXPERIENCE(50),
    ACTIVITY_POINT(100);

    private final int value;


}
