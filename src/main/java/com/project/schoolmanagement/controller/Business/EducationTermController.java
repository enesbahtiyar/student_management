package com.project.schoolmanagement.controller.Business;

import com.project.schoolmanagement.payload.request.business.EducationTermRequest;
import com.project.schoolmanagement.payload.response.business.EducationTermResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.PushBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping("/educationTerms")
@RequiredArgsConstructor
public class EducationTermController
{
    private final EducationTermService educationTermService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(@RequestBody @Valid EducationTermRequest educationTermRequest)
    {
        return educationTermService.saveEducationTerm(educationTermRequest);
    }
}