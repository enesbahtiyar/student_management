package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Lesson findLessonById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, id)));
    }

    public ResponseMessage deleteLessonById(Long id)
    {
        findLessonById(id);
        lessonRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.LESSON_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName)
    {
        Lesson lesson = lessonRepository.getLessonByLessonName(lessonName).
                orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, lessonName)));

        return ResponseMessage.<LessonResponse>builder()
                .object(lessonMapper.mapLessonToLessonResponse(lesson))
                .message(SuccessMessages.LESSON_FOUND)
                .httpStatus(HttpStatus.FOUND)
                .build();
    }

    public Set<Lesson> getAllLessonByLessonId(Set<Long> idSet)
    {
        idSet.forEach(this::findLessonById);
        return lessonRepository.getLessonByLessonIdIList(idSet);
    }

    public ResponseMessage<List<LessonResponse>> getLessonsByCreditScoreGreaterThan(Integer givenValue) {
        List<LessonResponse> lessonResponse = lessonRepository.getLessonsByCreditScoreGreaterThanEqual(givenValue).
                stream().
                map(lessonMapper::mapLessonToLessonResponse).
                collect(Collectors.toList());

        if(lessonResponse.isEmpty()) {
            return ResponseMessage.<List<LessonResponse>>builder()
                    .message(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, givenValue))
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .object(lessonResponse)
                    .build();
        }

        return ResponseMessage.<List<LessonResponse>>builder()
                .message(String.format(SuccessMessages.LESSON_FOUND, givenValue))
                .httpStatus(HttpStatus.FOUND)
                .object(lessonResponse)
                .build();
    }

    public ResponseMessage<LessonResponse> updateLessonById(Long id, LessonRequest lessonRequest) {
        Lesson lesson = findLessonById(id);
        if(!lesson.getLessonName().equalsIgnoreCase(lessonRequest.getLessonName())){
            if(isLessonExistsByName(lessonRequest.getLessonName())){
                throw  new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_LESSON_MESSAGE,lessonRequest.getLessonName()));
            }
        }
        Lesson updatedLesson = lessonMapper.mapLessonRequestToUpdateLesson(lessonRequest, id);
        Lesson savedLesson = lessonRepository.save(updatedLesson);
        return ResponseMessage.<LessonResponse>builder()
                .message(SuccessMessages.LESSON_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .build();
    }
}
