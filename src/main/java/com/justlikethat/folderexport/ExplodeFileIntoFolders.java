package com.justlikethat.folderexport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ExplodeFileIntoFolders {
	
	public static void main(String args[]) {
		args = new String[1];
		args[0] = "output.txt";
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
				folderList.add(line.substring(Constants.FOLD_START_TAG.length()));
			}
		});
		
		for(String s: lines) {
			System.out.println("Creating folder: " + s);
			FileUtils.forceMkdir(new File(s));
		}
		
	}
	
	private void extractIntoFiles(List<String> lines) throws IOException {
		List<String> singleFile = new ArrayList<>();
		boolean startFound = false;
		String fileName = "";
		
		for(String line : lines) {
			if(!startFound && line.startsWith(Constants.FILE_START_TAG)) {
				// found start line of a file
				fileName = line.substring(Constants.FOLD_START_TAG.length());
				startFound = true;
				continue;
			} 
			if(startFound) {
				if(!line.startsWith(Constants.END_TAG)) {
					// contents of the file
					singleFile.add(line);
				} else {
					// reached end of file
					writeToFile(singleFile, fileName);
					singleFile.clear();
					startFound = false;
				}
			}
		}
	}

	private void writeToFile(List<String> lines, String fileName) throws IOException {
		System.out.println("Writing file: " + fileName);
		FileUtils.writeLines(new File(fileName), lines);
	}
}