package com.project.schoolmanagement.controller.Business;

import com.project.schoolmanagement.entity.concretes.business.EducationTerm;
import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.entity.concretes.user.Student;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.payload.request.business.StudentInfoRequest;
import com.project.schoolmanagement.payload.request.business.UpdateStudentInfoRequest;
import com.project.schoolmanagement.payload.response.business.StudentInfoResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.service.business.EducationTermService;
import com.project.schoolmanagement.service.business.LessonService;
import com.project.schoolmanagement.service.business.StudentInfoService;
import com.project.schoolmanagement.service.user.StudentService;
import com.project.schoolmanagement.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController
{
    private final StudentInfoService studentInfoService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                                @RequestBody @Valid StudentInfoRequest studentInfoRequest)
    {
        return studentInfoService.saveStudentInfo(httpServletRequest, studentInfoRequest);
    }

    @PutMapping("/update/{studentInfoId}")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseMessage<StudentInfoResponse> updateStudentInfo(@RequestBody @Valid UpdateStudentInfoRequest updateStudentInfoRequest,
                                                                  @PathVariable Long studentInfoId)
    {
        return studentInfoService.updateStudentInfo(updateStudentInfoRequest, studentInfoId);
    }

    @GetMapping("/getAllForStudent")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForStudent(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page")int page,
            @RequestParam(value = "size")int size
    )
    {
        return new ResponseEntity<>(studentInfoService.getAllForStudent(httpServletRequest, page, size), HttpStatus.OK);
    }

}
