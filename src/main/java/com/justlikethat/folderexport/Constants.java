package com.justlikethat.folderexport;

import java.util.ArrayList;
import java.util.List;

public class Constants {
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
			
		}
	};
	
	final static List<String> SKIP_FOLDER_NAMES = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add(".git");
			add(".settings");
		}
	};

}