package com.project.ContactMessage.Controller;

import com.project.ContactMessage.Dto.ContactMessageRequest;
import com.project.ContactMessage.Dto.ContactMessageResponse;
import com.project.ContactMessage.Dto.ContactMessageUpdateRequest;
import com.project.ContactMessage.Service.ContactMessageService;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contactMessages")
public class ContactMessageController
{
    @Autowired
    private  ContactMessageService contactMessageService;

    // Create ContactMessage
    @PostMapping() //http://localhost:8080/contactMessages
    public ResponseEntity<Map<String ,String >> createContactMessage(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
        contactMessageService.createContactMessage(contactMessageRequest);
        Map<String,String> map= new HashMap<>();
        map.put("message","Contact Message created successfully.");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    // get all ContactMessage
    @GetMapping() //http://localhost:8080/contactMessages
    public ResponseEntity<List<ContactMessageResponse>> getAll(){
        List <ContactMessageResponse> contactMessageResponses = contactMessageService.getAll();
        return new ResponseEntity<>(contactMessageResponses, HttpStatus.FOUND);
    }

    // get all ContactMessage by page

    @GetMapping("/page") //http://localhost:8080/contactMessages/page?page=2&size=3&sort=name&direction=ASC
    public ResponseEntity<Page<ContactMessageResponse>> getAllByPage(@RequestParam("page") int page,
                                                                     @RequestParam("size") int size,
                                                                     @RequestParam ("sort") String sort,
                                                                     @RequestParam ("type") Sort.Direction type){
        Pageable pageable = PageRequest.of(page,size,Sort.by(type,sort));

        Page<ContactMessageResponse> contactMessageResponses = contactMessageService.getAllWithPageable(pageable);
        return new ResponseEntity<>(contactMessageResponses, HttpStatus.FOUND);
    }

    // search ContactMessage by subject

    @GetMapping("/subject") //http://localhost:8080/contactMessages/subject?subject=
    public ResponseEntity<List<ContactMessageResponse>> getBySubject (@RequestParam String subject){
        List<ContactMessageResponse> contactMessageResponses = contactMessageService.getBySubject(subject);
        return new ResponseEntity<>(contactMessageResponses, HttpStatus.FOUND);
    }

    // get ContactMessage by email

    @GetMapping("/email") //http://localhost:8080/contactMessages/email?email=
    public ResponseEntity<List<ContactMessageResponse>> getByEmail (@RequestParam String email){
        List<ContactMessageResponse> contactMessageResponses = contactMessageService.getByEmail(email);
        return new ResponseEntity<>(contactMessageResponses, HttpStatus.FOUND);
    }

    // get ContactMessage by Creation Date
    @GetMapping("/getByDate")
    //http://localhost:8080/contactMessages/getByDate?startYear=2023&startMonth=8&startDay=15&endYear=2023&endMonth=8&endDay=16
    public ResponseEntity<List<ContactMessageResponse>> getByDate (@RequestParam("startYear") Integer startYear,
                                                                   @RequestParam("startMonth") Integer startMonth,
                                                                   @RequestParam("startDay") Integer startDay,
                                                                   @RequestParam("endYear") Integer endYear,
                                                                   @RequestParam("endMonth") Integer endMonth,
                                                                   @RequestParam("endDay") Integer endDay
    ){
        List<ContactMessageResponse> contactMessageResponses = contactMessageService
                .getByDate(startYear, startMonth,startDay,endYear,endMonth,endDay);
        return new ResponseEntity<>(contactMessageResponses, HttpStatus.FOUND);
    }


    // get ContactMessage by Creation Time
    @GetMapping("/getByTime")
    //http://localhost:8080/contactMessages/getByTime?startHour=10&startMinute=30&endHour=22&endMinute=0
    public ResponseEntity<List<ContactMessageResponse>> getByTime (@RequestParam("startHour") Integer startHour,
                                                                   @RequestParam("startMinute") Integer startMinute,
                                                                   @RequestParam("endHour") Integer endHour,
                                                                   @RequestParam("endMinute") Integer endMinute
    ) {
        List<ContactMessageResponse> contactMessageResponses = contactMessageService
                .getByTime(startHour,startMinute,endHour,endMinute);
        return new ResponseEntity<>(contactMessageResponses, HttpStatus.FOUND);
    }

    // delete by ID (path)
    @DeleteMapping("/{id}") //http://localhost:8080/contactMessages/{id}
    public ResponseEntity<Map<String , String >> deleteBYPath(@PathVariable Long id){
        contactMessageService.deleteByPath(id);
        Map<String , String > map = new HashMap<>();
        map.put("message","Contact Message with id: "+id+" deleted successfully.");
        map.put("status","true");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    // delete by ID (parameter)
    @DeleteMapping("/id") //http://localhost:8080/contactMessages/id?id=
    public ResponseEntity<Map<String , String >> deleteByParameter(@RequestParam Long id) {
        contactMessageService.deleteByParameter(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Contact Message with id: " + id + " deleted successfully.");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // update a ContactMessage
    @PutMapping ("/updateById") //http://localhost:8080/contactMessages/updateById?id=
    public ResponseEntity<Map<String , String >> update(@Valid @RequestParam Long id,
                                                        @RequestBody ContactMessageUpdateRequest contactMessageUpdateRequest){
        contactMessageService.update(id, contactMessageUpdateRequest);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Contact Message with id: " + id + " updated successfully.");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
