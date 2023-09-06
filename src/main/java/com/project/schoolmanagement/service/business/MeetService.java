package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.entity.concretes.business.Meet;
import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Student;
import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.payload.mappers.business.MeetMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.business.MeetRequest;
import com.project.schoolmanagement.payload.response.business.MeetResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.repository.business.MeetRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.user.AdvisorTeacherService;
import com.project.schoolmanagement.service.user.StudentService;
import com.project.schoolmanagement.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetService
{
    private final MeetRepository meetRepository;
    private final AdvisorTeacherService advisorTeacherService;
    private final StudentService studentService;
    private final PageableHelper pageableHelper;
    private final DateTimeValidator dateTimeValidator;
    private final MeetMapper meetMapper;


    public ResponseMessage<MeetResponse> saveMeet(MeetRequest meetRequest, HttpServletRequest httpServletRequest)
    {
        String username = (String) httpServletRequest.getAttribute("username");
        AdvisoryTeacher advisoryTeacher = advisorTeacherService.getAdvisorTeacherByUsername(username);
        dateTimeValidator.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());

        for(Long studentId: meetRequest.getStudentIds())
        {
            studentService.isStudentExist(studentId);
            //I have to be sure all students do not have any other meetings at this time period
            checkMeetConflict(studentId,
                    meetRequest.getDate(),
                    meetRequest.getStartTime(),
                    meetRequest.getStopTime());
        }

        List<Student> students = studentService.getStudentByIdList(meetRequest.getStudentIds());

        Meet meet = meetMapper.mapMeetRequestToMeet(meetRequest);
        meet.setStudentList(students);
        meet.setAdvisoryTeacher(advisoryTeacher);

        Meet savedMeet = meetRepository.save(meet);

        return ResponseMessage.<MeetResponse>builder()
                .object(meetMapper.mapMeetToMeetResponse(savedMeet))
                .message(SuccessMessages.MEET_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private void checkMeetConflict(Long studentId, LocalDate date, LocalTime startTime, LocalTime stopTime)
    {
        List<Meet> meets = meetRepository.findByStudentList_IdEquals(studentId);
        for (Meet meet: meets)
        {
            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if(meet.getDate().equals(date) &&
                    ((startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                            (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) ||
                            (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) ||
                            (startTime.equals(existingStartTime) && stopTime.equals(existingStopTime))))
            {
                throw new ConflictException(ErrorMessages.MEET_HOURS_CONFLICT);
            }
        }
    }
}
