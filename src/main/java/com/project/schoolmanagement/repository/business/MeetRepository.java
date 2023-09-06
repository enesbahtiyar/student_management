package com.project.schoolmanagement.repository.business;

import com.project.schoolmanagement.entity.concretes.business.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long>
{
    List<Meet> findByStudentList_IdEquals(Long studentId);

    List<Meet> getByAdvisoryTeacher_IdEquals(Long advisoryTeacherId);

    Page<Meet> getByAdvisoryTeacher_IdEquals(Long advisoryTeacherId, Pageable pageable);

}
