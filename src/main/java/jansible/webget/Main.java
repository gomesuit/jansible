package jansible.webget;

import jansible.model2.Module;
import jansible.model2.Parameter;

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

public class Main {

	public static void main(String[] args) throws IOException {
		//Module module = getModule("http://docs.ansible.com/ansible/copy_module.html");
		Module module = getModule("http://docs.ansible.com/ansible/win_package_module.html");
		System.out.println(module);
        for(Parameter parameter : module.getParameterList()){
        	System.out.println(parameter);
        }
	}

	private static Module getModule(String url) throws IOException{
		Document document = Jsoup.connect(url).get();
        Elements elements = document.select("#options tr");
        List<Parameter> parameterList = getParameterList(elements);
        Elements titleElements = document.select("title");
        Module module = new Module();
        module.setParameterList(parameterList);
        module.setName(getModuleName(titleElements.text()));
        module.setDescription(getDescription(titleElements.text()));
        return module;
	}
	
	private static String getModuleName(String title){
		String[] nameList = title.split(" ");
		return nameList[0];
	}
	
	private static String getDescription(String title){
		String regex = " \\- (.*) —";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(title);
		if (m.find()){
			return m.group(1);
		}else{
			return null;
		}
	}
	
	private static List<Parameter> getParameterList(Elements elements){
		List<Parameter> ParameterList = new ArrayList<>();
		
        for (Element element : elements) {
            Elements elements2 = element.select("td");
            Parameter parameter = getParameter(elements2);
            if(parameter != null){
            	ParameterList.add(parameter);
            }
        }
        
		return ParameterList;
	}

	private static Parameter getParameter(Elements elements){
		List<String> stringList = elementsToStringList(elements);
		if(!stringList.isEmpty()){
			Parameter parameter = createParameter(stringList);
			return parameter;
		}else{
			return null;
		}
	}
	
	private static Parameter createParameter(List<String> stringList){
		Parameter parameter = new Parameter();
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
