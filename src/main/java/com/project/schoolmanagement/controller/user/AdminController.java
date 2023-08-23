package com.project.schoolmanagement.controller.user;


import com.project.schoolmanagement.payload.request.user.AdminRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.AdminResponse;
import com.project.schoolmanagement.service.user.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController
{
    private final AdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<AdminResponse>> saveAdmin(@RequestBody @Valid AdminRequest adminRequest)
    {
        return ResponseEntity.ok(adminService.saveAdmin(adminRequest));
    }
}
