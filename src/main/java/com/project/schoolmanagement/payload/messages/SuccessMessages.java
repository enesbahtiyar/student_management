package com.project.schoolmanagement.payload.messages;

public abstract class SuccessMessages
{
    //Admin
    public static final String ADMIN_CREATE = "Admin created successfully";
    public static final String ADMIN_DELETE = "Admin is deleted successfully";


    //Dean
    public static final String DEAN_SAVE = "Dean is Saved";
    public static final String DEAN_UPDATE = "Dean is Updated Successfully";
    public static final String DEAN_FOUND = "Dean is Found Successfully";
    public static final String DEAN_DELETE = "Dean is Deleted Successfully";

    //ViceDean
    public static final String VICE_DEAN_SAVE = "ViceDean is Saved";
    public static final String VICE_DEAN_UPDATE = "ViceDean is Updated Successfully";

    //Education term
    public static final String EDUCATION_TERM_SAVE = "Education Term is Saved";
    public static final String EDUCATION_TERM_UPDATE = "Education Term is Updated Successfully";
    public static final String EDUCATION_TERM_DELETE = "Education Term is Deleted Successfully";

    //Lesson
    public static final String LESSON_SAVE = "Lesson is Saved";
    public static final String LESSON_UPDATE = "Lesson is Updated Successfully";
    public static final String LESSON_DELETE = "Lesson is Deleted Successfully";
    public static final String LESSON_FOUND = "Lesson is Found Successfully";

    //Lesson program
    public static final String LESSON_PROGRAM_SAVE = "Lesson Program is Saved";
    public static final String LESSON_PROGRAM_UPDATE = "Lesson Program is Updated Successfully";
    public static final String LESSON_PROGRAM_DELETE = "Lesson Program is Deleted Successfully";
    public static final String LESSON_PROGRAM_ADD_TO_TEACHER = "Lesson Program added to teacher";
    public static final String LESSON_PROGRAM_ADD_TO_STUDENT = "Lesson Program added to student";
}
