package com.project.schoolmanagement.repository.user;

import com.project.schoolmanagement.entity.concretes.user.Admin;
import com.project.schoolmanagement.entity.concretes.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    Student findByUsernameEquals(String username);

    @Query(value = "SELECT (count (s) > 0) FROM Student s")
    boolean findStudent();

    @Query(value = "SELECT MAX(s.studentNumber) FROM Student s")
    int getMaxStudentNumber();

    List<Student> getStudentsByNameContaining(String name);

    @Query(value = "SELECT s FROM Student s WHERE s.advisoryTeacher.teacher.username = :username")
    List<Student> getStudentByAdvisoryTeacher_Username(String username);

    @Query("SELECT s FROM Student  s WHERE s.id IN :id")
    List<Student> findByIdEquals(Long[] id);
}
