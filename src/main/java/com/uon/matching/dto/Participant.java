package com.uon.matching.dto;

import lombok.Data;

@Data
public class Participant {
    String userId;
    int activityId;
    String name;

    public Participant(String userId, int activityId) {
        this.userId = userId;
        this.activityId = activityId;
    }

    public Participant(String userId, int activityId, String name) {
        this(userId, activityId);
        this.name = name;
    }
}
