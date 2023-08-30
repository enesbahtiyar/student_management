package com.project.schoolmanagement.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagement.entity.concretes.business.EducationTerm;
import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.entity.enums.Day;
import com.project.schoolmanagement.payload.response.user.StudentResponse;
import com.project.schoolmanagement.payload.response.user.TeacherResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonProgramResponse
{
    private Long lessonProgramId;
    private Day day;
    private LocalTime startTime;
    private LocalTime stopTime;
    private Set<Lesson> lessonName;
    private EducationTerm educationTerm;
    private Set<TeacherResponse> teacherResponseSet;
    private Set<StudentResponse> students;
}
