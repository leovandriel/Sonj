package net.sf.sonj.util;

import net.sf.sonj.collection.JsonArray;
import net.sf.sonj.collection.JsonFactory;
import net.sf.sonj.collection.JsonFactory.UtilFactory;
import net.sf.sonj.collection.JsonObject;

public class JsonCompare {
	private static final JsonFactory factory = new JsonFactory(UtilFactory.get());

	public static Object diff(Object a, Object b) {
		if (!a.getClass().equals(b.getClass())) {
			JsonObject result = factory.getJsonObject();
			result.put(a.getClass().getSimpleName(), a);
			result.put(b.getClass().getSimpleName(), b);
			return result;
		}
		if (a instanceof JsonObject) {
			JsonObject result = diff((JsonObject) a, (JsonObject) b);
			if (result.isEmpty()) {
				return null;
			}
			return result;
		}
		if (a instanceof JsonArray) {
			JsonArray result = diff((JsonArray) a, (JsonArray) b);
			if (result.isEmpty()) {
				return null;
			}
			return result;
		}
		return null;
	}

	public static JsonObject diff(JsonObject a, JsonObject b) {
		JsonObject result = factory.getJsonObject();
		for (String key : a.keySet()) {
			if (b.keySet().contains(key)) {
				Object diff = diff(a.get(key), b.get(key));
				if (diff != null) {
					result.put(key, diff);
				}
			} else {
				result.put(key, a.get(key));
			}
		}
		for (String key : b.keySet()) {
			if (!a.keySet().contains(key)) {
				result.put(key, b.get(key));
			}
		}
		return result;
	}

	public static JsonArray diff(JsonArray a, JsonArray b) {
		JsonArray result = factory.getJsonArray();
		for (Object o : a) {
			if (!b.contains(o)) {
				result.add(o);
			}
		}
		for (Object o : b) {
			if (!a.contains(o)) {
				result.add(o);
			}
		}
		return result;
	}
}
