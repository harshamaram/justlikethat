package com.my.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class FileProcess {

	public static void main(String s[]) {
		String input = s[0];
		String output = s[1];

		try {
			BufferedReader reader = new BufferedReader(new FileReader(input));
			BufferedWriter writer = new BufferedWriter(new FileWriter(output));

			String str;
			String[] tokens;
			while ((str = reader.readLine()) != null) {
				writer.write(merge(str.split("\t"), "|"));
				writer.write("\n");
			}

			writer.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static String merge(String[] t, String delim) {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<t.length; i++) {
			builder.append(t[i]).append(delim);
		}
		if(builder.length() > 1) {
			builder.setLength(builder.length()-1);
		}
		return builder.toString();
	}

}
