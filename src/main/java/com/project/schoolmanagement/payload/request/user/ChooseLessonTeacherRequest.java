package com.project.schoolmanagement.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChooseLessonTeacherRequest
{
    @NotNull
    @Size(min = 1, message = "Lessons must not be empty")
    private Set<Long> lessonProgramId;

    @NotNull(message = "please select teacher")
    private Long teacherId;
}
