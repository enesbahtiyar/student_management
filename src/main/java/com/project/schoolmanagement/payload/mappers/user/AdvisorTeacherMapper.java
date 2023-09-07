package com.project.schoolmanagement.payload.mappers.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.payload.response.user.AdvisoryTeacherResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdvisorTeacherMapper
{
    public AdvisoryTeacher mapTeacherToAdvisorTeacher(Teacher teacher)
    {
        return AdvisoryTeacher.builder()
                .teacher(teacher)
                .build();
    }

    public AdvisoryTeacherResponse mapAdvisoryTeacherToAdvisoryTeacherResponse(AdvisoryTeacher advisoryTeacher)
    {
        return AdvisoryTeacherResponse.builder()
                .advisoryTeacherId(advisoryTeacher.getId())
                .teacherName(advisoryTeacher.getTeacher().getName())
                .teacherSurname(advisoryTeacher.getTeacher().getSurname())
                .teacherSsn(advisoryTeacher.getTeacher().getSsn())
                .build();
    }
}
