package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.payload.request.business.LessonProgramRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgramService
{
    public ResponseMessage<LessonProgram> saveLessonProgram(LessonProgramRequest lessonProgramRequest)
    {
    }
}
