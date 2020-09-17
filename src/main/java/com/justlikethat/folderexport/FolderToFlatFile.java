package com.justlikethat.folderexport;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FolderToFlatFile {

	public static void main(String[] args) {
		args = new String[1];
		args[0] = "/home/harsha/data/git/justlikethat";
		if(args.length != 1) {
			System.out.println("Provide root path");
		}
		
		File rootFolder = new File(args[0]);
		FolderToFlatFile job = new FolderToFlatFile();
		if(rootFolder.exists()) {
			if(job.isAllowedFolder(rootFolder)) {
				job.recurse(rootFolder);
			}
		} else {
			System.out.println("Provided root folder does not exist");
		}
	
	}
	
	File resultingFile = new File("output.txt");

	void recurse(File file) {
		process(file);
		if(! file.isFile()) {
			File[] fileList = file.listFiles();
			for (File f : fileList) {
				recurse(f);
			}
		}
	}
	
	void process(File file) {
		writeToFile(extract(file));
	}
	
	void writeToFile(String content) {
		try {
			FileUtils.writeStringToFile(resultingFile, content, true);
		} catch(Exception e) {
			
		}
	}
	
	String extract(File file) {
		System.out.println("Extract: " + file.getAbsolutePath());
		if(file.isFile()) {
			return readFile(file);
		} else {
			return readFolder(file);
		}
	}
	
	String getChildFolderName(String str) {
		return str.substring(str.lastIndexOf(File.separator));
	}

	String readFile(File file) {
		if(!isAllowedFileType(file)) {
			System.out.println("Skipping this file type");
			return null;
		}
		StringBuilder content = new StringBuilder();
		content.append(getStart(file));
		content.append(getFileContent(file));
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
	
	String readFolder(File file) {
		if(!isAllowedFolder(file))
			return null;
		return getStart(file);
	}
	
	private boolean isAllowedFileType(File file) {
		return !Constants.SKIP_FILE_TYPES
					.contains(FilenameUtils.getExtension(file.getAbsolutePath()));
	}
	
	private boolean isAllowedFolder(File file) {
		return !Constants.SKIP_FOLDER_NAMES
				.contains(getChildFolderName(file.getAbsolutePath()));
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