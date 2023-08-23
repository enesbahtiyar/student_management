package com.project.schoolmanagement.repository.user;

import com.project.schoolmanagement.entity.concretes.user.UserRole;
import com.project.schoolmanagement.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>
{
    @Query("SELECT r FROM UserRole r WHERE r.roleType = ?1")
    Optional<UserRole> findByEnumRoleEquals(RoleType roleType);

    @Query("SELECT (count (r)>0) FROM UserRole r WHERE r.roleType = ?1")
    boolean existsByEnumRoleEquals(RoleType roleType);
}
