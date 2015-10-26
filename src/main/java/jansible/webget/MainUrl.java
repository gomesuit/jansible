package jansible.webget;

import java.io.IOException;
import java.util.List;

public class MainUrl {

	public static void main(String[] args) throws IOException {
        List<String> urlList = ModuleUrlGetter.getUrlList();
        
        for (String url : urlList) {
        	System.out.println(url);
        }
	}
}
