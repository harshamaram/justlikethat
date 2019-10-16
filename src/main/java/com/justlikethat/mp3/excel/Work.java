package com.justlikethat.mp3.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		File folder = new File("C:\\data\\music\\Telugu\\Swathi Muthyam");
		
		try {
			recursiveProcess(folder);
			writeDataToSheet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-- end --");
	}
	
	public static void main3(String s[]) {
		
		LoadData data = new LoadData();
		data.readData();
		for (Map.Entry<String, String> entry : LoadData.data.entrySet()) {
			System.out.println(entry.getKey() + " / " + entry.getValue());
		}
	}
	
	static void writeDataToSheet() throws IOException {
		
		ExcelAbstract<TrackMetaData> absExcel = new ExcelMP3InfoImpl();
		absExcel.process("current_tracks", existingTrackList);
		
	}
	
	public static void recursiveProcess(File folder) {
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			
			if (listOfFiles[i].isDirectory()) {
				recursiveProcess(listOfFiles[i]);
			} else {
				if(isMp3File(listOfFiles[i])) {
					extractTrackMetadata(listOfFiles[i]);
				}
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
	
	public static void tagUpdater(File mp3File) {
		try {
			AudioFile f = AudioFileIO.read(mp3File);
			Tag tag = f.getTag();
			tag.setField(FieldKey.TITLE, fileName);
			tag.setField(FieldKey.ALBUM, albumName);
			tag.setField(FieldKey.ARTIST, artistName);
			f.commit();
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
		} catch (CannotWriteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			} catch (CannotWriteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static boolean isMp3File(File file) {
		return isMp3File(file.getName());
	}
	
	public static boolean isMp3File(String fileName) {
		if(fileName.endsWith(mp3) || fileName.endsWith(MP3)) {
			return true;
		}
		return false;
	}

	private static String getFolderNameAsAlbumName(File file) {

		String absPath = file.getAbsolutePath();
		absPath = absPath.substring(0, absPath.lastIndexOf(File.separator));
		return absPath.substring(absPath.lastIndexOf(File.separator) + 1);
		
	}
	
	private static String getFileNameAsTrackTitle(File file) {
		return file.getName();
	}

	
	
}
