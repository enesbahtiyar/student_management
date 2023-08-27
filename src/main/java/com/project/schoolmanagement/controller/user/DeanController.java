package com.project.schoolmanagement.controller.user;

import com.project.schoolmanagement.payload.request.user.DeanRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.DeanResponse;
import com.project.schoolmanagement.service.user.DeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseMessage<DeanResponse> updateDeanById(@PathVariable Long userId,
                                                        @RequestBody @Valid DeanRequest deanRequest)
    {
        return deanService.updateDeanById(userId, deanRequest);
    }

    @GetMapping("/getAllDeansByPage")
    public Page<DeanResponse> getAllDeansByPage(
            @RequestParam(value = "page")int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(defaultValue = "desc",value = "type") String type
    )
    {
        return deanService.getAllDeansByPage(page,size,sort,type);

    }

}
