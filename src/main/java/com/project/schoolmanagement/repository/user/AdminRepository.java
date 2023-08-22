package com.project.schoolmanagement.repository.user;

import com.project.schoolmanagement.entity.concretes.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>
{

}
