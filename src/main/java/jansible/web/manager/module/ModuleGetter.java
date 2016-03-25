package jansible.web.manager.module;

import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ModuleGetter {

	public static HtmlModule getModule(String url) throws IOException{
		Document document = Jsoup.connect(url).get();
		HtmlModule module = createHtmlModule(document);
		return module;
	}
	
	private static HtmlModule createHtmlModule(Document document){
		List<HtmlParameter> parameterList = getParameterList(document);
		String moduleName = getModuleName(document);
		String description = getDescription(document);

		HtmlModule module = new HtmlModule();
		module.setParameterList(parameterList);
		module.setName(moduleName);
		module.setDescription(description);
		return module;
	}
	
	private static List<HtmlParameter> getParameterList(Document document){
		Elements elements = document.select("#options tr");
		List<HtmlParameter> ParameterList = new ArrayList<>();
		
		for (Element element : elements) {
			Elements elements2 = element.select("td");
			HtmlParameter parameter = getParameter(elements2);
			if(parameter != null){
				ParameterList.add(parameter);
			}
		}
		
		return ParameterList;
	}

	private static String getModuleName(Document document){
		Elements titleElements = document.select("title");
		String[] nameList = titleElements.text().split(" ");
		return nameList[0];
	}
	
	private static String getDescription(Document document){
		Elements titleElements = document.select("title");
		String title = titleElements.text();
		
		String regex = " \\- (.*) —";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(title);
		if (m.find()){
			return m.group(1);
		}else{
			return null;
		}
	}
	
	private static HtmlParameter getParameter(Elements elements){
		List<String> stringList = elementsToStringList(elements);
		if(!stringList.isEmpty()){
			HtmlParameter parameter = createParameter(stringList);
			return parameter;
		}else{
			return null;
		}
	}
	
	private static HtmlParameter createParameter(List<String> stringList){
		HtmlParameter parameter = new HtmlParameter();
		parameter.setName(getName(stringList.get(0)));
		parameter.setAddedVersion(getAddedVersion(stringList.get(0)));
		parameter.setRequired(isRequired(stringList.get(1)));
		parameter.setDefaultValue(stringList.get(2));
		parameter.setChoices(getChoices(stringList.get(3)));
		parameter.setDescription(stringList.get(4));
		parameter.setFreeForm(isFreeForm(parameter.getName()));
		return parameter;
	}
	
	private static boolean isFreeForm(String name){
		if(name.equals("free_form")){
			return true;
		}else{
			return false;
		}
	}
	
	private static String getName(String name){
		String[] nameList = name.split(" ");
		
		if(nameList.length == 1){
			return name;
		}else{
			return nameList[0];
		}
	}
	
	private static String getAddedVersion(String name){
		String regex = "\\(added in (.*)\\)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		if (m.find()){
			return m.group(1);
		}else{
			return null;
		}
	}
	
	private static boolean isRequired(String required){
		if(required == null){
			return false;
		}
		
		if(required.equals("yes")){
			return true;
		}else{
			return false;
		}
	}
	
	private static List<String> getChoices(String choicesString){
		if(choicesString == null){
			return null;
		}
		
		String[] Choices = choicesString.split(" ");
		
		return Arrays.asList(Choices);
	}
	
	private static List<String> elementsToStringList(Elements elements){
		List<String> stringList = new ArrayList<>();
		for (Element element : elements) {
			stringList.add(element.text());
		}
		return stringList;
	}
}
