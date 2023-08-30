package com.project.schoolmanagement.service.user;

import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.payload.mappers.user.AdminMapper;
import com.project.schoolmanagement.payload.request.user.TeacherRequest;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.payload.response.user.TeacherResponse;
import com.project.schoolmanagement.repository.user.TeacherRepository;
import com.project.schoolmanagement.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherService
{
    private final TeacherRepository  teacherRepository;
    private final AdminMapper adminMapper;
    private final LessonService lessonService;

    public ResponseMessage<TeacherResponse> saveTeacher(TeacherRequest teacherRequest)
    {
        //Set<Lesson>
        //we need to get lessons according to id.s

        return null;
    }
}
