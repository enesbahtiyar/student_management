package com.project.ContactMessage.Mapper;

import com.project.ContactMessage.Dto.ContactMessageRequest;
import com.project.ContactMessage.Dto.ContactMessageResponse;
import com.project.ContactMessage.Dto.ContactMessageUpdateRequest;
import com.project.ContactMessage.Entity.ContactMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactMessageMapper {

    public ContactMessageResponse contactMessageToContactMessageResponse(ContactMessage contactMessage){
        ContactMessageResponse contactMessageResponse = new ContactMessageResponse();
        contactMessageResponse.setName(contactMessage.getName());
        contactMessageResponse.setEmail(contactMessage.getEmail());
        contactMessageResponse.setSubject(contactMessage.getSubject());
        contactMessageResponse.setMessage(contactMessage.getMessage());
        String date = String.valueOf(contactMessage.getCreationDate()).substring(0,16);
        contactMessageResponse.setCreationDate(date);
        return contactMessageResponse;
    }

    public ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest){
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(contactMessageRequest.getName());
        contactMessage.setEmail(contactMessageRequest.getEmail());
        contactMessage.setSubject(contactMessageRequest.getSubject());
        contactMessage.setMessage(contactMessageRequest.getMessage());
        return contactMessage;
    }


    public Page<ContactMessageResponse> contactMessagePageToContactMessageResponse(Page<ContactMessage> contactMessagePage) {
        List<ContactMessageResponse> contactMessageResponseList = new ArrayList<>();

        for (ContactMessage contactMessage : contactMessagePage.getContent()) {
            ContactMessageResponse contactMessageResponse = new ContactMessageResponse();


            contactMessageResponse.setName(contactMessage.getName());
            contactMessageResponse.setMessage(contactMessage.getMessage());
            contactMessageResponse.setEmail(contactMessage.getEmail());
            contactMessageResponse.setSubject(contactMessage.getSubject());
            String date = String.valueOf(contactMessage.getCreationDate()).substring(0,16);
            contactMessageResponse.setCreationDate(date);

            contactMessageResponseList.add(contactMessageResponse);
        }

        return new PageImpl<>(contactMessageResponseList, contactMessagePage.getPageable(), contactMessagePage.getTotalElements());
    }


    public ContactMessage contactMessageUpdateRequestToContactMessage(ContactMessage contactMessage, ContactMessageUpdateRequest contactMessageUpdateRequest){
        if(contactMessageUpdateRequest.getName() !=null) {
            contactMessage.setName(contactMessageUpdateRequest.getName());
        }

        if(contactMessageUpdateRequest.getEmail() !=null) {
            contactMessage.setEmail(contactMessageUpdateRequest.getEmail());
        }

        if(contactMessageUpdateRequest.getSubject() !=null) {
            contactMessage.setSubject(contactMessageUpdateRequest.getSubject());
        }

        if(contactMessageUpdateRequest.getMessage() !=null) {
            contactMessage.setMessage(contactMessageUpdateRequest.getMessage());
        }

        return contactMessage;
    }




}