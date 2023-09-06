package com.project.schoolmanagement.controller.Business;

import com.project.schoolmanagement.payload.request.business.MeetRequest;
import com.project.schoolmanagement.payload.response.business.MeetResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.service.business.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/meet")
@RequiredArgsConstructor
public class MeetController
{
    private final MeetService meetService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseMessage<MeetResponse> saveMeet(HttpServletRequest httpServletRequest,
                                                  @RequestBody @Valid MeetRequest meetRequest)
    {
        return meetService.saveMeet(meetRequest, httpServletRequest);
    }


    @PutMapping("/update/{meetId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    public ResponseMessage<MeetResponse> updateMeet(@RequestBody @Valid MeetRequest meetRequest,
                                                    @PathVariable Long meetId)
    {
        return meetService.updateMeet(meetRequest, meetId);
    }

    @GetMapping("/getAllMeetByAdvisorTeacherAsList")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseEntity<List<MeetResponse>> getAllMeetByTeacher(HttpServletRequest httpServletRequest)
    {
        return ResponseEntity.ok(meetService.getAllMeetByAdvisorTeacherAsList(httpServletRequest));
    }

    @GetMapping("/getAllMeetByStudentAsList")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<List<MeetResponse>> getAllMeetByStudent(HttpServletRequest httpServletRequest)
    {
        return ResponseEntity.ok(meetService.getAllMeetByStudentAsList(httpServletRequest));
    }
}
