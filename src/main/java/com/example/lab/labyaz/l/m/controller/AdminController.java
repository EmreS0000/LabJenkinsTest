package com.example.lab.labyaz.l.m.controller;

import com.example.lab.labyaz.l.m.model.Course;
import com.example.lab.labyaz.l.m.model.Student;
import com.example.lab.labyaz.l.m.model.Teacher;
import com.example.lab.labyaz.l.m.model.User;
import com.example.lab.labyaz.l.m.service.StudentService;
import com.example.lab.labyaz.l.m.service.TeacherService;
import com.example.lab.labyaz.l.m.service.CourseService;
import com.example.lab.labyaz.l.m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User u) {
        return ResponseEntity.ok(userService.create(u));
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student s) {
        return ResponseEntity.ok(studentService.create(s));
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> listStudents() {
        return ResponseEntity.ok(studentService.list());
    }

    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher t) {
        return ResponseEntity.ok(teacherService.create(t));
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> listTeachers() {
        return ResponseEntity.ok(teacherService.list());
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course c) {
        return ResponseEntity.ok(courseService.create(c));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> listCourses() {
        return ResponseEntity.ok(courseService.list());
    }
}
