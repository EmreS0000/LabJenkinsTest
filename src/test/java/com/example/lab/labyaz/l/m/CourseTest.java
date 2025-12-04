package com.example.lab.labyaz.l.m;

import com.example.lab.labyaz.l.m.model.Course;
import com.example.lab.labyaz.l.m.model.Teacher;
import com.example.lab.labyaz.l.m.repository.CourseRepository;
import com.example.lab.labyaz.l.m.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class CourseTest {
    @Mock
    CourseRepository courseRepository;
    @InjectMocks
    CourseService courseService;
    @Test
    void createCourse() {
        Teacher teacher = new Teacher(null, "John", "Doe");
        Course course = new Course(null, "Mathematics", "An introductory course to Mathematics", null);
        Course savedcourse = new Course(1L, "Mathematics", "An introductory course to Mathematics", teacher);
        when(courseRepository.save(course)).thenReturn(savedcourse);
        courseService.create(course);
        assert course.getId() == 1L;
        assert course.getTeacher() == teacher;
    }


}
