package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.entity.concretes.business.EducationTerm;
import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.business.EducationTermMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.business.EducationTermRequest;
import com.project.schoolmanagement.payload.response.business.EducationTermResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.repository.business.EducationTermRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermService
{
    private final EducationTermRepository educationTermRepository;
    private final PageableHelper pageableHelper;
    private final EducationTermMapper educationTermMapper;

    private EducationTerm isEducationTermExist(Long id)
    {
        return educationTermRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id)));
    }

    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest)
    {
        validateEducationTermDatesForSave(educationTermRequest);
        EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);

        EducationTerm savedEducationTerm = educationTermRepository.save(educationTerm);

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_SAVE)
                .object(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private void validateEducationTermDatesForUpdate(EducationTermRequest educationTermRequest)
    {
        if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate()))
        {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate()))
        {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }


    private void validateEducationTermDatesForSave(EducationTermRequest educationTermRequest)
    {
        //in each year only one type of FALL_SEMESTER, SPRING_SEMESTER should exist
        if(educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(), educationTermRequest.getStartDate().getYear()))
        {
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }
        validateEducationTermDatesForUpdate(educationTermRequest);
    }

    public ResponseMessage<EducationTermResponse> updateEducationTerm(Long id, EducationTermRequest educationTermRequest)
    {
        isEducationTermExist(id);
        validateEducationTermDatesForUpdate(educationTermRequest);
        EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);

        educationTerm.setId(id);

        EducationTerm updatedEducationTerm = educationTermRepository.save(educationTerm);

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(educationTermMapper.mapEducationTermToEducationTermResponse(updatedEducationTerm))
                .build();
    }

    public EducationTermResponse findEducationTermById(Long id)
    {
        return educationTermMapper.mapEducationTermToEducationTermResponse(isEducationTermExist(id));
    }

    public List<EducationTermResponse> getAllEducationTerm()
    {
        return educationTermRepository.findAll()
                .stream()
                .map(educationTermMapper::mapEducationTermToEducationTermResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<?> deleteEducationTermById(long id)
    {
        isEducationTermExist(id);
        educationTermRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.EDUCATION_TERM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<EducationTermResponse> getAllEducationTermByPage(int page, int size, String sort, String type)
    {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        return educationTermRepository.findAll(pageable).map(educationTermMapper::mapEducationTermToEducationTermResponse);
    }

    public List<EducationTermResponse> getEducationTermByYear(Integer year) {
        List<EducationTerm> educationTerms = educationTermRepository.findAllByStartDateYear(year);
        if(educationTerms.isEmpty()){
            throw new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_BY_YEAR_MESSAGE,year));
        }
        return educationTerms.stream().map(educationTermMapper::mapEducationTermToEducationTermResponse).collect(Collectors.toList());
    }

    public List<EducationTermResponse> getEducationTermByByStartDate(String firstDateString, String secondDateString) {
        try {
            LocalDate firstDate = LocalDate.parse(firstDateString);
            LocalDate secondDate = LocalDate.parse(secondDateString);

            return educationTermRepository.findEducationTermBetweenDates(firstDate, secondDate)
                    .stream().map(educationTermMapper::mapEducationTermToEducationTermResponse).collect(Collectors.toList());
        } catch (DateTimeParseException e) {
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_WRONG_DATE_FORMAT_MESSAGE);
        }
    }

    public List<EducationTermResponse> getEducationTermsByDateSince(String startDateString) {
        try {
            LocalDate startDate = LocalDate.parse(startDateString);
            return educationTermRepository.getEducationTermsByStartDateAfter(startDate)
                    .stream().map(educationTermMapper::mapEducationTermToEducationTermResponse).collect(Collectors.toList());
        } catch (DateTimeParseException e) {
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_WRONG_DATE_FORMAT_MESSAGE);
        }
    }
}
