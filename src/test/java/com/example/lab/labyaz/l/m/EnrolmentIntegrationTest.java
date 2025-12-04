package com.example.lab.labyaz.l.m;

import com.example.lab.labyaz.l.m.model.Course;
import com.example.lab.labyaz.l.m.model.Enrollment;
import com.example.lab.labyaz.l.m.model.Student;
import com.example.lab.labyaz.l.m.model.Teacher;
import com.example.lab.labyaz.l.m.service.CourseService;
import com.example.lab.labyaz.l.m.service.EnrollmentService;
import com.example.lab.labyaz.l.m.service.StudentService;
import com.example.lab.labyaz.l.m.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

public class EnrolmentIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnrollStudentInCourse() throws Exception {
        Teacher teacher = teacherService.create(new Teacher(1L, "John", "Doe"));
        Student student = studentService.create(new Student(1L, "Jane", "Smith"));
        Course course = courseService.create(new Course(1L, "Math 101", "Basic Mathematics", teacher));
        Enrollment enrollment= new Enrollment(null ,null,null);
        Enrollment savedEnrollment = new Enrollment(1L,student, course);
        when(enrollmentService.enroll(student.getId(), course.getId()))
                .thenReturn(savedEnrollment);
        assert enrollment.getStudent() == student;
        assert enrollment.getCourse() == course;

    }
}
