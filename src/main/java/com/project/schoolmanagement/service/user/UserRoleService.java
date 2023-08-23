package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.abstracts.User;
import com.project.schoolmanagement.entity.concretes.user.UserRole;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.PushBuilder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService
{
    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType)
    {
        return userRoleRepository.findByEnumRoleEquals(roleType)
                .orElseThrow(() -> new ConflictException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<UserRole> getAllUserRole()
    {
        return userRoleRepository.findAll();
    }

    public UserRole saveUserRole(RoleType roleType)
    {
        if(userRoleRepository.existsByEnumRoleEquals(roleType))
        {
            throw new ConflictException(ErrorMessages.ROLE_ALREADY_EXIST);
        }

        UserRole userRole = UserRole.builder()
                .roleType(roleType)
                .build();

        return userRoleRepository.save(userRole);
    }
}
