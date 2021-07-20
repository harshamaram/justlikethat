package com.justlikethat.folderexport;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

class Constants {
	final static String FILE_START_TAG = "#########FILE#START#########";
	final static String FOLD_START_TAG = "#########FOLDER#############";
	final static String END_TAG = "#########END#########";

	final static List<String> SKIP_FILE_TYPES = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("png");
			add("jpeg");
			add("jpg");
			add("pdf");
			add("class");
			add("jar");
			add("zip");
			add("mp3");
			add("xlsx");
			add("xls");
			add("log");
			
		}
	};
	
	static List<String> SKIP_FOLDER_NAMES = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("\\.git");
			add("\\.settings");
			add("\\.mvn");
			add("\\.project");
			add("\\target");
			add("\\src\\test\\resources");
			add("\\node_modules");
			add("\\dist");
		}
	};

}

public class FolderToFlatFile {
	
	public static String ROOT_FOLDER_STR;
	public static void main(String[] args) throws IOException {
		args = new String[1];
		args[0] = "/home/harsha/data/git/justlikethat";
		if(args.length != 1) {
			System.out.println("Provide root path");
		}
		ROOT_FOLDER_STR = args[0];
		
		File rootFolder = new File(args[0]);
		FolderToFlatFile job = new FolderToFlatFile();
		job.updateSkipFolderNames();
		
		job.write("Starting process at: " + LocalDateTime.now());
		if(rootFolder.exists()) {
			if(job.isAllowedFolder(rootFolder)) {
				System.out.println("Recursing over: " + rootFolder.getAbsolutePath());
				job.recurse(rootFolder);
			}
		} else {
			System.out.println("Provided root folder does not exist");
		}
		job.write("Ending process at: " + LocalDateTime.now());
	
		FileUtils.writeStringToFile(job.resultingFile, job.buffer.toString(), true);
		
	}
	
	public void updateSkipFolderNames() {
		Iterator<String> it = Constants.SKIP_FOLDER_NAMES.iterator();
		List<String> updated = new ArrayList<>();
		String t;
		while(it.hasNext()) {
			t = it.next();
			if(new File(ROOT_FOLDER_STR + t).exists()) {
				updated.add(ROOT_FOLDER_STR + t);
			}
		}
		Constants.SKIP_FOLDER_NAMES.clear();
		Constants.SKIP_FOLDER_NAMES.addAll(updated);
		System.out.println(Constants.SKIP_FOLDER_NAMES);
	}
	
	File resultingFile = new File("output-"+getTimestamp()+".txt");

	void recurse(File file) {
		
		process(file);
		if(!file.isFile() && isAllowedFolder(file)) {
			System.out.println("Recursing over: " + file.getAbsolutePath());
			File[] fileList = file.listFiles();
			for (File f : fileList) {
				recurse(f);
			}
		}
	}
	
	private String getTimestamp() {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");  
        String formatDateTime = localDate.format(format);  
        System.out.println("Current timestamp: " + formatDateTime);  
		return formatDateTime;
	}

	void process(File file) {
		write(extract(file));
	}
	
	StringBuilder buffer = new StringBuilder();
	
	void write(String content) {
		writeToBuffer(content);
	}
	
	void writeToBuffer(String content) {
		if (content == null)
			return;
		
		buffer.append(content);
		
	}
	
	void writeToFile(String content) {
		long start = System.currentTimeMillis();
		if (content == null)
			return;
		
		try {
			FileUtils.writeStringToFile(resultingFile, content, true);
			FileUtils.sizeOf(resultingFile);
		} catch(Exception e) {
			
		}
		System.out.println(String.format("time taken to write %s bytes is %s", 
				content.length(), (System.currentTimeMillis() - start)));
	}
	
	String extract(File file) {
		if(file.isFile()) {
			return readFile(file);
		} else {
			return readFolder(file);
		}
	}
	
	String getChildFolderName(String str) {
		return str.substring(str.lastIndexOf(File.separator) + File.separator.length());
	}

	String readFile(File file) {
		System.out.println("readFile: " + file.getAbsolutePath());
		if(!isAllowedFileType(file)) {
			System.out.println("Skipping this file type");
			return null;
		}
		StringBuilder content = new StringBuilder();
		content.append(getStart(file));
		content.append(getFileContent(file));
		content.append(System.lineSeparator());
		content.append(getEnd());
		return content.toString();
	}
	
	String getFileContent(File file) {
		try {
			return FileUtils.readFileToString(file);
		} catch(Exception e) {
			return "";
		}
	}
	
	String readFolder(File folder) {
		System.out.println("readFolder: " + folder.getAbsolutePath());
		if(!isAllowedFolder(folder)) {
			return null;
		}
		return getStart(folder);
	}
	
	private boolean isAllowedFileType(File file) {
		return !Constants.SKIP_FILE_TYPES
					.contains(FilenameUtils.getExtension(file.getAbsolutePath()));
	}
	
	private boolean isAllowedFolder(File file) {
		
		for(String s : Constants.SKIP_FOLDER_NAMES) {
			try {
			if(file.getCanonicalPath().startsWith(s))
				return false;
			} catch (IOException ioe) {}
		}
		
		return true;
		
//		return !Constants.SKIP_FOLDER_NAMES
//				.contains(getChildFolderName(file.getAbsolutePath()));
	}

	String getStart(File file) {
		return file.isDirectory() 
				? getFolderStart(file)
				: getFileStart(file);
	}
	
	String getFileStart(File file) {
		return Constants.FILE_START_TAG 
				+ file.getAbsolutePath()
				+ System.lineSeparator();
	}
	
	String getFolderStart(File file) {
		return Constants.FOLD_START_TAG 
				+ file.getAbsolutePath()
				+ System.lineSeparator();
	}
	
	String getEnd() {
		return Constants.END_TAG + System.lineSeparator();
	}

}
