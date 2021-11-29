package com.example.soapCrud.ws;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.soapCrud.Entity.Student;
import com.example.soapCrud.dao.StudentRepository;
import com.example.soapCrud.schema.AddStudentRequest;
import com.example.soapCrud.schema.AddStudentResponse;
import com.example.soapCrud.schema.GetAllStudentsResponse;
import com.example.soapCrud.schema.GetStudentRequest;
import com.example.soapCrud.schema.GetStudentResponse;
import com.example.soapCrud.schema.ServiceStatus;
import com.example.soapCrud.schema.StudentInfo;
import com.example.soapCrud.schema.UpdateStudentRequest;
import com.example.soapCrud.schema.UpdateStudentResponse;
import com.example.soapCrud.service.impl.StudentServiceImpl;

@Endpoint
public class StudentEndpoint {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@PayloadRoot(namespace = "http://www.example.org/Student", localPart = "GetStudentRequest")
	@ResponsePayload
	public GetStudentResponse getStudent(@RequestPayload GetStudentRequest request) {
		GetStudentResponse response = new GetStudentResponse();
		StudentInfo studentInfo = new StudentInfo();
		BeanUtils.copyProperties(studentService.getStudentById(request.getId()), studentInfo);
        response.setStudentInfos(studentInfo);
        return response;
    }
	
	@PayloadRoot(namespace = "http://www.example.org/Student", localPart = "GetAllStudentsRequest")
	@ResponsePayload
	public GetAllStudentsResponse getAllStudents() {
		GetAllStudentsResponse allStudentsResponse = new GetAllStudentsResponse();
		
		List<Student> students = studentService.getAllStudent();
		for(Student student: students) {
			GetStudentResponse studentResponse = mapStudentInfo(student);
			allStudentsResponse.getStudentInfo().add(studentResponse.getStudentInfos());
		}
		return allStudentsResponse;
	}
	
	private GetStudentResponse mapStudentInfo(Student student) {
		StudentInfo studentInfo = mapStudent(student);
		
		GetStudentResponse studentResponse = new GetStudentResponse();
		
		studentResponse.setStudentInfos(studentInfo);
		return studentResponse;
	}
	
	private StudentInfo mapStudent(Student student) {
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setStudentId(student.getId());
		studentInfo.setFname(student.getLname());
		studentInfo.setLname(student.getFname());
		studentInfo.setAge(student.getAge());
		return studentInfo;
	}
	
	@PayloadRoot(namespace = "http://www.example.org/Student", localPart = "addStudentRequest")
	@ResponsePayload
	public AddStudentResponse addStudent (@RequestPayload AddStudentRequest request) {
		AddStudentResponse response = new AddStudentResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		Student student = new Student();
		student.setFname(request.getFname());
		student.setLname(request.getLname());
		student.setAge(request.getAge());
		boolean flag = studentService.addStudent(student);
		if (flag == false) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Content Already Available");
			response.setServiceStatus(serviceStatus);
		} else {
			StudentInfo studentInfo = new StudentInfo();
			BeanUtils.copyProperties(student, studentInfo);
			response.setStudentInfo(studentInfo);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
			response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	
	@PayloadRoot(namespace = "http://www.example.org/Student", localPart = "updateStudentRequest")
	@ResponsePayload
	public UpdateStudentResponse updateStudent(@RequestPayload UpdateStudentRequest request) {
		Student student = new Student();
		BeanUtils.copyProperties(request.getStudentInfo(), student);
		studentService.updateStudent(student);
		ServiceStatus serviceStatus = new ServiceStatus();
		serviceStatus.setMessage("SUCCESS");
		serviceStatus.setMessage("Content Update Successfully");
		UpdateStudentResponse response = new UpdateStudentResponse();
		response.setServiceStatus(serviceStatus);
		return response;
		}
}
