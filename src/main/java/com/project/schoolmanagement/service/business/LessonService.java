package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.payload.mappers.business.LessonMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.business.LessonRequest;
import com.project.schoolmanagement.payload.response.business.LessonResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.repository.business.LessonRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService
{
    private final LessonRepository lessonRepository;
    private final PageableHelper pageableHelper;
    private final LessonMapper lessonMapper;

    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest)
    {
        //validate that there is no lessonname exist the same with request
        isLessonExistsByName(lessonRequest.getLessonName());

        Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);
        Lesson savedLesson = lessonRepository.save(lesson);

        return ResponseMessage.<LessonResponse>builder()
                .object(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .message(SuccessMessages.LESSON_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private boolean isLessonExistsByName(String lessonName)
    {
        boolean isLessonExistByName = lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);

        if(isLessonExistByName)
        {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_LESSON_MESSAGE, lessonName));
        }
        else
            return false;
    }
}
