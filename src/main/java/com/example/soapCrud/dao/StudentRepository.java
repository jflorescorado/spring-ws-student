package com.example.soapCrud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soapCrud.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	
	Student findStudentById(Integer studentId);
	List<Student> findStudentByFnameOrLnameOrAge(String fname, String lname, Integer age);
}
