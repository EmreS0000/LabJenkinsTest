package com.example.lab.labyaz.l.m.service;

import com.example.lab.labyaz.l.m.model.Student;
import com.example.lab.labyaz.l.m.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student create(Student s) { return studentRepository.save(s); }
    public Student update(Long id, Student s) { s.setId(id); return studentRepository.save(s); }
    public void delete(Long id) { studentRepository.deleteById(id); }
    public Optional<Student> get(Long id) { return studentRepository.findById(id); }
    public List<Student> list() { return studentRepository.findAll(); }
}

