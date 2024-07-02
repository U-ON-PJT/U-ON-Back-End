package com.uon.matching.model.service;

import com.uon.matching.dto.Activity;
import com.uon.matching.dto.Participant;
import com.uon.matching.model.UserExperience;
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

            //마감시간이 지나면 모집 마감 처리
            for (Activity activity : activityList) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date deadline = formatter.parse(activity.getDeadline());
                Date currentDate = new Date();

                if (currentDate.after(deadline)) {
                    matchingMapper.updateIsDeadline(activity.getActivityId());
                }
            }
            return activityList;
        } catch (Exception e) {
            log.error("매칭방을 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Activity> selectAllMyMatchingRoom(String userId, int size, int page) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("size", size);
            paramMap.put("offset", (page - 1) * size);

            List<Activity> activityList = matchingMapper.selectAllMyMatchingRoom(paramMap);

            //마감시간이 지나면 모집 마감 처리
            for (Activity activity : activityList) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date deadline = formatter.parse(activity.getDeadline());
                Date currentDate = new Date();

                if (currentDate.after(deadline)) {
                    matchingMapper.updateIsDeadline(activity.getActivityId());
                }
            }
            return activityList;
        } catch (Exception e) {
            log.error("매칭방을 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Activity> selectAllMyEnterMatchingRoom(String userId, int size, int page) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("size", size);
            paramMap.put("offset", (page - 1) * size);

            List<Activity> activityList = matchingMapper.selectAllMyEnterMatchingRoom(paramMap);

            //마감시간이 지나면 모집 마감 처리
            for (Activity activity : activityList) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date deadline = formatter.parse(activity.getDeadline());
                Date currentDate = new Date();

                if (currentDate.after(deadline)) {
                    matchingMapper.updateIsDeadline(activity.getActivityId());
                }
            }
            return activityList;
        } catch (Exception e) {
            log.error("매칭방을 조회하는 도중 문제가 발생함");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int isEnterMatchingRoom(String userId, int activityId) {
        Activity activity = matchingMapper.selectMatchingRoom(activityId);

        //이미 마감된 방인지
        if (activity.getIsDeadline() == 1) {
            return 2;
        }
        //내가 올린 방인지
        if (activity.getUserId().equals(userId)) {
            return 1;
        }

        Participant participant = new Participant(userId, activityId);
        int isContainsMatchingParticipant = matchingMapper.isContainsMatchingParticipant(participant);

        if (isContainsMatchingParticipant == 0 || isContainsMatchingParticipant == 1) {
            return isContainsMatchingParticipant;
        }
        return -1;
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

            //마감시간이 지나면 모집 마감 처리
            for (Activity activity : activityListOfType) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date deadline = formatter.parse(activity.getDeadline());
                Date currentDate = new Date();

                if (currentDate.after(deadline)) {
                    matchingMapper.updateIsDeadline(activity.getActivityId());
                }
            }
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

            //마감시간이 지나면 모집 마감 처리
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date deadline = formatter.parse(activityInfo.getDeadline());
            Date currentDate = new Date();

            if (currentDate.after(deadline)) {
                matchingMapper.updateIsDeadline(activityInfo.getActivityId());
            }

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

            //신청이 마감되었고, 활동이 아직 완료되지 않은 상태라면
            if (activityInfo.getIsDeadline() == 0 || activityInfo.getIsCompleted() == 1) {
                log.error("신청이 마감되지 않았거나, 이미 활동이 마감된 매칭임");
                return -1;
            }

            matchingMapper.updateIsComplete(activityId);

            List<Participant> participantList = matchingMapper.selectParticipants(activityId);
            Map<String, Object> paramMap = new HashMap<>();
            participantList.add(new Participant(userId, activityId));

            for (Participant participant : participantList) {
                paramMap.put("userId", participant.getUserId());
                paramMap.put("experienceValue", UserExperience.ACTIVITY_EXPERIENCE.getValue());
                paramMap.put("pointValue", UserExperience.ACTIVITY_POINT.getValue());
                matchingMapper.increaseExperience(paramMap);
            }

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

            //방장은 자기 매칭방을 신청하지 못함
            if (activityInfo.getUserId().equals(participant.getUserId())) {
                return -1;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date deadline = formatter.parse(activityInfo.getDeadline());
            Date currentDate = new Date();

            //마감시간 이후 신청 제한
            if (currentDate.after(deadline)) {
                log.error("마감시간 이후 신청 불가");
                return -1;
            }

            //모집 마감으로 인한 신청 제한
            if (activityInfo.getIsDeadline() == 1) {
                log.error("신청 인원이 꽉 참으로 인한 신청 제한");
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

            if (activityInfo.getCurrentParticipant() == activityInfo.getMaxParticipant()) {
                if (matchingMapper.updateIsDeadline(activityInfo.getActivityId()) != 1) {
                    log.error("모집 마감하는 과정에서 문제 발생");
                    return -1;
                }
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

            //방장은 자기 매칭방을 신청 취소하지 못함
            if (activityInfo.getUserId().equals(participant.getUserId())) {
                return -1;
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date deadline = formatter.parse(activityInfo.getDeadline());
            Date currentDate = new Date();

            //마감시간 이후 신청 취소 제한
            if (currentDate.after(deadline)) {
                log.error("마감시간 이후 신청 취소 불가");
                return -1;
            }

            activityInfo.setCurrentParticipant(activityInfo.getCurrentParticipant() - 1);
            activityInfo.setIsDeadline(0);

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
