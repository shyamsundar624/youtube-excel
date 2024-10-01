package com.shyam.excel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.excel.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{

}
