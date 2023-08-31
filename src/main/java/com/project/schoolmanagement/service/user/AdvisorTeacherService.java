package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.payload.mappers.user.AdvisorTeacherMapper;
import com.project.schoolmanagement.repository.user.AdvisorTeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class AdvisorTeacherService
{
    private final AdvisorTeacherRepository advisorTeacherRepository;
    private final UserRoleService userRoleService;
    private final AdvisorTeacherMapper advisorTeacherMapper;

    public void saveAdvisorTeacher(Teacher teacher)
    {
        AdvisoryTeacher advisoryTeacher = advisorTeacherMapper.mapTeacherToAdvisorTeacher(teacher);
        advisoryTeacher.setUserRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));
        advisorTeacherRepository.save(advisoryTeacher);
    }
}
