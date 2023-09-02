package com.project.schoolmanagement.controller.user;

import com.project.schoolmanagement.payload.request.user.TeacherRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.TeacherResponse;
import com.project.schoolmanagement.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController
{
    private final TeacherService teacherService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<TeacherResponse> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest)
    {
        return teacherService.saveTeacher(teacherRequest);
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<TeacherResponse> updateTeacher(@RequestBody @Valid TeacherRequest teacherRequest,
                                                          @PathVariable Long userId)
    {
        return teacherService.updateTeacher(teacherRequest,userId);
    }

}
