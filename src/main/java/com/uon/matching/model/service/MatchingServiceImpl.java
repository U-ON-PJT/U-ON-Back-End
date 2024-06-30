package com.uon.matching.model.service;

import com.uon.matching.dto.Activity;
import com.uon.matching.dto.Participant;
import com.uon.matching.model.mapper.MatchingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingServiceImpl implements MatchingService {

    private final MatchingMapper matchingMapper;

    @Override
    public int insertMatchingRoom(Activity activity) {
        try {
            int isSuccess = matchingMapper.insertMatchingRoom(activity);
            return isSuccess;
        } catch (Exception e) {
            log.error("매칭방을 생성하는 도중 문제가 발생함");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateMatchingRoom(Activity activity) {
        try {
            int isSuccess = matchingMapper.updateMatchingRoom(activity);
            return isSuccess;
        } catch (Exception e) {
            log.error("매칭방을 수정하는 도중 문제가 발생함");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Activity> selectAllMatchingRoom(int size, int page) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("size", size);
            paramMap.put("offset", (page - 1) * size);

            List<Activity> activityList = matchingMapper.selectAllMatchingRoom(paramMap);
            return activityList;
        } catch (Exception e) {
            log.error("매칭방을 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Activity> selectAllMatchingRoom2(int size, int page, int type, String selectDate, String parsingDongCode) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("size", size);
            paramMap.put("offset", (page - 1) * size);
            paramMap.put("type", type);
            paramMap.put("selectDate", selectDate);
            paramMap.put("parsingDongCode", parsingDongCode);

            List<Activity> activityList = matchingMapper.selectAllMatchingRoom2(paramMap);
            return activityList;
        } catch (Exception e) {
            log.error("매칭방을 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Activity> selectMatchingRoomOfType(int type, int size, int page) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("type", type);
            paramMap.put("size", size);
            paramMap.put("offset", (page - 1) * size);

            List<Activity> activityListOfType = matchingMapper.selectMatchingRoomOfType(paramMap);
            return activityListOfType;
        } catch (Exception e) {
            log.error("매칭방을 타입 별로 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity selectMatchingRoom(int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);
            return activityInfo;
        } catch (Exception e) {
            log.error("매칭방을 상세 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteMatchingRoom(String userId, int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);

            if (!userId.equals(activityInfo.getUserId())) {
                log.error(userId + "는 매칭을 올린 글쓴이가 아님");
                return -1;
            }

            matchingMapper.deleteMatchingRoom(activityId);
            return 0;
        } catch (Exception e) {
            log.error("매칭방을 삭제하는 도중 문제가 발생함");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateIsDeadline(String userId, int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);

            if (!userId.equals(activityInfo.getUserId())) {
                log.error(userId + "는 매칭을 올린 글쓴이가 아님");
                return -1;
            }

            matchingMapper.updateIsDeadline(activityId);
            insertParticipant(new Participant(userId, activityId));
            return 0;
        } catch (Exception e) {
            log.error("매칭 마감을 하는 도중 문제가 발생함");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateIsComplete(String userId, int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);

            if (!userId.equals(activityInfo.getUserId())) {
                log.error(userId + "는 매칭을 올린 글쓴이가 아님");
                return -1;
            }

            matchingMapper.updateIsComplete(activityId);
            return 0;
        } catch (Exception e) {
            log.error("활동을 완료하는 도중 문제가 발생함");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Participant> selectParticipants(Participant participant) {
        try {
            int isContainsMatchingParticipant = matchingMapper.isContainsMatchingParticipant(participant);

            if (isContainsMatchingParticipant == 0) {
                log.error(participant.getUserId() + "는 매칭을 신청을 하지 않았음");
                return null;
            }

            List<Participant> participantsList = matchingMapper.selectParticipants(participant.getActivityId());

            return participantsList;
        } catch (Exception e) {
            log.error("참가자들을 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insertParticipant(Participant participant) {
        try {
            int isContainsMatchingParticipant = matchingMapper.isContainsMatchingParticipant(participant);

            if (isContainsMatchingParticipant == 1) {
                log.error(participant.getUserId() + "는 매칭을 이미 신청하였음");
                return -1;
            }

            Activity activityInfo = matchingMapper.selectMatchingRoom(participant.getActivityId());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date deadline = formatter.parse(activityInfo.getDeadline());
            Date currentDate = new Date();

            //마감시간 이후 신청 제한
            if (currentDate.after(deadline)) {
                log.error("마감시간 이후 신청 불가");
                return -1;
            }
            activityInfo.setCurrentParticipant(activityInfo.getCurrentParticipant() + 1);

            if (matchingMapper.updateMatchingRoom(activityInfo) != 1) {
                log.error("현재 활동 인원을 +1 하는 과정에서 문제 발생");
                return -1;
            }
            if (matchingMapper.insertParticipant(participant) != 1) {
                log.error("참가자 테이블에 참가자를 추가하는 과정에서 문제 발생");
                return -1;
            }

            return 0;
        } catch (Exception e) {
            log.error("매칭 신청을 하는 도중 문제가 발생함");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteParticipant(Participant participant) {
        try {
            int isContainsMatchingParticipant = matchingMapper.isContainsMatchingParticipant(participant);

            if (isContainsMatchingParticipant == 0) {
                log.error(participant.getUserId() + "는 매칭을 신청을 하지 않았음");
                return -1;
            }

            Activity activityInfo = matchingMapper.selectMatchingRoom(participant.getActivityId());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date deadline = formatter.parse(activityInfo.getDeadline());
            Date currentDate = new Date();

            //마감시간 이후 신청 취소 제한
            if (currentDate.after(deadline)) {
                log.error("마감시간 이후 신청 취소 불가");
                return -1;
            }

            activityInfo.setCurrentParticipant(activityInfo.getCurrentParticipant() - 1);

            if (matchingMapper.updateMatchingRoom(activityInfo) != 1) {
                log.error("현재 활동 인원을 -1 하는 과정에서 문제 발생");
                return -1;
            }
            if (matchingMapper.deleteParticipant(participant) != 1) {
                log.error("참가자 테이블에 참가자를 삭제하는 과정에서 문제 발생");
                return -1;
            }

            return 0;
        } catch (Exception e) {
            log.error("매칭신청을 취소하는 도중 문제가 발생함");
            e.printStackTrace();
            return -1;
        }
    }
}
