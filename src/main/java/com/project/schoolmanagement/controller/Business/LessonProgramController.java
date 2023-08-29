package com.project.schoolmanagement.controller.Business;

import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.payload.request.business.LessonProgramRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.service.user.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController
{
    private final LessonProgramService lessonProgramService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'AssıstantManager')")
    public ResponseMessage<LessonProgram> saveLessonProgram(@RequestBody @Valid LessonProgramRequest lessonProgramRequest)
    {
        //return lessonProgramService.saveLessonProgram(lessonProgramRequest);
        return null;
    }
}
