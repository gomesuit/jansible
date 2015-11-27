package sample;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class Main {

	public static void main(String[] args) {
		Properties p = new Properties();
		p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		p.setProperty("input.encoding", "UTF-8");
		p.setProperty(Velocity.RESOURCE_LOADER, "file,class");
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "classpath:vm/");
		Velocity.init(p);

		// テンプレートの作成
		Template template = Velocity.getTemplate("vm/test.vm");

		// vmファイルに出力する値を設定
		VelocityContext context = new VelocityContext();
		context.put("item", "ボールペン");
		context.put("price", "1000");

		// テンプレートへ値を出力します。
		StringWriter sw = new StringWriter();
		template.merge(context, sw);
		sw.flush();

		System.out.println(sw.toString());
	}

}
