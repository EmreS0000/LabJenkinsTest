package com.example.lab.labyaz.l.m.service;

import com.example.lab.labyaz.l.m.model.Teacher;
import com.example.lab.labyaz.l.m.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher create(Teacher t) { return teacherRepository.save(t); }
    public Teacher update(Long id, Teacher t) { t.setId(id); return teacherRepository.save(t); }
    public void delete(Long id) { teacherRepository.deleteById(id); }
    public Optional<Teacher> get(Long id) { return teacherRepository.findById(id); }
    public List<Teacher> list() { return teacherRepository.findAll(); }
}

