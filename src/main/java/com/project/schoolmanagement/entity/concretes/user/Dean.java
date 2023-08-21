package com.project.schoolmanagement.entity.concretes.user;

import com.project.schoolmanagement.entity.abstracts.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Dean extends User
{

}
