package com.example.soapCrud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soapCrud.Entity.Student;
import com.example.soapCrud.dao.StudentRepository;
import com.example.soapCrud.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<Student> getAllStudent() {
		List<Student> list = new ArrayList<>();
		studentRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public Student getStudentById(Integer studentId) {
		Student student = studentRepository.findStudentById(studentId);
		return student;
	}

	@Override
	public boolean addStudent(Student student) {
		List<Student> checkStudentExists = studentRepository.findStudentByFnameOrLnameOrAge(student.getFname(),
				student.getLname(), student.getAge());
		if (checkStudentExists.size() > 0) {
			return false;
		} else {
			studentRepository.save(student);
			return true;
		}
	}

	@Override
	public void updateStudent(Student student) {
		studentRepository.save(student);
	}

	@Override
	public void deleteStudent(Student student) {
		studentRepository.delete(student);
	}
}
