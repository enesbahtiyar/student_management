package com.project.schoolmanagement.entity.concretes.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagement.entity.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends User
{
    private String motherName;

    private String fatherName;

    private int studentNumber;

    private boolean isActive;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "advisory_teacher_id")
    private AdvisoryTeacher advisoryTeacher;
}
