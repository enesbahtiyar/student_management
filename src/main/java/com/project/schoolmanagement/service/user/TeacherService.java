package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.payload.mappers.user.AdminMapper;
import com.project.schoolmanagement.payload.mappers.user.TeacherMapper;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.user.TeacherRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.TeacherResponse;
import com.project.schoolmanagement.repository.user.TeacherRepository;
import com.project.schoolmanagement.service.business.LessonProgramService;
import com.project.schoolmanagement.service.business.LessonService;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherService
{
    private final TeacherRepository  teacherRepository;
    private final LessonProgramService lessonProgramService;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final TeacherMapper teacherMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final AdvisorTeacherService advisorTeacherService;

    public ResponseMessage<TeacherResponse> saveTeacher(TeacherRequest teacherRequest)
    {
        //I need to get lessonPrograms according to id.s
        Set<LessonProgram> lessonProgramSet = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());
        uniquePropertyValidator.checkDuplicate(teacherRequest.getUsername(),
                                                teacherRequest.getSsn(),
                                                teacherRequest.getPhoneNumber(),
                                                teacherRequest.getEmail());

        Teacher teacher = teacherMapper.mapTeacherRequestToTeacher(teacherRequest);
        //setting missing props
        teacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        teacher.setLessonsProgramList(lessonProgramSet);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        //saving the teacher
        Teacher savedTeacher = teacherRepository.save(teacher);

        if(teacherRequest.getIsAdvisorTeacher())
        {
            advisorTeacherService.saveAdvisorTeacher(teacher);
        }

        return ResponseMessage.<TeacherResponse>builder()
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.TEACHER_SAVE)
                .object(teacherMapper.mapTeacherToTeacherResponse(savedTeacher))
                .build();
    }
}
