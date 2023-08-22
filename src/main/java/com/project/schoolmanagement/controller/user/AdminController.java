package com.project.schoolmanagement.controller.user;


import com.project.schoolmanagement.service.user.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController
{
    private final AdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<> saveAdmin()
    {
        return null;
    }
}
