package com.justlikethat.mp3.excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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

public class Work implements Constants{
	public static StringBuffer sb = new StringBuffer();
	public static String fileName;
	public static String albumName;
	public static String artistName;
	public static String path;
	
	public static Sheet sheet;
	
	public static int count = 0;
	
	public static void main3(String s[]) {
		File folder = new File("C:\\Users\\Hello\\Music");
		recurrentProcess(folder);
		System.out.println("-- end --");
	}
	
	public static void main(String s[]) {
		
		File folder = new File("C:\\Users\\Hello\\Music\\Hindi");
		printFileList(folder);
	}
	
	public static void main1(String s[]) {
		try {
			
			readExcel();
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readExcel() throws BiffException, IOException {
		Sheet sheet = getWorkSheet();
		System.out.println("--");
		Cell a1 = sheet.getCell(1,0);
		Cell b2 = sheet.getCell(1,1);
		
		String stringa1 = a1.getContents();
		String stringb2 = b2.getContents();
		
		System.out.println(stringa1);
		System.out.println(stringb2);
		System.out.println("--");
		
	}
	
	public static Sheet getWorkSheet() throws IOException, BiffException {
		if(sheet == null) {
			Workbook workbook = Workbook.getWorkbook(new File("data.xls"));
			sheet = workbook.getSheet(1); 
		} 
		return sheet; 
	}
	
	public static void main2(String s[]) {
		try {
			
			WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"));
			WritableSheet sheet = workbook.createSheet("metadata", 0);
			
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
	
	public static void recurrentProcess(File folder) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"));
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			int count = 0;
			
			Label nameLabel = new Label(FILE_NAME, count, "NAME"); 
			Label folderLabel = new Label(FOLDER_NAME, count, "FOLDER"); 
			Label albumLabel = new Label(ALBUM, count, "ALBUM"); 
			Label titleLabel = new Label(TITLE, count, "TITLE");
			Label trackLabel = new Label(TRACK, count, "TRACK");
			Label artistLabel = new Label(ARTIST, count, "ARTIST");
			Label genreLabel = new Label(GENRE, count, "GENRE");
			Label yearLabel = new Label(YEAR, count, "YEAR");
			
			sheet.addCell(nameLabel);
			sheet.addCell(folderLabel);
			sheet.addCell(albumLabel);
			sheet.addCell(titleLabel); 
			sheet.addCell(trackLabel); 
			sheet.addCell(artistLabel); 
			sheet.addCell(genreLabel); 
			sheet.addCell(yearLabel); 
			
			recurrentWrite(sheet, folder);
			workbook.write(); 
			workbook.close();
		} catch(Exception e) {}		
	}
	
	public static void recurrentWrite(WritableSheet sheet, File folder) {
		String albumName = folder.getName();
		File[] listOfFiles = folder.listFiles();
		String trackName;
		
		for (int i = 0; i < listOfFiles.length; i++) {
			
			if (listOfFiles[i].isDirectory()) {
//				albumCount++;
//				writeAlbumMetadata(albumName, sheet, albumCount, fileCount);
				recurrentWrite(sheet, listOfFiles[i]);
			} else {
				trackName = getFileName(listOfFiles[i]);
				if(trackName != null && isMp3File(trackName)) {
					count++;
					writeTrackMetadata(listOfFiles[i], albumName, trackName, sheet, count);
				}
			}
		}		
	}
	
	public static void writeAlbumMetadata(String albumName, WritableSheet sheet, int count) {
		
	}
	
	public static void writeTrackMetadata(File trackFile, String albumName, String trackName, WritableSheet sheet, int count) {
		String title="", album="", genre="", artist="", year="", track="", name="";
//		System.out.println();
		
		try {
			System.out.println("writing: "+trackFile.getAbsolutePath()+"\tcount: "+count);
			AudioFile audioFile = AudioFileIO.read(trackFile);
			Tag tag = audioFile.getTag();
			name = trackFile.getName();
//			System.out.println("name: "+name);
			if(tag != null) {
//				System.out.println(trackFile.getAbsolutePath()+"\t count: "+tag.getFieldCount());
				
				album = tag.getFirst(FieldKey.ALBUM);
				title = tag.getFirst(FieldKey.TITLE);
				track = tag.getFirst(FieldKey.TRACK);
				artist = tag.getFirst(FieldKey.ARTIST);
				genre = tag.getFirst(FieldKey.GENRE);
				year = tag.getFirst(FieldKey.YEAR);	
				
			}
			
			Label nameLabel = new Label(FILE_NAME, count, name); 
			Label folderLabel = new Label(FOLDER_NAME, count, albumName); 
			Label albumLabel = new Label(ALBUM, count, album); 
			Label titleLabel = new Label(TITLE, count, title);
			Label trackLabel = new Label(TRACK, count, track);
			Label artistLabel = new Label(ARTIST, count, artist);
			Label genreLabel = new Label(GENRE, count, genre);
			Label yearLabel = new Label(YEAR, count, year);
			
			sheet.addCell(nameLabel);
			sheet.addCell(folderLabel);
			sheet.addCell(albumLabel);
			sheet.addCell(titleLabel); 
			sheet.addCell(trackLabel); 
			sheet.addCell(artistLabel); 
			sheet.addCell(genreLabel); 
			sheet.addCell(yearLabel); 
			
		} catch (InvalidAudioFrameException iafe) {
			iafe.printStackTrace();
		} catch (CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
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
	
	public static String getFileName(File file) {
		String fileName;		
		
		if(file.isDirectory())
			return null;
		else {
			fileName = file.getName();
//			fileName = filePath.substring(filePath.lastIndexOf(SLASH) + 1);
			return fileName;
		}
			
	}
	
	public static void printFileList(File folder) {
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					sb.append(listOfFiles[i].getAbsolutePath());
					path = sb.toString();
					fileName = sb.substring(sb.lastIndexOf("\\") + 1);
					sb.setLength(sb.lastIndexOf("\\"));
					albumName = sb.substring(sb.lastIndexOf("\\") + 1);
					// System.out.println("-F- "
					// +listOfFiles[i].getAbsolutePath().substring(listOfFiles[i].getAbsolutePath().lastIndexOf("\\")+1));
					System.out.println("-F- " + albumName + " # " + fileName);
					
					if(fileName.endsWith("mp3") || fileName.endsWith("MP3")) {
						
						if(LoadData.data.containsKey(albumName) && (!"".equalsIgnoreCase(LoadData.data.get(albumName)))) {
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
					printFileList(listOfFiles[i]);
					// System.out.println("-D- "
					// +listOfFiles[i].getAbsolutePath());
				}
			} catch (InvalidAudioFrameException iafe) {
				iafe.printStackTrace();
			} catch (CannotReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReadOnlyFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CannotWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
		
	
}
