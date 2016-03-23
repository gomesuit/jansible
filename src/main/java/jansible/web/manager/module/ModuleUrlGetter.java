package jansible.web.manager.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ModuleUrlGetter {

	public static List<String> getUrlList() throws IOException{
		Document document = Jsoup.connect("http://docs.ansible.com/ansible/list_of_all_modules.html").get();
        Elements elements = document.select("#all-modules li a.internal");
        
        List<String> urlList = new ArrayList<>();
        for (Element element : elements) {
        	String url = getUrl(element);
        	urlList.add(url);
        }
        
        return urlList;
	}
	
	private static String getUrl(Element element){
		return "http://docs.ansible.com/ansible/" + getHtml(element);
	}
	
	private static String getHtml(Element element){
		return element.attr("href");
	}
}
