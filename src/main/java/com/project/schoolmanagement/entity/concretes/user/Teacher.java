package com.project.schoolmanagement.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagement.entity.abstracts.User;
import com.project.schoolmanagement.entity.concretes.business.LessonProgram;
import com.project.schoolmanagement.entity.concretes.business.StudentInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Teacher extends User
{
    //TODO: orphan removal should be learned
    @JsonIgnore
    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private AdvisoryTeacher advisoryTeacher;

    @Column(name = "isAdvisor")
    private boolean isAdvisory;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfos;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "teacher_lessonprogram",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "lesson_program_id"))
    private Set<LessonProgram> lessonsProgramList;
}
