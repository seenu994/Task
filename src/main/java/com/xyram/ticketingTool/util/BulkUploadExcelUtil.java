package com.xyram.ticketingTool.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Table.Cell;
import com.xyram.ticketingTool.helper.EmployeePojo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;

public class BulkUploadExcelUtil {


public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
public static List<String> EmployeeHeader = Arrays.asList("eId", "password", "email","firstName","lastName","middleName","roleId","status","designationId","reportingTo","location","position","wing_id","profileUrl","mobileNumber");
//public static List<String> ApartmentWingHeader = Arrays.asList("Wings", "Apartment Name");

public static boolean hasEmployeeFormat(MultipartFile file) {
	if (!TYPE.equals(file.getContentType())) {
		return false;
	}
	return true;
}

public static boolean hasEmployeeHeader(MultipartFile file) {
	if (!TYPE.equals(file.getContentType())) {
		return false;
	} else {
		List<String> columnHeaderNames = getHeaderColumns(file, 0, 0);
		if (EmployeeHeader.size() == columnHeaderNames.size() && EmployeeHeader.containsAll(columnHeaderNames)) {
			return true;
		} else {
			return false;
		}
	}
}

/*
 * public static boolean hasWingHeader(MultipartFile file) { if
 * (!TYPE.equals(file.getContentType())) { return false; } else { List<String>
 * columnHeaderNames = getHeaderColumns(file, 0, 0); if
 * (ApartmentWingHeader.size() == columnHeaderNames.size() &&
 * ApartmentWingHeader.containsAll(columnHeaderNames)) { return true; } else {
 * return false; } } }
 */

public static List<String> getHeaderColumns(MultipartFile file, int sheetIndex, int rowNum) {

	ArrayList<String> headerData = new ArrayList<>();
	try {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

		XSSFSheet firstSheet = workbook.getSheetAt(sheetIndex);

		XSSFRow row = firstSheet.getRow(rowNum);

		for (org.apache.poi.ss.usermodel.Cell cell : row) {
			switch (cell.getCellType()) {
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell, null)) {
					DataFormatter dataFormatter = new DataFormatter();

					headerData.add(dataFormatter.formatCellValue(cell));
				} else {
					headerData.add(String.valueOf(cell.getNumericCellValue()));
				}
				break;
			case STRING:
				headerData.add(cell.getStringCellValue());
				break;
			case BOOLEAN:
				headerData.add(String.valueOf(cell.getBooleanCellValue()));
				break;
			default:
				headerData.add("");
				break;
			}
		}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return headerData;
}

public static List<EmployeePojo> excelToEmployeePojo(InputStream is) {
	DateTimeFormatter ISO_DATE_TIME = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().preferNullOverDefault(true)
			.caseInsensitive(true).datePattern("yyyy-MM-dd").build();
	List<EmployeePojo> patientList = Poiji.fromExcel(is, PoijiExcelType.XLSX, EmployeePojo.class, options);

	return patientList;

}



public static Date convertStringToDate(String dateInString, String format) {
	SimpleDateFormat formatter = new SimpleDateFormat(format);
	Date date = new Date();
	try {
		date = formatter.parse(dateInString);
	} catch (ParseException e) {
		System.out.println("Invalid Date format: " + dateInString);
	}
	return date;
}

}
