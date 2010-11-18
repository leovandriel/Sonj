package net.sf.sonj.demo;

import net.sf.sonj.collection.JsonArray;
import net.sf.sonj.collection.JsonFactory;
import net.sf.sonj.collection.JsonFactory.UtilFactory;
import net.sf.sonj.collection.JsonObject;
import net.sf.sonj.util.JsonConvert;

public class Demo {
	private JsonFactory factory = UtilFactory.get();

	public static void main(String[] args) {
		new Demo().run();
	}

	public void run() {
		formatting();
	}

	public void formatting() {
		String arrayText = "[ \"a\", \"b\" ]";
		JsonArray array = JsonConvert.toJsonArray(arrayText, factory);
		System.out.println(arrayText);
		System.out.println(".. is represented by:");
		System.out.println(array.toString());
		System.out.println(".. and with indentation:");
		System.out.println(array.toString("   "));
		String objectText = "{\"name\":\"Sinterklaas\",\"servants\":[ \"fungy\", \"b\" ]}";
		JsonObject object = JsonConvert.toJsonObject(objectText, factory);
		System.out.println(objectText);
		System.out.println(".. is represented with indentation by:");
		System.out.println(object.toString("   "));
	}
}
