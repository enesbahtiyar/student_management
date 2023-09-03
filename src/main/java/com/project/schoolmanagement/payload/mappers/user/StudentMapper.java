package com.project.schoolmanagement.payload.mappers.user;

import com.project.schoolmanagement.entity.concretes.user.Student;
import com.project.schoolmanagement.payload.request.user.StudentRequest;
import com.project.schoolmanagement.payload.response.user.StudentResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StudentMapper
{
    public Student mapStudentRequestToStudent(StudentRequest studentRequest)
    {
        return Student.builder()
                .fatherName(studentRequest.getFatherName())
                .motherName(studentRequest.getMotherName())
                .birthDay(studentRequest.getBirthDay())
                .birthPlace(studentRequest.getBirthPlace())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .password(studentRequest.getPassword())
                .username(studentRequest.getUsername())
                .ssn(studentRequest.getSsn())
                .email(studentRequest.getEmail())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .build();
    }


    public StudentResponse mapStudentToStudentResponse(Student student)
    {
        return StudentResponse.builder()
                .userId(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .username(student.getUsername())
                .birthDay(student.getBirthDay())
                .birthPlace(student.getBirthPlace())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .studentNumber(student.getStudentNumber())
                .ssn(student.getSsn())
                .isActive(student.isActive())
                .build();
    }

    public Student mapStudentRequestToUpdatedStudent(StudentRequest studentRequest, Long studentId)
    {
        Student student = mapStudentRequestToStudent(studentRequest);
        student.setId(studentId);
        return student;
    }
}
