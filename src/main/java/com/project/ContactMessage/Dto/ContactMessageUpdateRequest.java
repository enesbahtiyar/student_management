package com.project.ContactMessage.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageUpdateRequest {


    private String name;



    @Email(message = "please provide a valid email!")
    private String email;



    private String subject;



    private String message;


}