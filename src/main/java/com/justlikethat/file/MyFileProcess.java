package com.justlikethat.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyFileProcess {

	public static void main(String s[]) {

		if(s == null || s.length == 0) {
			s = new String[7];
			s[0] = "INJECT_DELIMITERS";
			s[1] = "input.txt";
			s[2] = "\t";
			s[3] = "5";
			s[4] = "8";
			s[5] = "10";
			s[6] = "15"; //*/
		}

		String cmd = s[0];

		try {
			if (cmd == "REPLACE") {
				replaceInEachLine(s);
			} else if (cmd.equals("SPLIT")) {
				splitIntoMultipleFiles(s);
			} else if (cmd.equals("MERGE")) {
				mergeAllFiles(s);
			} else if (cmd.equals("SPLIT_CONTENT_BASED")) {
				splitBasedOnLineContent(s);
			} else if (cmd.equalsIgnoreCase("EXTRACT_TOP_ROWS")) {
				extractTopRows(s);
			} else if (cmd.equalsIgnoreCase("INJECT_DELIMITERS")) {
				injectDelimiters(s);
			} else {
				displayUsage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String USAGE_REPLACE =
			"REPLACE: Replaces in given {source-file} by {source-string} with {target-string} in each line of the file\n" +
			"Required: source-file\n" +
			"Required: source-string\n" +
			"Required: target-string\n\t";
	private static final String USAGE_SPLIT =
			"SPLIT: splits a give {source-file} into multiple lines - each separated by {line-count}\n" +
			"Required: source-file\n" +
			"Required: line-count\n\t";
	private static final String USAGE_SPLIT_CONTENT_BASED =
			"SPLIT_CONTENT_BASED: splits a give {source-file} into multiple files - each separated by {indentifier-text}\n" +
			"Required: source-file\n" +
			"Required: indentifier-text\n\t";
	private static final String USAGE_EXTRACT_TOP_ROWS =
			"EXTRACT_TOP_ROWS: extracts top {line-count} lines from given {source-file}\n" +
			"Required: source-file\n" +
			"Requried: line-count\n\t";
	private static final String USAGE_MERGE =
			"MERGE: merges all files with given {extension} in given {folder-location} and writes the result to {output-file}\n" +
			"Required: folder-location\n" +
			"Required: extention\n" +
			"Required: output-file\n\t";
	private static final String USAGE_INJECT_DELIMITERS =
			"\tINJECT_DELIMITERS: addes given {delimiter} at {indexes} to each line to file {input-file} \n" +
			"Required: input-file\n" +
			"Required: delimitter\n" +
			"Required: indexes (1 or more separated by space)\n";

	private static void displayUsage() {
		System.out.println(
				"java MyFileProcess <COMMAND> [ARGUMENTS]\n\t"
						+ USAGE_REPLACE
						+ USAGE_SPLIT
						+ USAGE_SPLIT_CONTENT_BASED
						+ USAGE_EXTRACT_TOP_ROWS
						+ USAGE_INJECT_DELIMITERS
						+ USAGE_MERGE
		);
	}

	// java MyFileProcess INJECT_DELIMITERS "D:\Legacy migration\ScriptCare\RxSense_ScriptCare_201907_No_CF.txt" "\t" 1 5 10 15
	private static void injectDelimiters(String[] s) throws Exception {
		if (s.length < 4) {
			throw new Exception("java MyFileProcess INJECT_DELIMITTERS <FILE_LOCATION> <DELIMITTER> <index locations>");
		}
		String fileLoc = s[1];
		String delim = s[2];
		int indexes[] = new int[s.length - 3];
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = Integer.parseInt(s[i + 3]);
		}

		File currentFile = new File(fileLoc);
		File outputFile = new File(getSuggestedOutputFileName(currentFile));

		BufferedReader reader = new BufferedReader(new FileReader(currentFile));
		FileWriter fileWriter = new FileWriter(outputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		String str;
		String result;
		while ((str = reader.readLine()) != null) {
			result = addIndexes(str, delim, indexes);
			bufferedWriter.write(result);
			bufferedWriter.write("\n");
		}

		reader.close();
		bufferedWriter.close();
		fileWriter.close();

	}

	private static String addIndexes(String str, String delim, int[] indexes) {

		int start = 0;
		List<String> tokens = new ArrayList<>();

		for (int i = 0; i < indexes.length; i++) {
			tokens.add(str.substring(start, indexes[i]));
			start = indexes[i];
		}

		tokens.add(str.substring(start));

		StringBuffer buff = new StringBuffer();
		for (String s : tokens) {
			buff.append(s).append(delim);
		}
		buff.setLength(buff.length() - 1);
		return buff.toString();
	}

	private static void mergeAllFiles(String[] s) throws IOException {
		String folderLoc = s[1];
		String extn = s[2];
		String outputFileName = s[3];

		File folder = new File(folderLoc);
		System.out.println(folder.getAbsolutePath());
		String[] list = folder.list(new MyFileNameFilter(extn));

		String output = folderLoc + File.separator + outputFileName;
		File outputFile = new File(output);
		FileWriter fileWriter = new FileWriter(outputFile, true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		for (int i = 0; i < list.length; i++) {
			System.out.println(list[i]);
			File currentFile = new File(folderLoc + File.separator + list[i]);

			BufferedReader reader = new BufferedReader(new FileReader(currentFile));
			String str;
			while ((str = reader.readLine()) != null) {
				bufferedWriter.write(str);
				bufferedWriter.write("\n");
			}
			reader.close();

		}

		bufferedWriter.close();
		fileWriter.close();

	}

	private static void extractTopRows(String[] s) throws IOException {
		String input = s[1];
		int rowCount = Integer.parseInt(s[2]);

		File inputFile = new File(input);
		String fullFileName = inputFile.getAbsolutePath();
		String fileName = inputFile.getName();
		fileName = fileName.substring(0, fileName.indexOf("."));

		String fileExtn = getFileExtension(inputFile);

		System.out.println(fullFileName);
		System.out.println(fileName);

		File output = new File(getParentFolderName(inputFile) + File.separator + fileName + "_top_" + rowCount + "." + fileExtn);
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));

		String str;

		while ((str = reader.readLine()) != null
				&& rowCount-- > 0) {
			writer.write(str);
			writer.write("\n");

		}

		if (writer != null) {
			writer.close();
		}

		reader.close();

	}

	private static void splitIntoMultipleFiles(String s[]) throws IOException {
		String input = s[1];
		String input2 = s[2];
		int linesInEachFile = Integer.parseInt(input2);

		File inputFile = new File(input);
		String fullFileName = inputFile.getAbsolutePath();
		String fileName = inputFile.getName();
		fileName = fileName.substring(0, fileName.indexOf("."));

		String parentFolder = fullFileName.substring(0, fullFileName.lastIndexOf(File.separator));

		String fileExtn = fullFileName.substring(fullFileName.lastIndexOf(".") + 1);

		System.out.println(fullFileName);
		System.out.println(parentFolder);
		System.out.println(fileName);
		System.out.println(fileExtn);

		int counter = 0;
		int fileCount = 1;
		File output = new File(parentFolder + File.separator + fileName + fileCount + "." + fileExtn);
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));

		String str;

		while ((str = reader.readLine()) != null) {
			writer.write(str);
			writer.write("\n");
			counter++;

			if (counter >= linesInEachFile) {
				writer.close();
				fileCount++;
				counter = 0;
				output = new File(parentFolder + File.separator + fileName + fileCount + "." + fileExtn);
				writer = new BufferedWriter(new FileWriter(output));
			}
		}

		if (writer != null) {
			writer.close();
		}

		reader.close();

	}

	private static void splitBasedOnLineContent(String s[]) throws IOException {
		try {
			if(s == null || s.length != 3) throw new Exception("check usage");
			String input = s[1];
			String lineContent = s[2];

			File inputFile = new File(input);
			String fullFileName = inputFile.getAbsolutePath();

			String fileName = getFileNameWithoutExtension(inputFile);
			String parentFolder = getParentFolderName(inputFile);
			String fileExtn = getFileExtension(inputFile);

			System.out.println(fullFileName);
			System.out.println(parentFolder);
			System.out.println(fileName);
			System.out.println(fileExtn);

			int fileCount = 1;
			String completeFileName;
			completeFileName = parentFolder + File.separator + fileName + fileCount + "." + fileExtn;
			File output = new File(completeFileName);
			System.out.println("completeFileName: " + completeFileName);
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(output));

			String str;

			while ((str = reader.readLine()) != null) {
				writer.write(str);
				writer.write("\n");

				if (lineContent.equals(str)) {
					writer.close();
					fileCount++;
					completeFileName = parentFolder + File.separator + fileName + fileCount + "." + fileExtn;
					System.out.println("completeFileName: " + completeFileName);
					output = new File(completeFileName);
					writer = new BufferedWriter(new FileWriter(output));
				}
			}

			if (writer != null) {
				writer.close();
			}

			reader.close();
		} catch (Exception e) {
			System.out.println("Exception in running: " + e.getMessage());
			System.out.println(
					USAGE_SPLIT_CONTENT_BASED);

		}
	}

	private static void replaceInEachLine(String s[]) {
		try {
			if(s == null || s.length != 4) throw new Exception("check usage");
			String input = s[1];
			String source = s[2];
			String target = s[3];

			String output = getSuggestedOutputFileName(input);

			BufferedReader reader = new BufferedReader(new FileReader(input));
			BufferedWriter writer = new BufferedWriter(new FileWriter(output));

			String str;
			String[] tokens;
			while ((str = reader.readLine()) != null) {
				writer.write(filter(merge(str.split(source), target)));
				writer.write("\n");
			}

			writer.close();
			reader.close();
		} catch (Exception e) {
			System.out.println("Exception in running: " + e.getMessage());
			System.out.println(
					USAGE_REPLACE);

		}
	}

	private static String getSuggestedOutputFileName(File file) {
		return getSuggestedOutputFileName(file.getAbsolutePath());
	}

	private static String getSuggestedOutputFileName(String str) {
		String folder = getParentFolderName(str);
		return folder + File.separator + getFileNameWithoutExtension(str) + "_" + getFormatterTimestamp() + getFileExtension(str);

	}

	private static String getParentFolderName(File file) {
		return getParentFolderName(file.getAbsolutePath());
	}

	private static String getParentFolderName(String absPath) {
		String parentFolder = absPath.substring(0, absPath.lastIndexOf(File.separator));
		return parentFolder;
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		return getFileExtension(fileName);
	}

	private static String getFileExtension(String str) {
		String fileName = str;
		int index = fileName.lastIndexOf(".");
		return index == -1 ? "" : fileName.substring(fileName.indexOf("."));
	}

	private static String getFileNameWithoutExtension(File file) {
		String fileName = file.getName();
		return getFileNameWithoutExtension(fileName);
	}

	private static String getFileNameWithoutExtension(String str) {
		String fileName = str;
		int index = fileName.lastIndexOf(".");
		int start = fileName.lastIndexOf(File.separator);
		start = start == -1 ? 0 : start + 1;
		return index == -1 ? fileName : fileName.substring(start, fileName.indexOf("."));
	}

	private static String getFormatterTimestamp() {
		String pattern = "yyyyMMdd_HHmmss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(Calendar.getInstance().getTime());
	}


	private static String filter(String str) {
		return str.replace("\"", "");
	}

	private static String merge(String[] t, String delim) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < t.length; i++) {
			builder.append(t[i]).append(delim);
		}
		if (builder.length() > 1) {
			builder.setLength(builder.length() - 1);
		}
		return builder.toString();
	}


}

//FileNameFilter implementation
class MyFileNameFilter implements FilenameFilter {

	private String extension;

	public MyFileNameFilter(String extension) {
		this.extension = extension.toLowerCase();
	}

	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(extension);
	}

}
	
