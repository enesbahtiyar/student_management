package com.project.schoolmanagement.controller.user;

import com.project.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.AdvisoryTeacherResponse;
import com.project.schoolmanagement.service.user.AdvisorTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advisoryTeacher")
@RequiredArgsConstructor
public class AdvisoryTeacherController
{
    private final AdvisorTeacherService advisorTeacherService;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public List<AdvisoryTeacherResponse> getAllAdvisoryTeacher()
    {
        return advisorTeacherService.getAllAdvisoryTeacher();
    }

    @GetMapping("/getAllByPage")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public Page<AdvisoryTeacherResponse> getAllAdvisoryTeacherByPage(
            @RequestParam(value = "page")int page,
            @RequestParam(value = "size")int size,
            @RequestParam(value = "sort")String sort,
            @RequestParam(value = "type")String type)
    {
        return advisorTeacherService.getAllAdvisoryTeacherByPage(page,size,sort,type);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ASSISTANT_MANAGER')")
    public ResponseMessage<AdvisoryTeacher> deleteAdvisoryTeacher(@PathVariable Long id)
    {
        return advisorTeacherService.deleteAdvisoryTeacher(id);
    }
}
