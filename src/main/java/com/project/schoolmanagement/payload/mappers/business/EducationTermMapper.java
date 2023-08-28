package com.project.schoolmanagement.payload.mappers.business;

import com.project.schoolmanagement.entity.concretes.business.EducationTerm;
import com.project.schoolmanagement.payload.request.business.EducationTermRequest;
import com.project.schoolmanagement.payload.response.business.EducationTermResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EducationTermMapper
{
    public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest)
    {
        return EducationTerm.builder()
                .term(educationTermRequest.getTerm())
                .startDate(educationTermRequest.getStartDate())
                .endDate(educationTermRequest.getEndDate())
                .lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
                .build();
    }

    public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm)
    {
        return EducationTermResponse.builder()
                .id(educationTerm.getId())
                .term(educationTerm.getTerm())
                .startDate(educationTerm.getStartDate())
                .endDate(educationTerm.getEndDate())
                .lastRegistrationDate(educationTerm.getLastRegistrationDate())
                .build();
    }

}
