package jansible.template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class JansibleTemplate {
	private static final String TEMPLATE_FILE_PREFIX = "vm/";

	public static String getString(String fileName) {
		Map<String, String> param = new HashMap<>();
		return getString(fileName, param);
	}

	public static String getString(String fileName, Map<String, String> param) {
		VelocityEngine velocityEngine = createVelocityEngine();
		
		// テンプレートの作成
		Template template = velocityEngine.getTemplate(getTemplatePath(fileName));

		// vmファイルに出力する値を設定
		VelocityContext context = createVelocityContext(param);

		// テンプレートへ値を出力します。
		StringWriter stringWriter = new StringWriter();
		template.merge(context, stringWriter);
		stringWriter.flush();
		
		return stringWriter.toString();
	}
	
	private static VelocityContext createVelocityContext(Map<String, String> param){
		VelocityContext context = new VelocityContext();
		for(Map.Entry<String, String> e : param.entrySet()){
			context.put(e.getKey(), e.getValue());
		}
		return context;
	}
	
	private static String getTemplatePath(String fileName){
		return TEMPLATE_FILE_PREFIX + fileName;
	}
	
	private static VelocityEngine createVelocityEngine(){
		VelocityEngine velocityEngine = new VelocityEngine();
		
		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine.setProperty("input.encoding", "UTF-8");
		velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "file,class");
		velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "classpath:vm/");
		velocityEngine.init();
		
		return velocityEngine;
	}
}
