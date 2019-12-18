package com.myquest.hackerrank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PythegoreanTriplet {
	
	public static void main(String s[]) {
		int arr[] = {13, 10, 4, 6, 12, 5}; //{3, 1, 4, 6, 5}; //
		List<Integer> list = toList(arr);
		Collections.sort(list);
		
		System.out.println(list);
		
		List<Integer> squares = getSquares(list);
		System.out.println(squares);
		
		//int index;
		boolean flag = false;
		int tSum;
		for(int i=0; i<squares.size()-2; i++) {
			for(int j=i+1; j<squares.size()-1;j++) {
				tSum = squares.get(i) + squares.get(j);
				flag = false;
				System.out.println("Checking: " 
						+ squares.get(i) + ", " 
						+ squares.get(j));
				for(int k=j+1; k<squares.size(); k++) {
					System.out.println("\t"+tSum+" agaist: " + squares.get(k));
					if(squares.get(k) == tSum) {
						flag = true;
						break;
					}
					if(tSum < squares.get(k)) {
						break;
					}
				}
				
				if(flag) {
					break;
				}
			}
			if(flag) {
				break;
			}
		}
		if(flag)
			System.out.println("Found");
		else 
			System.out.println("Not Found");
	}

	private static List<Integer> getSquares(List<Integer> list) {
		
		for(int i=0; i<list.size(); i++) {
			list.set(i, list.get(i) * list.get(i));
		}
		return list;
	}
	
	private static List<Integer> toList(int[] arr) {
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<arr.length; i++) {
			list.add(arr[i]);
		}
		
		return list;
	}
}
