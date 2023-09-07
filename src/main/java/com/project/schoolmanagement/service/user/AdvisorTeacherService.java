package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.exception.ResourceNotFoundException;
import com.project.schoolmanagement.payload.mappers.user.AdvisorTeacherMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.AdvisoryTeacherResponse;
import com.project.schoolmanagement.repository.user.AdvisorTeacherRepository;
import com.project.schoolmanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvisorTeacherService
{
    private final AdvisorTeacherRepository advisorTeacherRepository;
    private final UserRoleService userRoleService;
    private final AdvisorTeacherMapper advisorTeacherMapper;
    private final PageableHelper pageableHelper;

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

    public AdvisoryTeacher getAdvisorTeacherByUsername(String username)
    {
        return advisorTeacherRepository
                .findByTeacher_UsernameEquals(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE_USERNAME, username)));
    }

    public List<AdvisoryTeacherResponse> getAllAdvisoryTeacher()
    {
        return advisorTeacherRepository.findAll()
                .stream()
                .map(advisorTeacherMapper::mapAdvisoryTeacherToAdvisoryTeacherResponse)
                .collect(Collectors.toList());
    }

    public Page<AdvisoryTeacherResponse> getAllAdvisoryTeacherByPage(int page, int size, String sort, String type)
    {
        return advisorTeacherRepository
                .findAll(pageableHelper.getPageableWithProperties(page,size,sort,type))
                .map(advisorTeacherMapper::mapAdvisoryTeacherToAdvisoryTeacherResponse);
    }

    public ResponseMessage<AdvisoryTeacher> deleteAdvisoryTeacher(Long id)
    {
        AdvisoryTeacher advisoryTeacher = getAdvisoryTeacherBYId(id);
        advisorTeacherRepository.deleteById(id);

        return ResponseMessage.<AdvisoryTeacher>builder()
                .object(advisoryTeacher)
                .message(SuccessMessages.ADVISOR_TEACHER_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
