package com.project.schoolmanagement.repository.user;

import com.project.schoolmanagement.entity.concretes.user.Admin;
import com.project.schoolmanagement.entity.concretes.user.ViceDean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViceDeanRepository extends JpaRepository<ViceDean, Long>
{
    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    ViceDean findByUsernameEquals(String username);
}
