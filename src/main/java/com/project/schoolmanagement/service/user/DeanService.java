package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.Dean;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.DeanMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.user.DeanRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.DeanResponse;
import com.project.schoolmanagement.repository.user.DeanRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeanService
{
    private final DeanRepository deanRepository;
    private final UserRoleService userRoleService;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final PageableHelper pageableHelper;
    private final DeanMapper deanMapper;

    private Dean isDeanExit(Long id)
    {
        return deanRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
    }

    public ResponseMessage<DeanResponse> saveDean(DeanRequest deanRequest)
    {
        uniquePropertyValidator.checkDuplicate(deanRequest.getUsername(),
                                                deanRequest.getSsn(),
                                                deanRequest.getPhoneNumber());


        Dean dean = deanMapper.mapDeanRequestToDean(deanRequest);

        dean.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        Dean savedDean = deanRepository.save(dean);
        return ResponseMessage.<DeanResponse>builder()
                .message(SuccessMessages.DEAN_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .object(deanMapper.mapDeanToDeanResponse(savedDean))
                .build();
    }

    public ResponseMessage<DeanResponse> updateDeanById(Long id, DeanRequest deanRequest)
    {
        Dean dean = isDeanExit(id);

    }
}
