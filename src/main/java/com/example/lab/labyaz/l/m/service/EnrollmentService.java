package com.example.lab.labyaz.l.m.service;

import com.example.lab.labyaz.l.m.model.Course;
import com.example.lab.labyaz.l.m.model.Enrollment;
import com.example.lab.labyaz.l.m.model.Student;
import com.example.lab.labyaz.l.m.repository.CourseRepository;
import com.example.lab.labyaz.l.m.repository.EnrollmentRepository;
import com.example.lab.labyaz.l.m.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Enrollment enroll(Long studentId, Long courseId) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Already enrolled");
        }
        Student s = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course c = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Enrollment e = new Enrollment();
        e.setStudent(s);
        e.setCourse(c);
        return enrollmentRepository.save(e);
    }

    public void delete(Long id) { enrollmentRepository.deleteById(id); }
}

