package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.entity.concretes.business.EducationTerm;
import com.project.schoolmanagement.entity.concretes.business.Lesson;
import com.project.schoolmanagement.entity.concretes.business.StudentInfo;
import com.project.schoolmanagement.entity.concretes.user.Student;
import com.project.schoolmanagement.entity.concretes.user.Teacher;
import com.project.schoolmanagement.entity.enums.Note;
import com.project.schoolmanagement.exception.ConflictException;
import com.project.schoolmanagement.payload.mappers.business.StudentInfoMapper;
import com.project.schoolmanagement.payload.messages.ErrorMessages;
import com.project.schoolmanagement.payload.messages.SuccessMessages;
import com.project.schoolmanagement.payload.request.business.StudentInfoRequest;
import com.project.schoolmanagement.payload.response.business.StudentInfoResponse;
import com.project.schoolmanagement.payload.response.message.ResponseMessage;
import com.project.schoolmanagement.repository.business.StudentInfoRepository;
import com.project.schoolmanagement.service.user.StudentService;
import com.project.schoolmanagement.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class StudentInfoService
{
    private final StudentInfoRepository studentInfoRepository;
    private final EducationTermService educationTermService;
    private final LessonService lessonService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final StudentInfoMapper studentInfoMapper;

    @Value("${midterm.exam.impact.percentage}")
    private Double midtermExamPercentage;
    @Value("${final.exam.impact.percentage}")
    private Double finalExamPercentage;

    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest, StudentInfoRequest studentInfoRequest)
    {
        String teacherUsername = (String) httpServletRequest.getAttribute("username");
        Teacher teacher = teacherService.getTeacherByUsername(teacherUsername);
        Student student = studentService.isStudentExist(studentInfoRequest.getStudentId());
        EducationTerm educationTerm = educationTermService.isEducationTermExist(studentInfoRequest.getEducationTermId());
        Lesson lesson = lessonService.findLessonById(studentInfoRequest.getLessonId());
        //does student really have only one lesson according to this lesson name
        checkSameLesson(studentInfoRequest.getStudentId(), lesson.getLessonName());
        //Check note
        Double examAverage = calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam());
        Note note = checkLetterGrade(examAverage);
        //mapper
        StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
                studentInfoRequest,
                note,
                examAverage);

        //set values that do not exist in mappers
        studentInfo.setStudent(student);
        studentInfo.setEducationTerm(educationTerm);
        studentInfo.setTeacher(teacher);
        studentInfo.setLesson(lesson);

        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);

        return ResponseMessage.<StudentInfoResponse>builder()
                .message(SuccessMessages.STUDENT_INFO_SAVE)
                .object(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private void checkSameLesson(Long studentId, String lessonName)
    {
        boolean isLessonDuplicationExist = studentInfoRepository.getAllByStudentId_Id(studentId)
                .stream()
                .anyMatch((e) -> e.getLesson().getLessonName().equalsIgnoreCase(lessonName));

        if(isLessonDuplicationExist)
        {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_LESSON_MESSAGE, lessonName));
        }
    }

    private Double calculateExamAverage(Double midtermExam, Double finalExam)
    {
        return ((midtermExam * midtermExamPercentage) + (finalExam * finalExamPercentage));
    }

    private Note checkLetterGrade(Double average)
    {
        if(average<50.0) {
            return Note.FF;
        } else if (average>=50.0 && average<55) {
            return Note.DD;
        } else if (average>=55.0 && average<60) {
            return Note.DC;
        } else if (average>=60.0 && average<65) {
            return Note.CC;
        } else if (average>=65.0 && average<70) {
            return Note.CB;
        } else if (average>=70.0 && average<75) {
            return Note.BB;
        } else if (average>=75.0 && average<80) {
            return Note.BA;
        } else {
            return Note.AA;
        }
    }
}
