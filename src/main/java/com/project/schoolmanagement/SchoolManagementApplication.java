package com.project.schoolmanagement;


import com.project.schoolmanagement.entity.enums.Gender;
import com.project.schoolmanagement.entity.enums.RoleType;
import com.project.schoolmanagement.payload.request.user.AdminRequest;
import com.project.schoolmanagement.service.user.AdminService;
import com.project.schoolmanagement.service.user.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner
{
    private final UserRoleService userRoleService;
    private final AdminService adminService;

    public SchoolManagementApplication(UserRoleService userRoleService, AdminService adminService)
    {
        this.userRoleService = userRoleService;
        this.adminService = adminService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception
    {
        if(userRoleService.getAllUserRole().isEmpty())
        {
            userRoleService.saveUserRole(RoleType.ADMIN);
            userRoleService.saveUserRole(RoleType.MANAGER);
            userRoleService.saveUserRole(RoleType.ASSISTANT_MANAGER);
            userRoleService.saveUserRole(RoleType.STUDENT);
            userRoleService.saveUserRole(RoleType.TEACHER);
            userRoleService.saveUserRole(RoleType.ADVISORY_TEACHER);
        }

        if(adminService.countAllAdmins() == 0)
        {
            AdminRequest adminRequest = new AdminRequest();

            adminRequest.setUsername("superAdmin");
            adminRequest.setSsn("111-11-1111");
            adminRequest.setPassword("240921");
            adminRequest.setName("Enes");
            adminRequest.setSurname("Bahtiyar");
            adminRequest.setBirthPlace("Adana");
            adminRequest.setPhoneNumber("999-999-9999");
            adminRequest.setGender(Gender.MALE);
            adminRequest.setBirthDay(LocalDate.of(1998, 7, 18));

            adminService.saveAdmin(adminRequest);
        }
    }
}









