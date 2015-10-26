package jansible.webget;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainUrl {

	public static void main(String[] args) throws IOException {
		Document document = Jsoup.connect("http://docs.ansible.com/ansible/list_of_all_modules.html").get();
        Elements elements = document.select("#all-modules li a.internal");
        
        for (Element element : elements) {
        	String url = getUrl(element);
        	System.out.println(url);
        }
	}
	
	private static String getUrl(Element element){
		return "http://docs.ansible.com/ansible/" + getHtml(element);
	}
	
	private static String getHtml(Element element){
		return element.attr("href");
	}

}
