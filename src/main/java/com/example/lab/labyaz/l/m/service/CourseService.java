package com.example.lab.labyaz.l.m.service;

import com.example.lab.labyaz.l.m.model.Course;
import com.example.lab.labyaz.l.m.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course create(Course c) { return courseRepository.save(c); }
    public Course update(Long id, Course c) { c.setId(id); return courseRepository.save(c); }
    public void delete(Long id) { courseRepository.deleteById(id); }
    public Optional<Course> get(Long id) { return courseRepository.findById(id); }
    public List<Course> list() { return courseRepository.findAll(); }
}

