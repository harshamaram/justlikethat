package com.justlikethat.mp3.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.justlikethat.mp3.bean.TrackMetaData;

public class ExcelMP3InfoImpl extends ExcelAbstract<TrackMetaData> {

	enum METADATA {
		ALBUM ("ALBUM"),
		TITLE ("TITLE"),
		TRACK ("TRACK"),
		ARTIST ("ARTIST"),
		GENRE ("GENRE"),
		YEAR ("YEAR"),
		ORIGINAL_FILE_NAME("ORIGINAL FILE NAME"),
		ORIGINAL_FOLDER_NAME("ORIGINAL FOLDER NAME");
		
		String name;
		METADATA(String name) {
			this.name = name;
		}
	}
	
	@Override
	public void createHeader() {
		Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell cell;
        int count = 0;
        for(METADATA metadata : METADATA.values()) {
        	
        	cell = titleRow.createCell(count);
        	cell.setCellValue(metadata.name());
        	count++;
        }
        
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2,  5 * 256);
        sheet.setColumnWidth(3, 30 * 256);
        sheet.setColumnWidth(4, 40 * 256);
        sheet.setColumnWidth(5, 50 * 256);
		
	}

	public String getFileName() {
		return "current_track_data_" + getTimestamp() + ".xls";
	}

	@Override
	public void processData(String fileName, List<TrackMetaData> dataList) {
		try {
			
			Cell cell;
			int count = 1;
			for(TrackMetaData data : dataList) {
				System.out.println(data);
				Row row = sheet.createRow(count);
				cell = row.createCell(0);
				cell.setCellValue(data.getAlbum());
				cell = row.createCell(1);
				cell.setCellValue(data.getTitle());
				cell = row.createCell(2);
				cell.setCellValue(data.getTrack());
				cell = row.createCell(3);
				cell.setCellValue(data.getArtist());
				cell = row.createCell(4);
				cell.setCellValue(data.getGenre());
				cell = row.createCell(5);
				cell.setCellValue(data.getYear());
				cell = row.createCell(6);
				cell.setCellValue(data.getOriginalFileName());
				cell = row.createCell(7);
				cell.setCellValue(data.getOriginalFolderName());
				count++;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
