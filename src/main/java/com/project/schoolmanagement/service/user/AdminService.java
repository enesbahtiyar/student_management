package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.Admin;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.AdminMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.user.AdminRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.AdminResponse;
import com.project.schoolmanagement.repository.user.AdminRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService
{
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;
    private final UserRoleService userRoleService;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final PageableHelper pageableHelper;

    public ResponseMessage<AdminResponse> saveAdmin(AdminRequest adminRequest)
    {
        uniquePropertyValidator.checkDuplicate(adminRequest.getUsername(),
                                              adminRequest.getSsn(),
                                              adminRequest.getPhoneNumber());
        //map dta -> entity
        Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest);

        //setting role type from roles table
        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

        //superAdmin will not be deleted or changed
        if(Objects.equals(adminRequest.getUsername(), "superAdmin"))
        {
            admin.setBuiltIn(true);
        }

        Admin savedAdmin = adminRepository.save(admin);

        //returning response dto by mapping the saved version of admin.
        return ResponseMessage.<AdminResponse>builder()
                .message(SuccessMessages.ADMIN_CREATE)
                .object(adminMapper.mapAdminToAdminResponse(savedAdmin))
                .build();
    }

    public Page<Admin> getAllAdminsByPage(int page, int size, String sort, String type)
    {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        return adminRepository.findAll(pageable);
    }

    public Long countAllAdmins()
    {
        return adminRepository.count();
    }

    public String deleteById(Long id)
    {
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isEmpty())
        {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id));
        }
        else if (admin.get().isBuiltIn())
        {
            throw new ConflictException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        adminRepository.deleteById(id);

        return String.format(SuccessMessages.ADMIN_DELETE, id);
    }
}
