package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.Admin;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.payload.mappers.AdminMapper;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.user.AdminRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.AdminResponse;
import com.project.schoolmanagement.repository.user.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService
{
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;
    private final UserRoleService userRoleService;

    public ResponseMessage<AdminResponse> saveAdmin(AdminRequest adminRequest)
    {
        //map dta -> entity
        Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest);

        //setting role type from roles table
        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

        Admin savedAdmin = adminRepository.save(admin);

        //superAdmin will not be deleted or changed
        if(Objects.equals(adminRequest.getUsername(), "superAdmin"))
        {
            admin.setBuiltIn(true);
        }

        //returning response dto by mapping the saved version of admin.
        return ResponseMessage.<AdminResponse>builder()
                .message(SuccessMessages.ADMIN_CREATE)
                .object(adminMapper.mapAdminToAdminResponse(savedAdmin))
                .build();
    }
}
