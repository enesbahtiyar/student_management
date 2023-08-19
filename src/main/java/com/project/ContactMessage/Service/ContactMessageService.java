package com.project.ContactMessage.Service;

import com.project.ContactMessage.Dto.ContactMessageRequest;
import com.project.ContactMessage.Dto.ContactMessageResponse;
import com.project.ContactMessage.Dto.ContactMessageUpdateRequest;
import com.project.ContactMessage.Entity.ContactMessage;


import com.project.Exceptions.ConflictException;
import com.project.Exceptions.ResourceNotFoundException;
import com.project.ContactMessage.Mapper.ContactMessageMapper;
import com.project.ContactMessage.Repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactMessageService {

    @Autowired
    private  ContactMessageRepository contactMessageRepository;

    @Autowired
    private ContactMessageMapper contactMessageMapper;


    // Create ContactMessage

    @Transactional
    public void createContactMessage (ContactMessageRequest contactMessageRequest) {

        boolean isExist = contactMessageRepository.existsByEmail(contactMessageRequest.getEmail());
        if(isExist){
            throw  new ConflictException("Contact Message Already Exists.");
        }
        ContactMessage newContactMessage =  contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);

        contactMessageRepository.save(newContactMessage);
    }

    // get all ContactMessage

    public List<ContactMessageResponse> getAll () {
        List<ContactMessage> contactMessages = contactMessageRepository.findAll();
        if(contactMessages.isEmpty()){
            throw new ResourceNotFoundException("No Contact Messages found.");
        }
        List<ContactMessageResponse> contactMessageResponseList = new ArrayList<>();
        for(ContactMessage contactMessage: contactMessages){
            ContactMessageResponse contactMessageResponse = contactMessageMapper.contactMessageToContactMessageResponse(contactMessage);
            contactMessageResponseList.add(contactMessageResponse);
        }
        return contactMessageResponseList;
    }

    // get all ContactMessage by page

    public Page<ContactMessageResponse> getAllWithPageable (Pageable pageable) {

        Page<ContactMessage> contactMessagePage = contactMessageRepository.findAll(pageable);

        if(contactMessagePage.isEmpty()){
            throw new ResourceNotFoundException("No Contact Messages found.");
        }
        Page<ContactMessageResponse> contactMessageResponses = contactMessageMapper.contactMessagePageToContactMessageResponse(contactMessagePage);
        return contactMessageResponses;
    }


    // search ContactMessage by subject

    public List<ContactMessageResponse> getBySubject (String subject) {

        if(subject.isEmpty()){
            throw new NullPointerException("Subject is empty, please enter a valid Subject.");
        }

        List<ContactMessage> contactMessageList = contactMessageRepository.getBySubject(subject);
        if(contactMessageList.isEmpty()){
            throw new ResourceNotFoundException("No Contact Messages found by subject: "+subject);
        }

        List<ContactMessageResponse> contactMessageResponses = new ArrayList<>();
        for(ContactMessage contactMessage: contactMessageList){
            ContactMessageResponse contactMessageResponse = contactMessageMapper.contactMessageToContactMessageResponse(contactMessage);
            contactMessageResponses.add(contactMessageResponse);
        }
        return contactMessageResponses;
    }


    // get ContactMessage by email

    public List<ContactMessageResponse> getByEmail (String email) {

        if(email.isEmpty()){
            throw new NullPointerException("Email is empty, please enter a valid Email?");
        }

        List<ContactMessage> contactMessageList = contactMessageRepository.findByEmail(email);
        if(contactMessageList.isEmpty()){
            throw new ResourceNotFoundException("No Contact Messages found by email: "+email);
        }

        List<ContactMessageResponse> contactMessageResponses = new ArrayList<>();
        for(ContactMessage contactMessage: contactMessageList){
            ContactMessageResponse contactMessageResponse = contactMessageMapper.contactMessageToContactMessageResponse(contactMessage);
            contactMessageResponses.add(contactMessageResponse);
        }
        return contactMessageResponses;
    }



    // get ContactMessage by Creation Date


    public List<ContactMessageResponse> getByDate (Integer startYear, Integer startMonth, Integer startDay, Integer endYear, Integer endMonth, Integer endDay) {

        LocalTime startTime = LocalTime.of(0,0,0);
        LocalTime endTime = LocalTime.of(23,59,59);

        LocalDate startDate = LocalDate.of(startYear,startMonth,startDay);
        LocalDate endDate = LocalDate.of(endYear,endMonth,endDay);

        LocalDateTime startLocalDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endLocalDateTime = LocalDateTime.of(endDate, endTime);

        Timestamp startDateTime = Timestamp.valueOf(startLocalDateTime);
        Timestamp endDateTime = Timestamp.valueOf(endLocalDateTime);

        List<ContactMessage> contactMessageList = contactMessageRepository.getByDate(startDateTime,endDateTime);

        if(contactMessageList.isEmpty()){
            throw new ResourceNotFoundException("No Contact Messages found between: "+startDate+" and "+endDate);
        }
        List<ContactMessageResponse> contactMessageResponses = new ArrayList<>();
        for(ContactMessage contactMessage: contactMessageList){
            ContactMessageResponse contactMessageResponse = contactMessageMapper.contactMessageToContactMessageResponse(contactMessage);
            contactMessageResponses.add(contactMessageResponse);
        }
        return contactMessageResponses;
    }

    // get ContactMessage by Creation Time

    public List<ContactMessageResponse> getByTime (Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {

        List<ContactMessage> contactMessageList = contactMessageRepository.findAll();

        List<ContactMessageResponse> contactMessageResponses = new ArrayList<>();
        for(ContactMessage contactMessage: contactMessageList){

            int hour = contactMessage.getCreationDate().toLocalDateTime().getHour();
            int minute = contactMessage.getCreationDate().toLocalDateTime().getMinute();

            LocalTime contactMessageTime = LocalTime.of(hour,minute);

            LocalTime startTime = LocalTime.of(startHour,startMinute,0);
            LocalTime endTime = LocalTime.of(endHour,endMinute,59);



            if ((contactMessageTime.isAfter(startTime)||contactMessageTime.equals(startTime)) && (contactMessageTime.isBefore(endTime) || contactMessageTime.equals(endTime))){
                ContactMessageResponse contactMessageResponse = contactMessageMapper.contactMessageToContactMessageResponse(contactMessage);
                contactMessageResponses.add(contactMessageResponse);}

        }
        if(contactMessageResponses.isEmpty()){
            throw new ResourceNotFoundException("No Contact Messages found between: "+startHour+":"+startMinute+" and "+endHour+":"+endMinute);
        }
        return contactMessageResponses;
    }


    // delete by ID (path)

    @Transactional
    public void deleteByPath (Long id) {

        boolean isExist = contactMessageRepository.existsById(id);
        if(!isExist){
            throw new ResourceNotFoundException("No Contact Messages found by id: "+id);
        }
        contactMessageRepository.deleteById(id);
    }


    // delete by ID (parameter)
    @Transactional
    public void deleteByParameter (Long id) {

        boolean isExist = contactMessageRepository.existsById(id);
        if(!isExist){
            throw new ResourceNotFoundException("No Contact Messages found by id: "+id);
        }
        contactMessageRepository.deleteById(id);
    }

    // update a ContactMessage
    @Transactional
    public void update (Long id, ContactMessageUpdateRequest contactMessageUpdateRequest) {

        ContactMessage existContactMessage = contactMessageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Contact Messages found by id: "+id));
        boolean isEmailExist = contactMessageRepository.existsByEmail(contactMessageUpdateRequest.getEmail());

        if(contactMessageUpdateRequest.getEmail()!=null){
            if(isEmailExist && !existContactMessage.getEmail().equals(contactMessageUpdateRequest.getEmail())){
                throw new ConflictException("Contact Message with email:   "+contactMessageUpdateRequest.getEmail()+"  already exists.");
            }
        }





        existContactMessage = contactMessageMapper.contactMessageUpdateRequestToContactMessage(existContactMessage,contactMessageUpdateRequest);
        contactMessageRepository.save(existContactMessage);

    }

}
