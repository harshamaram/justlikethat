package com.justlikethat.folderexport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ExplodeFileIntoFolders {
	
	public static void main(String args[]) {
		args = new String[1];
		args[0] = "/home/harsha/my-data/git/learnings/clipboard5.txt";
		if(args.length != 1) {
			System.out.println("Provide file path");
			return;
		}
		
		File rootFile = new File(args[0]);
		ExplodeFileIntoFolders job = new ExplodeFileIntoFolders();
		if(rootFile.exists()) {
			try {
				job.explode(rootFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Provided file does not exist");
		}
	}

	List<String> folderList = new ArrayList<>();
	private void explode(File rootFile) throws IOException {
			List<String> lines = FileUtils.readLines(rootFile);
			extractFolderList(lines);
			extractIntoFiles(lines);
	}
	
	private void extractFolderList(List<String> lines) throws IOException {
		
		lines.forEach(line -> {
			if(line.startsWith(Constants.FOLD_START_TAG)) {
				folderList.add(fixOSFileSeparator(line.substring(Constants.FOLD_START_TAG.length())));
			}
		});
		System.out.printf("Found %d folders to be created", folderList.size());
		
		for(String s: folderList) {
			System.out.println("Creating folder: " + s);
			FileUtils.forceMkdir(new File(s));
		}
		
	}
	
	private void extractIntoFiles(List<String> lines) throws IOException {
		List<String> fileContents = new ArrayList<>();
		boolean startFound = false;
		String fileName = "";
		
		for(String line : lines) {
			if(!startFound && line.startsWith(Constants.FILE_START_TAG)) {
				// found start line of a file
				fileName = line.substring(Constants.FOLD_START_TAG.length());
				fileName = fixOSFileSeparator(fileName);
				startFound = true;
				continue;
			} 
			if(startFound) {
				if(!line.startsWith(Constants.END_TAG)) {
					// contents of the file
					fileContents.add(line);
				} else {
					// reached end of file
					writeToFile(fileContents, fileName);
					fileContents.clear();
					startFound = false;
				}
			}
		}
	}

	private void writeToFile(List<String> lines, String fileName) throws IOException {
		System.out.println("Writing file: " + fileName);
		FileUtils.writeLines(new File(fileName), lines);
	}
	
	private String fixOSFileSeparator(String str) {
		str = str.replace('\\', File.separatorChar);
		str = str.replace('/', File.separatorChar);
		
		return str;
	}
}