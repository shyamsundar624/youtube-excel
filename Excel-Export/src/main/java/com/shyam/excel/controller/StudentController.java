package com.shyam.excel.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shyam.excel.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	@GetMapping("/export")
	public ResponseEntity<byte[]> exportStudentData() throws IOException{
		ByteArrayInputStream inputStream = studentService.exportStudentData();
		 
		String headerKey="Content-Disposition";
		String headerValue="attachment; filename=student.xlsx";
		
		HttpHeaders headers=new HttpHeaders();
		headers.add(headerKey, headerValue);
		
		return ResponseEntity.ok().
				headers(headers).
				contentType(MediaType.APPLICATION_OCTET_STREAM).
				body(inputStream.readAllBytes());
	}
	
	@PostMapping("/import")
	public ResponseEntity<String> importStudentInf(@RequestParam("file") MultipartFile file){
		try {
			studentService.importStudentInfo(file);
			return ResponseEntity.status(HttpStatus.CREATED).body("Student data Import Successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Issue");
		}
	}
}
