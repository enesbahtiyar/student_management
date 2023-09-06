package com.project.schoolmanagement.payload.mappers.business;

import com.project.schoolmanagement.entity.concretes.business.Meet;
import com.project.schoolmanagement.payload.request.business.MeetRequest;
import com.project.schoolmanagement.payload.response.business.MeetResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MeetMapper
{
    public Meet mapMeetRequestToMeet(MeetRequest meetRequest)
    {
        return Meet.builder()
                .date(meetRequest.getDate())
                .startTime(meetRequest.getStartTime())
                .stopTime(meetRequest.getStopTime())
                .description(meetRequest.getDescription())
                .build();
    }

    public MeetResponse mapMeetToMeetResponse(Meet meet)
    {
        return MeetResponse.builder()
                .id(meet.getId())
                .date(meet.getDate())
                .startTime(meet.getStartTime())
                .stopTime(meet.getStopTime())
                .description(meet.getDescription())
                .advisorTeacherId(meet.getAdvisoryTeacher().getId())
                .teacherSsn(meet.getAdvisoryTeacher().getTeacher().getSsn())
                .teacherName(meet.getAdvisoryTeacher().getTeacher().getName())
                .students(meet.getStudentList())
                .build();
    }

    public Meet mapMeetUpdateRequestToMeet(MeetRequest meetRequest, Long meetId)
    {
        Meet meet = mapMeetRequestToMeet(meetRequest);
        meet.setId(meetId);
        return meet;
    }
}
