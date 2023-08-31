package com.project.schoolmanagement.controller.Business;

import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.payload.request.business.LessonProgramRequest;
import com.project.schoolmanagement.payload.response.business.LessonProgramResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.service.business.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController
{
    private final LessonProgramService lessonProgramService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@RequestBody @Valid LessonProgramRequest lessonProgramRequest)
    {
        return lessonProgramService.saveLessonProgram(lessonProgramRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public ResponseMessage deleteLessonProgram(@PathVariable Long id)
    {
        return lessonProgramService.deleteLessonProgramById(id);
    }

    @GetMapping("/getAllUnassigned")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAllUnassigned()
    {
        return lessonProgramService.getAllLessonProgramUnassigned();
    }


    @GetMapping("/getAllAssigned")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAllAssigned()
    {
        return lessonProgramService.getAllLessonProgramAssigned();
    }
}
