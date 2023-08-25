package com.project.schoolmanagement.controller.user;

import com.project.schoolmanagement.payload.request.user.DeanRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.DeanResponse;
import com.project.schoolmanagement.service.user.DeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dean")
public class DeanController
{
    private final DeanService deanService;

    @PostMapping("/save")
    public ResponseMessage<DeanResponse> saveDean(@RequestBody @Valid DeanRequest deanRequest)
    {
        return deanService.saveDean(deanRequest);
    }

    @PutMapping("/update/{userId}")
    public ResponseMessage<DeanResponse> updateDeanById(@PathVariable Long id,
                                                        @RequestBody @Valid DeanRequest deanRequest)
    {
        return deanService.updateDeanById(id, deanRequest);
    }
}
