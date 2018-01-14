package com.justlikethat.mp3;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ListDirectories {
	
	public static StringBuffer sb = new StringBuffer();
	public static String fileName;
	public static String albumName;
	public static String path;

	public static void main(String s[]) {
		File folder = new File("C:\\Users\\Hello\\Music");
		printFileList(folder);
	}
	
	public static void main1(String s[]) {
		try {
			
			WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"));
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			
			Label label = new Label(0, 1, "A label record"); 
			sheet.addCell(label); 
			
			Number number = new Number(0, 2, 3.1459); 
			sheet.addCell(number);
			
			workbook.write(); 
			workbook.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printFileList(File folder) {
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (!listOfFiles[i].isFile()) {
					System.out.println(getLastSection(listOfFiles[i].getAbsolutePath()));
					printFileList(listOfFiles[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getLastSection(String str) {
		int index = str.lastIndexOf("\\") + 1;
		if(index > 0) {
			return str.substring(index);
		}
		return "";
	}
}


