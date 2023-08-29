package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.ViceDean;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.user.ViceDeanMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.user.ViceDeanRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.ViceDeanResponse;
import com.project.schoolmanagement.repository.user.ViceDeanRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViceDeanService {

    private final ViceDeanRepository viceDeanRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final ViceDeanMapper viceDeanMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;



    private ViceDean isViceDeanExist(Long id){
        return viceDeanRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,id))
        );
    }

    public ResponseMessage<ViceDeanResponse> saveViceDean(ViceDeanRequest viceDeanRequest) {

        uniquePropertyValidator.checkDuplicate(viceDeanRequest.getUsername(),
                viceDeanRequest.getSsn(),
                viceDeanRequest.getPhoneNumber());

        ViceDean viceDean = viceDeanMapper.mapViceDeanRequestToViceDean(viceDeanRequest);
        viceDean.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
        viceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));

        ViceDean savedViceDean = viceDeanRepository.save(viceDean);
        return ResponseMessage.<ViceDeanResponse>builder()
                .message(SuccessMessages.VICE_DEAN_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .object(viceDeanMapper.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();

    }


    public ResponseMessage<ViceDeanResponse> updateViceDeanById(Long userId, ViceDeanRequest viceDeanRequest) {
        ViceDean viceDean = isViceDeanExist(userId);
        uniquePropertyValidator.checkUniqueProperties(viceDean, viceDeanRequest );
        ViceDean updatedViceDean = viceDeanMapper.mapViceDeanRequestToUpdatedViceDean(viceDeanRequest,userId);
        ViceDean savedViceDean = viceDeanRepository.save(updatedViceDean);
        return ResponseMessage.<ViceDeanResponse>builder()
                .message(SuccessMessages.VICE_DEAN_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(viceDeanMapper.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();
    }



}