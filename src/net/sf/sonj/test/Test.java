package net.sf.sonj.test;

import net.sf.sonj.collection.JsonFactory;
import net.sf.sonj.collection.JsonFactory.UtilFactory;
import net.sf.sonj.collection.JsonObject;

public class Test {
	private JsonFactory factory = UtilFactory.get();

	public static void main(String[] args) {
		new Test().run();
	}

	public void run() {
		test1();
	}

	private void test1() {
		JsonObject object = factory.getJsonObject();
		object.put("i", 10001);
		int i = object.getAsInt("i");
		System.out.println(i);
		object.putAsInt("s", "10001");
		int s = object.getAsInt("s");
		System.out.println(s);
	}
}
