package com.example.soapCrud.ws;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//Enable Spring Web Services
@EnableWs
//Spring Configuration
@Configuration
public class WebServiceConfig {

	//MessageDispatcherServlet
	//ApplicationContext
	// url -> /ws/*
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
	}
	
	@Bean(name = "student")
	public DefaultWsdl11Definition defaultWsdl1Definition(XsdSchema studentSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("StudentPort");
		definition.setTargetNamespace("http://www.example.org/Student");
		definition.setLocationUri("/ws");
		definition.setSchema(studentSchema);
		return definition;
	}
	
	@Bean
	public XsdSchema studentSchema() {
		return new SimpleXsdSchema (new ClassPathResource("xsd/Student.xsd"));
	}
	
}
