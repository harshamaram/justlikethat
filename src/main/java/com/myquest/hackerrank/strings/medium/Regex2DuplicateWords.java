package com.myquest.hackerrank.strings.medium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex2DuplicateWords {

    public static void main(String[] args) {

        String regex = "/* Write a RegEx matching repeated words here. */";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE); ///* Insert the correct Pattern flag here.*/
        
        String input = "one two One three two four four FIVE five six sIx seven eight Seven";
        
        Matcher m = p.matcher(input);
        
        // Check for subsequences of input that match the compiled pattern
        while (m.find()) {
            input = input.replaceAll("", ""); // /* The regex to replace */, /* The replacement. */
        }
        
        // Prints the modified sentence.
        System.out.println(input);
    }
}


