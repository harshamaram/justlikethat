package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public interface Util {
	
	List<String> FC_INTERNAL_ALLOWED_FILE_TYPES = new ArrayList<String>() {
	    
        private static final long serialVersionUID = 1L;
        {
			System.out.println("adding to list");
			add("one");
			add("two");
			add("three");
			add("four");
        }

    };
    
    List<String> A = new ArrayList<String>(Arrays.asList("a","b"));

}
