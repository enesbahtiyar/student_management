package com.project.schoolmanagement.payload.mappers.user;

import com.project.schoolmanagement.entity.concretes.user.ViceDean;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.payload.request.user.ViceDeanRequest;
import com.project.schoolmanagement.payload.response.user.ViceDeanResponse;
import com.project.schoolmanagement.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViceDeanMapper {

    private final UserRoleService userRoleService;

    public ViceDean mapViceDeanRequestToViceDean(ViceDeanRequest viceDeanRequest){
        return ViceDean.builder()
                .username(viceDeanRequest.getUsername())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .password(viceDeanRequest.getPassword())
                .ssn(viceDeanRequest.getSsn())
                .birthDay(viceDeanRequest.getBirthDay())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .build();
    }

    public ViceDeanResponse mapViceDeanToViceDeanResponse(ViceDean viceDean){
        return ViceDeanResponse.builder()
                .userId(viceDean.getId())
                .username(viceDean.getUsername())
                .name(viceDean.getName())
                .surname(viceDean.getSurname())
                .birthDay(viceDean.getBirthDay())
                .birthPlace(viceDean.getBirthPlace())
                .phoneNumber(viceDean.getPhoneNumber())
                .gender(viceDean.getGender())
                .ssn(viceDean.getSsn())
                .build();
    }

    public ViceDean mapViceDeanRequestToUpdatedViceDean(ViceDeanRequest viceDeanRequest, Long viceDeanId){
        return ViceDean.builder()
                .id(viceDeanId)
                .username(viceDeanRequest.getUsername())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .password(viceDeanRequest.getPassword())
                .ssn(viceDeanRequest.getSsn())
                .birthDay(viceDeanRequest.getBirthDay())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER))
                .build();
    }
}
