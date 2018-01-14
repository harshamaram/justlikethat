package com.justlikethat.mp3.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.justlikethat.mp3.Constants;
import com.justlikethat.mp3.LoadData;
import com.justlikethat.mp3.bean.TrackMetaData;

public class Work implements Constants{
	public static StringBuffer sb = new StringBuffer();
	public static String fileName;
	public static String albumName;
	public static String artistName;
	public static String trackName;
	public static String path;
	
	public static Workbook workBook;
	public static Sheet sheet;
	
	private static List<TrackMetaData> existingTrackList = new ArrayList<TrackMetaData>();
	
	public static void main(String s[]) {
		File folder = new File("D:\\documents\\music");
		initializeWorkBook();
		recursiveExtract(folder);
		writeDataToSheet();
		System.out.println("-- end --");
	}
	
	public static void main3(String s[]) {
		
		LoadData data = new LoadData();
		data.readData();
		for (Map.Entry<String, String> entry : LoadData.data.entrySet()) {
			System.out.println(entry.getKey() + " / " + entry.getValue());
		}
	}
	
	static void initializeWorkBook() {
		try {
	        workBook = new HSSFWorkbook();
	        sheet = workBook.createSheet("MetadataInfo");

	        sheet.setColumnWidth(0, 30*256);
	        sheet.setColumnWidth(1, 50*256);
	        sheet.setColumnWidth(2, 5*256);
	        sheet.setColumnWidth(3, 30*256);
	        sheet.setColumnWidth(4, 40*256);
	        sheet.setColumnWidth(5, 50*256);
	        
	        Row titleRow = sheet.createRow(0);
	        titleRow.setHeightInPoints(45);
	        Cell cell;
	        int count = 0;
	        for(Constants.METADATA metadata : Constants.METADATA.values()) {
	        	
	        	cell = titleRow.createCell(count);
	        	cell.setCellValue(metadata.name());
	        	count++;
	        }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void writeDataToSheet() {
		try {
			
			Cell cell;
			int count = 1;
			for(TrackMetaData data : existingTrackList) {
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
			
			String file = "current_track_data_" + getTimestamp() + ".xls";
	        FileOutputStream out = new FileOutputStream(file);
	        workBook.write(out);
	        out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void recursiveExtract(File folder) {
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			
			if (listOfFiles[i].isDirectory()) {
				recursiveExtract(listOfFiles[i]);
			} else {
				extractTrackMetadata(listOfFiles[i]);
			}
		}		
	}
	
	public static void extractTrackMetadata(File trackFile) {
		
		try {
			System.out.println("writing: " + trackFile.getAbsolutePath());
			
			String fileName = trackFile.getName();
			String folderName = getFolderNameAsAlbumName(trackFile);
			
			if(isMp3File(fileName)) {
				AudioFile audioFile = AudioFileIO.read(trackFile);
				Tag tag = audioFile.getTag();
				
				TrackMetaData trackData = new TrackMetaData();
				if(tag != null) {
					
					trackData.setAlbum(tag.getFirst(FieldKey.ALBUM));
					trackData.setTitle(tag.getFirst(FieldKey.TITLE));
					trackData.setTrack(tag.getFirst(FieldKey.TRACK));
					trackData.setArtist(tag.getFirst(FieldKey.ARTIST));
					trackData.setGenre(tag.getFirst(FieldKey.GENRE));
					trackData.setYear(tag.getFirst(FieldKey.YEAR));	
					trackData.setOriginalFileName(fileName);
					trackData.setOriginalFolderName(folderName);
					
				}
				
				existingTrackList.add(trackData);
				System.out.println(trackData);
			}
			
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isMp3File(String fileName) {
		if(fileName.endsWith(mp3) || fileName.endsWith(MP3)) {
			return true;
		}
		return false;
	}
	
	
	
	public static void recurrentTagUpdateCommit(File folder) {
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					
					albumName = getFolderNameAsAlbumName(listOfFiles[i]);
					trackName = getFileNameAsTrackTitle(listOfFiles[i]);
					// System.out.println("-F- "
					// +listOfFiles[i].getAbsolutePath().substring(listOfFiles[i].getAbsolutePath().lastIndexOf("\\")+1));
					System.out.println("-F- " + albumName + " # " + fileName);
					
					if(fileName.endsWith("mp3") || fileName.endsWith("MP3")) {
						
						if(LoadData.data.containsKey(albumName) 
								&& (!"".equalsIgnoreCase(LoadData.data.get(albumName)))) {
							artistName = LoadData.data.get(albumName);
						} else {
							artistName = albumName;
						}
						
						fileName = fileName.substring(0, fileName.lastIndexOf("."));
						AudioFile f = AudioFileIO.read(listOfFiles[i]);
						Tag tag = f.getTag();
						tag.setField(FieldKey.TITLE, fileName);
						tag.setField(FieldKey.ALBUM, albumName);
						tag.setField(FieldKey.ARTIST, artistName);
						f.commit();
					}
					
				} else if (listOfFiles[i].isDirectory()) {
					recurrentTagUpdateCommit(listOfFiles[i]);
					// System.out.println("-D- "
					// +listOfFiles[i].getAbsolutePath());
				}
			} catch (InvalidAudioFrameException iafe) {
				iafe.printStackTrace();
			} catch (CannotReadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TagException e) {
				e.printStackTrace();
			} catch (ReadOnlyFileException e) {
				e.printStackTrace();
			} catch (CannotWriteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private static String getFolderNameAsAlbumName(File file) {

		String absPath = file.getAbsolutePath();
		absPath.substring(0, absPath.lastIndexOf("\\"));
		return absPath.substring(absPath.lastIndexOf("\\"));
		
	}
	
	private static String getFileNameAsTrackTitle(File file) {
		return file.getName();
	}

	public static String getTimestamp() {
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String ts = dt1.format(Calendar.getInstance().getTime());
		return ts;
	}
	
}
