package com.project.schoolmanagement.payload.mappers.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
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
}
