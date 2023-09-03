package com.project.schoolmanagement.controller.user;

import com.project.schoolmanagement.payload.request.user.StudentRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.StudentResponse;
import com.project.schoolmanagement.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController
{
    private final StudentService studentService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<StudentResponse> saveStudent(@Valid @RequestBody StudentRequest studentRequest)
    {
        return studentService.saveStudent(studentRequest);
    }

    @GetMapping("/changeStatus")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    private ResponseMessage changeStatus(@RequestParam Long id, @RequestParam boolean status)
    {
        return studentService.changeStatus(id,status);
    }
}
