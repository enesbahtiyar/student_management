package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.payload.mappers.business.EducationTermMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.request.business.EducationTermRequest;
import com.project.schoolmanagement.payload.response.business.EducationTermResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.repository.business.EducationTermRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermService
{
    private final EducationTermRepository educationTermRepository;
    private final PageableHelper pageableHelper;
    private final EducationTermMapper educationTermMapper;

    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest)
    {
        validateEducationTermDates(educationTermRequest);
        return null;
    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest)
    {
        if(educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(), educationTermRequest.getStartDate().getYear()))
        {
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }
    }
}
