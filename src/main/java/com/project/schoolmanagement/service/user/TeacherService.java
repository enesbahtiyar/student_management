package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.user.AdminMapper;
import com.project.schoolmanagement.payload.mappers.user.TeacherMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.user.ChooseLessonTeacherRequest;
import com.project.schoolmanagement.payload.request.user.TeacherRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.TeacherResponse;
import com.project.schoolmanagement.repository.user.TeacherRepository;
import com.project.schoolmanagement.service.business.LessonProgramService;
import com.project.schoolmanagement.service.business.LessonService;
import com.project.schoolmanagement.service.validator.DateTimeValidator;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final DateTimeValidator dateTimeValidator;

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

    private Teacher isTeacherExist(Long id)
    {
        return teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
    }

    public ResponseMessage<TeacherResponse> updateTeacher(TeacherRequest teacherRequest, Long userId)
    {
        Teacher teacher = isTeacherExist(userId);
        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());
        uniquePropertyValidator.checkUniqueProperties(teacher, teacherRequest);
        Teacher updatedTeacher = teacherMapper.mapTeacherRequestToUpdatedTeacher(teacherRequest, userId);
        updatedTeacher.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
        updatedTeacher.setLessonsProgramList(lessonPrograms);
        updatedTeacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        Teacher savedTeacher = teacherRepository.save(updatedTeacher);
        //isAdvisory
        advisorTeacherService.updateAdvisorTeacher(teacherRequest.getIsAdvisorTeacher(), savedTeacher);
        return ResponseMessage.<TeacherResponse>builder()
                .message(SuccessMessages.TEACHER_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(teacherMapper.mapTeacherToTeacherResponse(savedTeacher))
                .build();
    }

    public List<TeacherResponse> getTeacherByName(String teacherName)
    {
        return teacherRepository.getTeacherByNameContaining(teacherName)
                .stream()
                .map(teacherMapper::mapTeacherToTeacherResponse)
                .collect(Collectors.toList());
    }

    public List<TeacherResponse> getAllTeacher()
    {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::mapTeacherToTeacherResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<TeacherResponse> chooseLesson(ChooseLessonTeacherRequest chooseLessonTeacherRequest)
    {
        //check if the lesson program is exist
        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(chooseLessonTeacherRequest.getLessonProgramId());
        //check if the teacher is exist -- expected to have
        Teacher teacher = isTeacherExist(chooseLessonTeacherRequest.getTeacherId());
        //existing list
        Set<LessonProgram> teacherLessonProgram = teacher.getLessonsProgramList();

        dateTimeValidator.checkLessonPrograms(teacherLessonProgram, lessonPrograms);

        teacherLessonProgram.addAll(lessonPrograms);
        teacher.setLessonsProgramList(teacherLessonProgram);

        Teacher updatedTeacher = teacherRepository.save(teacher);

        return ResponseMessage.<TeacherResponse>builder()
                .message(SuccessMessages.TEACHER_UPDATE)
                .object(teacherMapper.mapTeacherToTeacherResponse(updatedTeacher))
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public Teacher getTeacherByUsername(String username)
    {
        if(!teacherRepository.existsByUsername(username))
        {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE_USERNAME, username));
        }
        return teacherRepository.getTeachersByUsername(username);
    }
}
