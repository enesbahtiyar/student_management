package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Student;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.user.StudentMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.user.StudentRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.StudentResponse;
import com.project.schoolmanagement.repository.user.StudentRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService
{
    private final StudentRepository studentRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final AdvisorTeacherService advisorTeacherService;
    private final PageableHelper pageableHelper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final StudentMapper studentMapper;

    public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest)
    {
        AdvisoryTeacher advisoryTeacher = advisorTeacherService.getAdvisoryTeacherBYId(studentRequest.getAdvisorTeacherId());
        //uniquepropertyvalidator
        uniquePropertyValidator.checkDuplicate(studentRequest.getUsername(),
                                               studentRequest.getSsn(),
                                               studentRequest.getPhoneNumber(),
                                               studentRequest.getEmail());
        //mapping DTO -> Entity
        Student student = studentMapper.mapStudentRequestToStudent(studentRequest);
        student.setAdvisoryTeacher(advisoryTeacher);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        student.setActive(true);
        student.setStudentNumber(getLastNumber());

        Student savedStudent = studentRepository.save(student);
        return ResponseMessage.<StudentResponse>builder()
                .object(studentMapper.mapStudentToStudentResponse(savedStudent))
                .message(SuccessMessages.STUDENT_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private int getLastNumber()
    {
        if(!studentRepository.findStudent())
        {
            //first student
            return 1000;
        }
        return studentRepository.getMaxStudentNumber() + 1;
    }

    public Student isStudentExist(Long id)
    {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
    }

    public ResponseMessage changeStatus(Long id, boolean status)
    {
        Student student = isStudentExist(id);
        student.setActive(status);
        studentRepository.save(student);

        return ResponseMessage.builder()
                .message("student is " + (status ? "active" : "passive"))
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
