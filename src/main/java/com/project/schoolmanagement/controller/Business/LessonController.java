package com.project.schoolmanagement.controller.Business;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.payload.request.business.LessonRequest;
import com.project.schoolmanagement.payload.response.business.LessonResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/** CONTROLLER
// authorization
// requestType -> path/parameter/body
// requested DTO + TDO validation
// requested response DTO
// SERVICE
// validation (existing/nonExisting/date/time/conflict)
// mapping to suitable object
// database related method
// mapping to requested response
// REPO
// suitable query for related service
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController
{
    private final LessonService lessonService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public ResponseMessage<LessonResponse> saveLesson(@Valid @RequestBody LessonRequest lessonRequest)
    {
        return lessonService.saveLesson(lessonRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public ResponseMessage deleteLessonById(@PathVariable Long id)
    {
        return lessonService.deleteLessonById(id);
    }

    @GetMapping("/getByLessonName")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName)
    {
        return lessonService.getLessonByLessonName(lessonName);
    }

    @GetMapping("/getAllLessonByLessonId")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public Set<Lesson> getAllLessonById(@RequestParam(name = "lessonId") Set<Long> idSet)
    {
        return lessonService.getAllLessonByLessonId(idSet);
    }


}
