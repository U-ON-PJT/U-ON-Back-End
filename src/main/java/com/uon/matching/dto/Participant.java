package com.uon.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Participant {
    String userId;
    int activityId;
}
