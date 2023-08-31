package com.project.schoolmanagement.repository.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisorTeacherRepository extends JpaRepository<AdvisoryTeacher, Long>
{

}
