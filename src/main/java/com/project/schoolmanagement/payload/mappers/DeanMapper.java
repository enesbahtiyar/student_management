package com.project.schoolmanagement.payload.mappers;

import com.project.schoolmanagement.entity.concretes.user.Admin;
import com.project.schoolmanagement.entity.concretes.user.Dean;
import com.project.schoolmanagement.payload.request.user.AdminRequest;
import com.project.schoolmanagement.payload.request.user.DeanRequest;
import com.project.schoolmanagement.payload.response.user.DeanResponse;
import com.project.schoolmanagement.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeanMapper
{
    private final UserRoleService userRoleService;

    public Dean mapAdminRequestToAdmin(DeanRequest deanRequest)
    {
        return Dean.builder()
                .username(deanRequest.getUsername())
                .name(deanRequest.getName())
                .surname(deanRequest.getSurname())
                .password(deanRequest.getPassword())
                .ssn(deanRequest.getSsn())
                .birthDay(deanRequest.getBirthDay())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .build();
    }


    public DeanResponse mapDeanToDeanResponse(Dean dean)
    {
        return DeanResponse.builder()
                .userId(dean.getId())
                .username(dean.getUsername())
                .name(dean.getName())
                .surname(dean.getSurname())
                .birthDay(dean.getBirthDay())
                .birthPlace(dean.getBirthPlace())
                .phoneNumber(dean.getPhoneNumber())
                .gender(dean.getGender())
                .ssn(dean.getSsn())
                .build();
    }
}
