package jansible;

import jansible.model.YamlModule;
import jansible.model.YamlParameter;
import jansible.model.YamlParameters;
import jansible.util.YamlDumper;

public class YamlTest2 {

	public static void main(String[] args) {
		YamlDumper yamlDumper = new YamlDumper();
		System.out.println(yamlDumper.dump(createModule()));
	}

	private static YamlModule createModule() {
		YamlModule module = new YamlModule("yum", createParameter());
		return module;
	}

	private static YamlParameters createParameter() {
		YamlParameters parameters = new YamlParameters();
		parameters.addParameter(new YamlParameter("name","mysql"));
		parameters.addParameter(new YamlParameter("state","installed"));
		return parameters;
	}
}