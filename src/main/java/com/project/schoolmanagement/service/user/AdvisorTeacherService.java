package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.user.AdvisorTeacherMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.repository.user.AdvisorTeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Optional;

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

    /**
     *
     * @param status -> will be advisory or not
     * @param teacher -> teacher from update
     */
    public void updateAdvisorTeacher(boolean status, Teacher teacher)
    {

        Optional<AdvisoryTeacher> advisoryTeacher = advisorTeacherRepository.getAdvisoryTeacherByTeacherId(teacher.getId());

        AdvisoryTeacher.AdvisoryTeacherBuilder advisoryTeacherBuilder = AdvisoryTeacher.builder()
                .teacher(teacher)
                .userRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));
        //do i really have an advisory teacher in db with this id
        if(advisoryTeacher.isPresent())
        {
            //will be this new updatedTeacher really an advisoryTeacher
            if(!status)
            {

                advisorTeacherRepository.deleteById(advisoryTeacher.get().getId());
                //advisoryTeacherBuilder.id(advisoryTeacher.get().getId());
                //advisorTeacherRepository.save(advisoryTeacherBuilder.build());
            }
        }
        else
        {
            if(status)
            {
                advisoryTeacherBuilder.id(teacher.getId());
                advisorTeacherRepository.save(advisoryTeacherBuilder.build());
            }
        }
    }

    public AdvisoryTeacher getAdvisoryTeacherBYId(Long id)
    {
        return advisorTeacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
    }
}
