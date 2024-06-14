package com.uon.matching.dto;

import lombok.Data;

@Data
public class Activity {

    private int activityId;         //활동 id
    private String userId;          //작성자 id
    private String title;           //제목
    private String content;         //내용
    private String activityAddress; //모임 주소
    private String dongCode;        //동코드
    private String activityDate;    //모임 시간
    private String createTime;      //작성 시간
    private String deadline;        //마감시간
    private int minParticipant;     //최소 참여인원
    private int currentParticipant; //현재 참여 인원
    private int maxParticipant;     //최대 참여 인원
    private int isDeadline;     //마감 여부
    private int isCompleted;    //활동 완료 여부
    private int type;               //글 분류
}
