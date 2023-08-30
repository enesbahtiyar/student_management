package com.project.schoolmanagement.payload.mappers.business;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.payload.request.business.LessonRequest;
import com.project.schoolmanagement.payload.response.business.LessonResponse;
import com.project.schoolmanagement.repository.business.LessonRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LessonMapper
{
    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest)
    {
        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }

    public LessonResponse mapLessonToLessonResponse(Lesson lesson)
    {
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }
}
