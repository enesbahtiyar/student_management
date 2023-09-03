package com.project.schoolmanagement.repository.business;

import com.project.schoolmanagement.entity.concretes.business.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long>
{

}
