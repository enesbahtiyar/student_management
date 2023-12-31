package com.project.schoolmanagement.repository.business;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>
{
    boolean existsLessonByLessonNameEqualsIgnoreCase(String lessonName);

    Optional<Lesson> getLessonByLessonName(String lessonName);

    //Set<Lesson> getLessonByLessonIds(Set<Long> lessonIds);

    //pay attention of usage "lessonIds" parameter
    @Query(value = "SELECT l FROM Lesson l WHERE l.id IN :lessonIds")
    Set<Lesson>getLessonByLessonIdIList(Set<Long> lessonIds);

    List<Lesson> getLessonsByCreditScoreGreaterThanEqual(Integer creditScore);
}
