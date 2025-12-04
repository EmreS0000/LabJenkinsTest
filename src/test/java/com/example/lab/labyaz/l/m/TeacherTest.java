package com.example.lab.labyaz.l.m;

import com.example.lab.labyaz.l.m.model.Teacher;
import com.example.lab.labyaz.l.m.repository.TeacherRepository;
import com.example.lab.labyaz.l.m.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TeacherTest {

    @Mock
    TeacherRepository teacherRepository;

    @InjectMocks
    TeacherService teacherService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTeacher() {
        Teacher input = new Teacher(null, "Test Teacher", "teacher@example.com");
        Teacher createdTeacher = new Teacher(1L, "Test Teacher", "teacher@example.com");
        when(teacherRepository.save(any(Teacher.class))).thenReturn(createdTeacher);

        Teacher result = teacherService.create(input);

        assertEquals(1L, result.getId());
        assertEquals("Test Teacher", result.getName());
        assertEquals("teacher@example.com", result.getEmail());
    }
}
