package com.project.schoolmanagement.service.validator;

import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.exception.BadRequestException;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class DateTimeValidator
{
    public void checkTimeWithException(LocalTime start, LocalTime end)
    {
        if(start.isAfter(end) || start.equals(end))
        {
            throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }
    }

    public void checkLessonPrograms(Set<LessonProgram> existLessonProgram, Set<LessonProgram> lessonProgramRequest)
    {
        if(existLessonProgram.isEmpty() && lessonProgramRequest.size() > 1)
        {
            //we habe no lesson program in db for dedicated teacher
            checkTimeAndDayConflict(lessonProgramRequest);
        }
        else
        {
            //we have lesson program in db for dedicated teacher
            checkTimeAndDayConflict(lessonProgramRequest);
            checkConflictWithExistingLessonProgramSet(existLessonProgram, lessonProgramRequest);
        }
    }

    private void checkTimeAndDayConflict(Set<LessonProgram> lessonPrograms)
    {
        Set<String> uniqueLessonProgramsKey = new HashSet<>();
        for (LessonProgram lessonProgram: lessonPrograms)
        {
            String lessonProgramKey = lessonProgram.getDay().name() + lessonProgram.getStartTime();
            if(uniqueLessonProgramsKey.contains(lessonProgramKey))
            {
                throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_EXIST_MESSAGE);
            }
        }
    }

    //TODO: improve conflict validation in respect to finish time lesson
    private void checkConflictWithExistingLessonProgramSet(Set<LessonProgram> existingLessonProgram, Set<LessonProgram> requestedLessonProgram)
    {
        for (LessonProgram lessonProgramData: requestedLessonProgram)
        {
            if(existingLessonProgram.stream().anyMatch(
                    lessonProgram -> lessonProgram.getStartTime().equals(lessonProgramData.getStartTime())
                    &&
                    lessonProgram.getDay().name().equals(lessonProgramData.getDay().name())
            ))
            {
                throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_EXIST_MESSAGE);
            }
        }
    }
}
