package com.project.schoolmanagement.controller.Business;

import com.project.schoolmanagement.entity.concretes.business.EducationTerm;
import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.entity.concretes.user.Student;
import com.project.schoolmanagement.payload.request.business.StudentInfoRequest;
import com.project.schoolmanagement.payload.response.business.StudentInfoResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.service.business.EducationTermService;
import com.project.schoolmanagement.service.business.LessonService;
import com.project.schoolmanagement.service.business.StudentInfoService;
import com.project.schoolmanagement.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController
{
    private final StudentInfoService studentInfoService;
    private final EducationTermService educationTermService;
    private final LessonService lessonService;
    private final StudentService studentService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                                @RequestBody @Valid StudentInfoRequest studentInfoRequest)
    {
        String teacherUsername = (String) httpServletRequest.getAttribute("username");
        Student student = studentService.isStudentExist(studentInfoRequest.getStudentId());
        EducationTerm educationTerm = educationTermService.isEducationTermExist(studentInfoRequest.getEducationTermId());
        Lesson lesson = lessonService.findLessonById(studentInfoRequest.getLessonId());
        return null;
    }

}
