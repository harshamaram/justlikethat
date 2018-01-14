package com.sample.spreadsheet;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

public class Main {
	
	public static String BLANK = "";
	
	public static void main(String s[]) {
		
		try {
			
			HSSFWorkbook hwb = null;
			hwb = new HSSFWorkbook();
			
			HSSFSheet hssfSheet = hwb.createSheet("tab-1");
			
			HSSFRow row;
			HSSFCell cell;
			
			row = hssfSheet.createRow(0);
			
			CellStyle numberFormatCell;
			numberFormatCell = hwb.createCellStyle();
			numberFormatCell.setDataFormat(hwb.createDataFormat().getFormat("_(#,##0.00_);_((#,##0.00);_(0.00_);_(@_)"));
			
			cell = row.createCell(0);
			cell.setCellStyle(numberFormatCell);
			cell.setCellValue(getValueFromFormattedNumber("45.32"));
			
			cell = row.createCell(1);
			cell.setCellStyle(numberFormatCell);
			cell.setCellValue(getValueFromFormattedNumber("0.32"));
			
			cell = row.createCell(2);
			cell.setCellStyle(numberFormatCell);
			cell.setCellValue(getValueFromFormattedNumber("-.32"));
			
			cell = row.createCell(3);
			cell.setCellStyle(numberFormatCell);
			cell.setCellValue(getValueFromFormattedNumber("-12454578.32"));
			
			cell = row.createCell(4);
			cell.setCellStyle(numberFormatCell);
			cell.setCellValue(getValueFromFormattedNumber(""));
			
			row = hssfSheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue(0);
			
			cell = row.createCell(1);
			cell.setCellStyle(numberFormatCell);
			cell.setCellValue(12547.3264);
			
			cell = row.createCell(2);
			cell.setCellValue(123456);
			
			FileOutputStream out = new FileOutputStream("sample.xls");
			hwb.write(out);
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public static String LEFT_PARANTHESES = "(";
	public static String RIGHT_PARANTHESES = ")";
	public static String COMMA = ",";
	
	public static Double getValueFromFormattedNumber(String formattedNumber) {

		if(BLANK.equalsIgnoreCase(formattedNumber)
				|| formattedNumber == null) {
			return null;
		}
		
		boolean isNegativeNumber = false;
		
		if(formattedNumber.contains(LEFT_PARANTHESES)) {
			formattedNumber = formattedNumber.replace(LEFT_PARANTHESES, BLANK);
			formattedNumber = formattedNumber.replace(RIGHT_PARANTHESES, BLANK);
			isNegativeNumber = true;
		}
		
		
		
		formattedNumber = formattedNumber.replaceAll(COMMA, BLANK);
		if(isNegativeNumber)
			return Double.parseDouble(formattedNumber)*(-1);
		else 
			return Double.parseDouble(formattedNumber);
		
	}
	
}
