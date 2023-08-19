package com.project.ContactMessage.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageResponse {


    private String name;

    private String email;

    private String subject;

    private String message;

    private String creationDate;


}
