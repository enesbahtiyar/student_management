package com.project.schoolmanagement.payload.mappers.user;

import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.payload.request.user.TeacherRequest;
import com.project.schoolmanagement.payload.response.user.TeacherResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TeacherMapper
{
    public Teacher mapTeacherRequestToTeacher(TeacherRequest teacherRequest)
    {
        return Teacher.builder()
                .name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .ssn(teacherRequest.getSsn())
                .username(teacherRequest.getUsername())
                .birthDay(teacherRequest.getBirthDay())
                .birthPlace(teacherRequest.getBirthPlace())
                .password(teacherRequest.getPassword())
                .email(teacherRequest.getEmail())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .isAdvisory(teacherRequest.getIsAdvisorTeacher())
                .gender(teacherRequest.getGender())
                .build();
    }

    public TeacherResponse mapTeacherToTeacherResponse(Teacher teacher)
    {
        return TeacherResponse.builder()
                .userId(teacher.getId())
                .username(teacher.getUsername())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .build();
    }

    public Teacher mapTeacherRequestToUpdatedTeacher(TeacherRequest teacherRequest, Long id)
    {
        Teacher teacher = mapTeacherRequestToTeacher(teacherRequest);
        teacher.setId(id);
        return teacher;
    }
}
