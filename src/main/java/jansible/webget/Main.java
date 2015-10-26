package jansible.webget;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("http://docs.ansible.com/ansible/copy_module.html").get();
        //System.out.println(document.html());

        Elements elements = document.select("#options tr");
        for (Element element : elements) {
            //System.out.println(element.outerHtml());
        	System.out.println("=====================");
            Elements elements2 = element.select("tr");
            for (Element element2 : elements2) {
                //System.out.println(element2.html());
                Elements elements3 = element2.select("td");
                for (Element element3 : elements3) {
                	System.out.println(element3.text());
                }
            }
        }
	}

}
