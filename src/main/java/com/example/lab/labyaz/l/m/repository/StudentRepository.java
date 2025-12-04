package com.example.lab.labyaz.l.m.repository;

import com.example.lab.labyaz.l.m.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

