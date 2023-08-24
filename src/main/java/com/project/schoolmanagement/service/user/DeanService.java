package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.payload.request.user.DeanRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.DeanResponse;
import com.project.schoolmanagement.repository.user.DeanRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import com.project.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeanService
{
    private final DeanRepository deanRepository;
    private final UserRoleService userRoleService;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final PageableHelper pageableHelper;

    public ResponseMessage<DeanResponse> saveDean(DeanRequest deanRequest)
    {
        uniquePropertyValidator.checkDuplicate(deanRequest.getUsername(),
                                                deanRequest.getSsn(),
                                                deanRequest.getPhoneNumber());

        return null;
    }
}
