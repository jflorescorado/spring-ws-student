package com.example.soapCrud.service;

import java.util.List;

import com.example.soapCrud.Entity.Student;

public interface StudentService {

	List<Student> getAllStudent();
	Student getStudentById(Integer studentId);
	boolean addStudent(Student student);
	void updateStudent(Student student);
	void deleteStudent(Student stundent);
	
}
