package jansible.webget;

import jansible.model2.Module;
import jansible.model2.Parameter;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		//Module module = getModule("http://docs.ansible.com/ansible/copy_module.html");
		Module module = ModuleGetter.getModule("http://docs.ansible.com/ansible/win_package_module.html");
		System.out.println(module);
        for(Parameter parameter : module.getParameterList()){
        	System.out.println(parameter);
        }
	}

}
