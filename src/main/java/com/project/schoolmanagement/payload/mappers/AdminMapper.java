package com.project.schoolmanagement.payload.mappers;

import com.project.schoolmanagement.entity.concretes.user.Admin;
import com.project.schoolmanagement.payload.request.user.AdminRequest;
import com.project.schoolmanagement.payload.response.user.AdminResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for admin
 * @author enes
 */
@Component
public class AdminMapper
{
    /**
     *
     * @param adminRequest Dto from ui
     * @return entity for db
     */
    public Admin mapAdminRequestToAdmin(AdminRequest adminRequest)
    {
        return Admin.builder()
                .username(adminRequest.getUsername())
                .name(adminRequest.getName())
                .surname(adminRequest.getSurname())
                .password(adminRequest.getPassword())
                .ssn(adminRequest.getSsn())
                .birthDay(adminRequest.getBirthDay())
                .birthPlace(adminRequest.getBirthPlace())
                .phoneNumber(adminRequest.getPhoneNumber())
                .gender(adminRequest.getGender())
                .build();
    }

    public AdminResponse mapAdminToAdminResponse(Admin admin)
    {
        return AdminResponse.builder()
                .userId(admin.getId())
                .username(admin.getUsername())
                .name(admin.getName())
                .surname(admin.getSurname())
                .ssn(admin.getSsn())
                .birthDay(admin.getBirthDay())
                .birthPlace(admin.getBirthPlace())
                .phoneNumber(admin.getPhoneNumber())
                .gender(admin.getGender())
                .build();
    }
}
