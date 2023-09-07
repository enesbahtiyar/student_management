package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Student;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.user.StudentMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.business.ChooseLessonProgramWithIdRequest;
import com.project.schoolmanagement.payload.request.user.StudentRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.StudentResponse;
import com.project.schoolmanagement.repository.user.StudentRepository;
import com.project.schoolmanagement.service.business.LessonProgramService;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.validator.DateTimeValidator;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final LessonProgramService lessonProgramService;
    private final DateTimeValidator dateTimeValidator;

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

    public ResponseMessage<StudentResponse> updateStudent(Long id, StudentRequest studentRequest)
    {
        Student student = isStudentExist(id);
        AdvisoryTeacher advisoryTeacher = advisorTeacherService.getAdvisoryTeacherBYId(studentRequest.getAdvisorTeacherId());
        //existing student is being used for validation
        uniquePropertyValidator.checkUniqueProperties(student, studentRequest);

        Student studentForUpdate = studentMapper.mapStudentRequestToUpdatedStudent(studentRequest, id);
        studentForUpdate.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        studentForUpdate.setAdvisoryTeacher(advisoryTeacher);
        studentForUpdate.setStudentNumber(student.getStudentNumber());
        studentForUpdate.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        studentForUpdate.setActive(true);

        Student updatedStudent = studentRepository.save(studentForUpdate);

        return ResponseMessage.<StudentResponse>builder()
                .message(SuccessMessages.STUDENT_UPDATE)
                .object(studentMapper.mapStudentToStudentResponse(updatedStudent))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<StudentResponse> getStudentsByName(String studentName)
    {
        return studentRepository.getStudentsByNameContaining(studentName)
                .stream()
                .map(studentMapper::mapStudentToStudentResponse)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getAllByAdvisoryTeacherUsername(HttpServletRequest httpServletRequest)
    {
        String userName = (String) httpServletRequest.getAttribute("username");

        return studentRepository.getStudentByAdvisoryTeacher_Username(userName)
                .stream()
                .map(studentMapper::mapStudentToStudentResponse)
                .collect(Collectors.toList());
    }

    public Student isStudentExistByUsername(String username)
    {
        Student student = studentRepository.findByUsernameEquals(username);

        if(student == null)
        {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE_USERNAME, username));
        }

        return student;
    }

    public ResponseMessage<StudentResponse> chooseLesson(HttpServletRequest httpServletRequest, ChooseLessonProgramWithIdRequest chooseLessonProgramWithIdRequest)
    {
        String username = (String) httpServletRequest.getAttribute("username");
        Student student = isStudentExistByUsername(username);
        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(chooseLessonProgramWithIdRequest.getLessonProgramId());
        Set<LessonProgram> existingLessonPrograms = student.getLessonsProgramList();

        dateTimeValidator.checkLessonPrograms(existingLessonPrograms, lessonPrograms);
        existingLessonPrograms.addAll(lessonPrograms);
        student.setLessonsProgramList(existingLessonPrograms);

        Student updatedStudent = studentRepository.save(student);

        return ResponseMessage.<StudentResponse>builder()
                .message(SuccessMessages.STUDENT_UPDATE)
                .object(studentMapper.mapStudentToStudentResponse(updatedStudent))
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public List<Student> getStudentByIdList(Long[] id)
    {
        return studentRepository.findByIdEquals(id);
    }

    public Page<StudentResponse> getAllStudentByPAge(int page, int size, String sort, String type) {
        return studentRepository
                .findAll(pageableHelper.getPageableWithProperties(page,size,sort,type))
                .map(studentMapper::mapStudentToStudentResponse);
    }
}
