package com.example.lab.labyaz.l.m;

import com.example.lab.labyaz.l.m.repository.EnrollmentRepository;
import com.example.lab.labyaz.l.m.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


public class EnrolmentsTest {
    @Mock
    EnrollmentRepository enrollmentRepository;
    @InjectMocks
    EnrollmentService enrollmentService;
    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void enrollStudentTest() {
        Long studentId = 1L;
        Long courseId = 1L;
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
        assert !exists;
    }

}
