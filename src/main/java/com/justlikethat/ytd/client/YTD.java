package com.justlikethat.ytd.client;

import java.io.File;
import java.net.URL;

import org.w3c.dom.stylesheets.LinkStyle;

import com.github.axet.vget.VGet;

public class YTD {
	
	static String[] links = {
			"https://www.youtube.com/watch?v=vso7uz2D7uw" // daniel tiger
//			, "https://www.youtube.com/watch?v=RZ-AGkY_u3E" // bhagamathi
	};

    public static void main(String[] args) {
    	
    	for (int i=0; i <= links.length; i++ ) {
	        try {
	            String url = links[i];
	            String path = "C:\\data\\Media\\";
	            VGet v = new VGet(new URL(url), new File(path));
	            v.download();
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
    	}
    }
}
