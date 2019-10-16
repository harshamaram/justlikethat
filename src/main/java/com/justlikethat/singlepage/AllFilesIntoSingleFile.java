package com.justlikethat.singlepage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class AllFilesIntoSingleFile {
	public static void main(String s[]) {
		if(s!= null && (s.length == 1 || s.length == 2)) {
			String inputFolder = s[0];
			String outputFileName = s.length == 2 ? s[1] : "output-file.txt";
			new AllFilesIntoSingleFile().process(new File(inputFolder), outputFileName);
		} else {
			System.out.println("Usage com.justlikethat.singlepage.AllFilesIntoSingleFile "
					+ "<input-folder> [output-file-name]");
		}
	}

	private void process(File inputFolder, String outputFileName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
			recursiveProcess(inputFolder, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recursiveProcess(File folder, BufferedWriter writer) throws IOException {
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			
			if (listOfFiles[i].isDirectory()) {
				appendDirectoryInfo(listOfFiles[i], writer);
				recursiveProcess(listOfFiles[i], writer);
			} else {
				appendFileInfo(listOfFiles[i], writer);
			}
		}		
	}

	private static String FOLDER_KEY_START = ">>>QWERTYFOLDER12345START";
	private static String FOLDER_KEY_END = ">>>QWERTYFOLDER12345END";
	private static String FILE_KEY_START = ">>>QWERTYFILE12345START";
	private static String FILE_KEY_END = ">>>QWERTYFILE12345END";
	
	private void appendFileInfo(File file, BufferedWriter writer) throws IOException {
		writer.append(FILE_KEY_START);
		writer.append(file.getAbsolutePath());
		writer.append(FILE_KEY_END);
		writer.append(readFileContent(file));
	}
	
	private String readFileContent(File file) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		System.out.println(file.getAbsolutePath());
		List<String> allLines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		allLines.forEach(str -> contentBuilder.append(str).append("\n"));
	    return contentBuilder.toString();
	}
	
	private void appendDirectoryInfo(File file, BufferedWriter writer) throws IOException {
		writer.append(FOLDER_KEY_START);
		writer.append(file.getAbsolutePath());
		writer.append(FOLDER_KEY_END);
		
	}
}
