package com.justlikethat.subtitles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesTimeFrame {
	public static void main(String s[]) {
		try {
			int adjust = Integer.parseInt("2000");
			FileInputStream fstream = new FileInputStream(
					"C:\\Users\\Hello\\Downloads\\The Departed (2006)\\The.Departed.srt");
			FileWriter fout = new FileWriter(
					"C:\\Users\\Hello\\Downloads\\The Departed (2006)\\The.Departed_1.srt");

			System.out.println("=== start ===");

			DataInputStream in = new DataInputStream(fstream);

			BufferedWriter out = new BufferedWriter(fout);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			Pattern p = Pattern
					.compile("[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9]");
			Matcher m;

			String strLine;
			StringBuffer buffer = new StringBuffer();
			String actualStr, adjustedStr;
			Date adjustedTime;
			SimpleDateFormat format = new SimpleDateFormat("KK:mm:ss,SSS");
			int count = 0, start, end; 
			boolean flag = false;

			while ((strLine = br.readLine()) != null) {
				count++;
				flag = false;
				buffer.setLength(0);
				buffer.append(strLine);
				m = p.matcher(strLine);

				while (m.find()) {
					start = m.start();
					end = m.end();
					flag = true;
					actualStr = strLine.substring(start, end);
					adjustedTime = format.parse(actualStr);
					adjustedTime.setTime(adjustedTime.getTime() + adjust);
					// System.out.println("Actual: "+actualStr+"; adjusted: "+adjustedTime);
					adjustedStr = format.format(adjustedTime);

					buffer.replace(start, end, adjustedStr);
				}
				// if (flag ) System.out.println(buffer);
				out.write(buffer.toString()
						+ System.getProperty("line.separator"));

			}

			System.out.println("=== end ===");

			in.close();
			out.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	
}