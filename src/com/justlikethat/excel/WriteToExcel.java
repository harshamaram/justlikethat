package com.justlikethat.excel;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

public class WriteToExcel {
	
	public WritableWorkbook workbook;
	
	private WriteToExcel(String wBookName) throws IOException {
		this.workbook =  Workbook.createWorkbook(new File(wBookName));
	}
	
	public void createWorkBook() {
		
	}

}
