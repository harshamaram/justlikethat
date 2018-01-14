package com.test;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String s[]) {
		
		List<String> list = new ArrayList<String>();
		list.add("f1");
		list.add("f2");
		list.add("f3");
		list.add("f4_(1)");
		list.add("f1");
		list.add("f1");
		
		System.out.println(list.contains("f4"));
		
		/*
		String a1 = "f1";
		String temp;
		temp = getUniqueName(list, a1);
		list.add(temp);
		System.out.println(temp);
		System.out.println(list);
		System.out.println("=======");
		
		String a2 = "f2";
		temp = getUniqueName(list, a2);
		list.add(temp);
		System.out.println(temp);
		System.out.println(list);
		System.out.println("=======");
		
		String a3 = "f3";
		temp = getUniqueName(list, a3);
		list.add(temp);
		System.out.println(temp);
		System.out.println(list);
		System.out.println("=======");
		
		String a4 = "f2";
		temp = getUniqueName(list, a4);
		list.add(temp);
		System.out.println(temp);
		System.out.println(list);
		System.out.println("=======");
		
		String a5 = "f1";
		temp = getUniqueName(list, a5);
		list.add(temp);
		System.out.println(temp);
		System.out.println(list);
		System.out.println("=======");
		
		String a6 = "f1";
		temp = getUniqueName(list, a6);
		list.add(temp);
		System.out.println(temp);
		System.out.println(list);
		System.out.println("=======");*/
		
		
	}
	
	public static String getUniqueName(List<String> list, String name) {
        int count = 1;
        String tempStr = name;

        while (list.contains(tempStr)) {
            tempStr = (new StringBuilder(name))
                    .append("_")
                    .append("(").append(count)
                    .append(")").toString();
            count++;
        }
        return tempStr;

    }
	
	
	

}

