package com.example.soapCrud.ws;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.soapCrud.dao.StudentRepository;
import com.example.soapCrud.schema.GetStudentRequest;
import com.example.soapCrud.schema.GetStudentResponse;
import com.example.soapCrud.schema.StudentDetails;
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
		StudentDetails studentDetails = new StudentDetails();
		BeanUtils.copyProperties(studentService.getStudentById(request.getId()), studentDetails);
        response.setStudentInfos(studentDetails);
        return response;
    }
}
