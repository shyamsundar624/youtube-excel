package com.shyam.excel.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.excel.entity.Farmer;
import com.shyam.excel.service.FarmerService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class FarmerRestController {

	@Autowired
	private FarmerService farmerService;
	
	@PostMapping("/save")
	public ResponseEntity<Farmer> saveFarmer(@RequestBody Farmer farmer){
		
		return new ResponseEntity<>(farmerService.SaveFarmer(farmer),HttpStatus.CREATED);
		
	}
	@GetMapping("/export")
	public ResponseEntity<byte[]> exportFarmerData() throws IOException {
		ByteArrayInputStream byteArrayInputStream = farmerService.exportFarmerData();
		
		
String heeaderKey="Content-Disposition";		
String heeaderValue="attachment; filename=farmer.xlsx";

HttpHeaders headers=new HttpHeaders();
headers.add(heeaderKey, heeaderValue);

return ResponseEntity.ok().headers(headers)
.contentType(MediaType.APPLICATION_OCTET_STREAM)
.body(byteArrayInputStream.readAllBytes());
	
	}
}
