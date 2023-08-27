package com.project.schoolmanagement.controller.user;

import com.project.schoolmanagement.payload.request.user.ViceDeanRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.ViceDeanResponse;
import com.project.schoolmanagement.service.user.ViceDeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/viceDean")
@RequiredArgsConstructor
public class ViceDeanController {

    public final ViceDeanService viceDeanService;



    @PostMapping("/save")
    public ResponseMessage<ViceDeanResponse> saveViceDean(@Valid @RequestBody ViceDeanRequest viceDeanRequest){
        return viceDeanService.saveViceDean(viceDeanRequest);
    }

    @PutMapping("/update/{userId}")
    public ResponseMessage<ViceDeanResponse> updateViceDeanById (@PathVariable Long userId, @RequestBody @Valid ViceDeanRequest viceDeanRequest){
        return viceDeanService.updateViceDeanById(userId, viceDeanRequest);
    }

}
