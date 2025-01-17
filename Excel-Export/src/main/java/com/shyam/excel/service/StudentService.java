package com.shyam.excel.service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shyam.excel.entity.Student;
import com.shyam.excel.repo.StudentRepository;

import jakarta.annotation.PostConstruct;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@PostConstruct
	public void saveStudent() {
		List<Student> list = List.of(new Student("shyam", "CS", "Axis", 3432),
				new Student("sangam", "CS", "Axis", 3432), new Student("Navaya", "CS", "Axis", 3432));

		studentRepository.saveAll(list);
	}

	public ByteArrayInputStream exportStudentData() throws IOException {
		List<Student> all = studentRepository.findAll();

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Student");

		XSSFRow headRow = sheet.createRow(0);

		XSSFCellStyle headerStyle = workbook.createCellStyle();
		XSSFColor color = new XSSFColor(new Color(204, 255, 204), null);
		headerStyle.setFillForegroundColor(color);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		int headerCellIndex = 0;
		String[] headerArr = { "Id", "Name", "Branch", "College", "Fees" };
		for (String header : headerArr) {
			XSSFCell cell = headRow.createCell(headerCellIndex);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
			headerCellIndex++;
		}
		XSSFCellStyle decimalStyle = workbook.createCellStyle();
		XSSFDataFormat format = workbook.createDataFormat();
		decimalStyle.setDataFormat(format.getFormat("0.00"));

		int rowIndex = 1;
		for (Student s : all) {
			XSSFRow row = sheet.createRow(rowIndex);

			row.createCell(0).setCellValue(s.getId());
			row.createCell(1).setCellValue(s.getName());
			row.createCell(2).setCellValue(s.getBranch());
			row.createCell(3).setCellValue(s.getCollege());
			XSSFCell cellFees = row.createCell(4);
			cellFees.setCellValue(s.getFees());
			cellFees.setCellStyle(decimalStyle);
			rowIndex++;
		}
		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());
	}

	public void importStudentInfo(MultipartFile file) throws IOException {
		InputStream inputStream = file.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		for (Sheet sheet : workbook) {
			for (Row row : sheet) {
				if (row.getRowNum() > 0) {
					Student student = new Student();
					for (Cell cell : row) {
						String cellValue = "";
						 // Get the cell type
				        CellType cellType = cell.getCellType();
				        
				        switch (cellType) {
				            case STRING:
				                // If the cell contains a string value
				                System.out.println("Cell contains a String: " + cell.getStringCellValue());
				               cellValue=String.valueOf(cell.getStringCellValue());
				                break;
				            case NUMERIC:
				                // If the cell contains a numeric value
				                System.out.println("Cell contains a Numeric value: " + cell.getNumericCellValue());
				               cellValue=String.valueOf(cell.getNumericCellValue());
				                break;
				            case BOOLEAN:
				                // If the cell contains a boolean value
				                System.out.println("Cell contains a Boolean value: " + cell.getBooleanCellValue());
				                break;
				            case FORMULA:
				                // If the cell contains a formula
				                System.out.println("Cell contains a Formula: " + cell.getCellFormula());
				                break;
				            case BLANK:
				                System.out.println("Cell is blank.");
				                break;
				            default:
				                System.out.println("Cell type not recognized.");
				        }
					
						if (cell.getColumnIndex() == 1) {
							student.setName(cellValue);
						} else if (cell.getColumnIndex() == 2) {
							student.setBranch(cellValue);
						} else if (cell.getColumnIndex() == 3) {
							student.setCollege(cellValue);
						} else if (cell.getColumnIndex() == 4) {
							student.setFees(Double.valueOf(cellValue));
						}
					}
					studentRepository.save(student);
				}
			}
		}
	}

}
