package com.project.schoolmanagement.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvisoryTeacherResponse
{
    private Long advisoryTeacherId;
    private String teacherName;
    private String teacherSsn;
    private String teacherSurname;
}
