package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.entity.concretes.business.EducationTerm;
import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.business.LessonProgramMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.business.LessonProgramRequest;
import com.project.schoolmanagement.payload.response.business.LessonProgramResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.repository.business.LessonProgramRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService
{
    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final PageableHelper pageableHelper;
    private final DateTimeValidator dateTimeValidator;
    private final LessonProgramMapper lessonProgramMapper;


    //TODO
    // program to interface not in implentation
    // bookmarks
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest)
    {
        //I need list of lessons to create a lesson program
        Set<Lesson> lessons = lessonService.getAllLessonByLessonId(lessonProgramRequest.getLessonIdList());
        //i need an education term
        EducationTerm educationTerm = educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());
        //time validations
        dateTimeValidator.checkTimeWithException(lessonProgramRequest.getStartTime(), lessonProgramRequest.getStopTime());

        LessonProgram lessonProgram = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(lessonProgramRequest, lessons, educationTerm);
        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);


        return ResponseMessage.<LessonProgramResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                .object(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public LessonProgram findLessonProgramById(Long id)
    {
        return lessonProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE, id)));
    }

    public ResponseMessage deleteLessonProgramById(Long id)
    {
        findLessonProgramById(id);
        lessonProgramRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.LESSON_PROGRAM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<LessonProgramResponse> getAllLessonProgramUnassigned()
    {
        return lessonProgramRepository.findByTeachers_IdNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public List<LessonProgramResponse> getAllLessonProgramAssigned()
    {
        return lessonProgramRepository.findByTeachers_IdNotNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public Set<LessonProgram> getLessonProgramById(Set<Long> lessonProgramSet)
    {
        return lessonProgramSet.stream()
                .map(this::findLessonProgramById)
                .collect(Collectors.toSet());
    }

    public Set<LessonProgramResponse> getLessonProgramByTeacher(HttpServletRequest httpServletRequest)
    {
        String username = (String) httpServletRequest.getAttribute("username");

        return lessonProgramRepository.getLessonProgramByTeachersUsername(username)
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());
    }

    public Set<LessonProgramResponse> getLessonProgramByStudent(HttpServletRequest httpServletRequest)
    {
        String username = (String) httpServletRequest.getAttribute("username");

        return lessonProgramRepository.getLessonProgramByStudentsUsername(username)
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());
    }
}
