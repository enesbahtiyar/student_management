package com.project.schoolmanagement.repository.business;

import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram, Long>
{
    List<LessonProgram> findByTeachers_IdNull();

    List<LessonProgram> findByTeachers_IdNotNull();
}
