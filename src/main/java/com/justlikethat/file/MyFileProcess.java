package com.justlikethat.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFileProcess {

	public static void main(String s[]) {
			/*
			s = new String[4];
			s[0] = "REPLACE_REGEX";
			s[1] = "/home/harsha/my-data/git/justlikethat/input.txt";
			s[2] = "";
			s[3] = "";
			//*/

		if(s == null || s.length == 0) {
			displayUsage();
			return;
		}

		String cmd = s[0];
		try {
			if (cmd.equals("REPLACE")) {
				replaceInEachLine(s);
			} else if(cmd.equals("REPLACE_REGEX")) {
				replaceRegexInEachLine(s);
			} else if (cmd.equals("SPLIT_INTO_FILES")) {
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
			"\tREPLACE {source-file} {source-string} {target-string}\n" +
			"\t\tReplaces in given {source-file} by {source-string} with {target-string} in each line of the file\n";

	private static final String USAGE_SPLIT_INTO_FILES =
			"\tSPLIT_INTO_FILES {source-file} {line-count}\n"+
			"\t\tSplits a give {source-file} into multiple lines - each file containing {line-count} lines\n";

	private static final String USAGE_SPLIT_CONTENT_BASED =
			"\tSPLIT_CONTENT_BASED {source-file} {identifier-text}\n" +
			"\t\tSplits a give {source-file} into multiple files - each separated by {indentifier-text}\n";

	private static final String USAGE_EXTRACT_TOP_ROWS =
			"\tEXTRACT_TOP_ROWS {line-count} lines from given {source-file}\n" +
			"\t\textracts top {line-count} lines from given {source-file}\n";

	private static final String USAGE_MERGE =
			"\tMERGE {folder-location} {extension} {output-file}\n" +
			"\t\tmerges all files with given {extension} in given {folder-location} and writes the result to {output-file}\n";

	private static final String USAGE_INJECT_DELIMITERS =
			"\tINJECT_DELIMITERS {input-file} {delimiter} {index}+\n" +
			"\t\tAdds given {delimiter} at {index} to each line to file {input-file} \n" +
			"\t\t{index} can be 1 or more separated by space)\n";

	private static final String USAGE_REPLACE_REGEX =
			"\tREPLACE_REGEX {input-file} {find-regex} {replace-regex}\n" +
			"\t\tFinds based on {find-regex} and replaces it with {replace-regex} in each line.\n" +
			"\t\tYou can use groups in regex to make it efficient\n" +
			"\t\t{index} can be 1 or more separated by space)\n";

	private static void displayUsage() {
		System.out.println(
				"java MyFileProcess <COMMAND> [ARGUMENTS]\n"
						+ USAGE_REPLACE
						+ USAGE_REPLACE_REGEX
						+ USAGE_SPLIT_INTO_FILES
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

	private static void replaceRegexInEachLine(String[] s) {
		try {

			if(s == null || s.length != 4) throw new Exception("check usage");
			String input = s[1];
			String findRegex = s[2];
			String replaceRegex = s[3];

			List<Integer> list = getIndexList(replaceRegex);
			String formattedString = getFormattedString(replaceRegex);
			// System.out.println(String.format(getFormattedString(replaceRegex), list.toArray()));

			String output = getSuggestedOutputFileName(input);

			BufferedReader reader = new BufferedReader(new FileReader(input));
			BufferedWriter writer = new BufferedWriter(new FileWriter(output));

			String str;
			String[] tokens;
			Pattern findPattern = Pattern.compile(findRegex);
			Matcher m;
			Map<Integer, String> map = new HashMap<>();
			List<String> valueList = new ArrayList<>();

			int count;

			while ((str = reader.readLine()) != null) {
				//writer.write(filter(merge(str.split(source), target)));
				m = findPattern.matcher(str);
				m.find();
				for(int i=0; i<=m.groupCount(); i++) {
					map.put(i, m.group(i));
				}

				for(Integer i : list) {
					valueList.add(map.get(i));
				}

				writer.write(String.format(formattedString, valueList.toArray()));
				writer.write("\n");

				map.clear();
				valueList.clear();

			}

			writer.close();
			reader.close();

		} catch (Exception e) {
			System.out.println("Exception in running: " + e.getMessage());
			e.printStackTrace();
			System.out.println(
					USAGE_REPLACE_REGEX);
		}
	}

    public static void main2(String s[]) {
        String str = "abc123def";
        String regex = "([a-z]*)([0-9]*)([a-z]*)";
        String replaceRegex = "(1)NEW(3)AND(2)";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        boolean r = m.find();
        Map<Integer, String> map = new HashMap<>();
        System.out.println("group count: " + m.groupCount());
        for(int i=0; i<=m.groupCount(); i++) {
            map.put(i, str.substring(m.start(i), m.end(i)));
        }
        System.out.println(map);

        StringBuilder sb = new StringBuilder(str);

        List<Integer> list = getIndexList(replaceRegex);
        System.out.println(String.format(getFormattedString(replaceRegex), list.toArray()));

        List<String> valueList = new ArrayList<>();
        for(Integer i : list) {
            valueList.add(map.get(i));
        }
        System.out.println(String.format(getFormattedString(replaceRegex), valueList.toArray()));

    }

    static String findGroupsRegex = "\\((\\d+)\\)";

    static String getFormattedString(String replaceRegex) {
        String formatted = replaceRegex;
        formatted = formatted.replaceAll(findGroupsRegex, "%s");
        return formatted;
    }

    static List getIndexList(String replaceRegex) {
        Pattern findGroupsRegexPattern = Pattern.compile(findGroupsRegex);
        Matcher m = findGroupsRegexPattern.matcher(replaceRegex);
        List<Integer> list = new ArrayList<>();

        while(m.find()) {
            list.add(Integer.parseInt(m.group(1)));
        }
        return list;
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
	
