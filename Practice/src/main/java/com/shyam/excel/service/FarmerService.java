package com.shyam.excel.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.excel.entity.Farmer;
import com.shyam.excel.repo.FarmerRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class FarmerService {

	@Autowired
	private FarmerRepository farmerRepo;

	public Farmer SaveFarmer(Farmer farmer) {
		return farmerRepo.save(farmer);
	}

	public ByteArrayInputStream exportFarmerData( ) throws IOException {
		List<Farmer> farmers = farmerRepo.findAll();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Farmer");
//create a cellStyle for Header Row
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		//headerCellStyle.setFillBackgroundColor(Indexd);
		
		//set with for third cell
		sheet.setColumnWidth(2, 25*256);
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Name");
		row.createCell(2).setCellValue("Income");
		row.createCell(3).setCellValue("Village");

		//create a DataFormat and celltype for double
		XSSFDataFormat dataFormat = workbook.createDataFormat();
		XSSFCellStyle doubleCellStype = workbook.createCellStyle();
		doubleCellStype.setDataFormat(dataFormat.getFormat("0.00"));
		
		int rowIndex = 1;
		for (Farmer farmer : farmers) {
			XSSFRow dataRow = sheet.createRow(rowIndex);
			dataRow.createCell(0).setCellValue(farmer.getId());
			dataRow.createCell(1).setCellValue(farmer.getName());
			XSSFCell cell = dataRow.createCell(2);
			cell.setCellValue(farmer.getIncome());
			cell.setCellStyle(doubleCellStype);
			dataRow.createCell(3).setCellValue(farmer.getVillage());
			rowIndex++;
		}
		//ServletOutputStream ops = response.getOutputStream();
		
		workbook.write(out);
		//workbook.close();
		//ops.close();
		return new ByteArrayInputStream(out.toByteArray());
	}
}
