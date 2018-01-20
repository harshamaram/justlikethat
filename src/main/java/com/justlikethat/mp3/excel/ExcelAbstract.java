package com.justlikethat.mp3.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

abstract class ExcelAbstract<T> {
	
	public static Workbook workBook;
	public static Sheet sheet;
	
	private String sheetName;
	private String fileName;
	
	private void setDefaults() {
		sheetName = "wb-sheet";
		fileName = getFileName();
	}
	
	private void init() {
		
		setDefaults();
		try {
	        workBook = new HSSFWorkbook();
	        sheet = workBook.createSheet(sheetName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getFileName() {
		return "workbok_" + getTimestamp() + ".xls";
	}
	
	public abstract void createHeader();
	
	private void writeAndClose() throws IOException {
		String file = fileName;
        FileOutputStream out = new FileOutputStream(file);
        workBook.write(out);
        out.close();
	}
	
	public abstract void processData(String fileName, List<T> dataList);
	
	public void process(String fileName, List<T> dataList) throws IOException {
		init();
		processData(fileName, dataList);
		writeAndClose();
		
	}
	
	public String getTimestamp() {
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String ts = dt1.format(Calendar.getInstance().getTime());
		return ts;
	}

	


}
