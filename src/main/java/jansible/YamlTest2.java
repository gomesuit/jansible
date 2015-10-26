package jansible;

import jansible.model.Module;
import jansible.model.Parameter;
import jansible.model.Parameters;
import jansible.util.YamlDumper;

public class YamlTest2 {

	public static void main(String[] args) {
		YamlDumper yamlDumper = new YamlDumper();
		System.out.println(yamlDumper.dump(createModule()));
	}

	private static Module createModule() {
		Module module = new Module("yum", createParameter());
		return module;
	}

	private static Parameters createParameter() {
		Parameters parameters = new Parameters();
		parameters.addParameter(new Parameter("name","mysql"));
		parameters.addParameter(new Parameter("state","installed"));
		return parameters;
	}
}