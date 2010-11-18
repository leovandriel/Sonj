package net.sf.sonj.util;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.sonj.collection.JsonArray;
import net.sf.sonj.collection.JsonFactory;
import net.sf.sonj.collection.JsonObject;
import net.sf.sonj.io.JsonReadException;
import net.sf.sonj.io.JsonReader;

/**
 * Provides static conversion utilities.
 */
public class JsonConvert {
	public static JsonObject toJsonObject(Object object, JsonFactory factory) {
		if (object instanceof JsonObject) {
			return (JsonObject) object;
		}
		if (object instanceof Map<?, ?>) {
			return toJsonObject((Map<?, ?>) object, factory);
		}
		return toJsonObject(toString(object), factory);
	}

	public static JsonObject toJsonObject(Map<?, ?> map, JsonFactory factory) {
		JsonObject jsonObject = factory.getJsonObject();
		for (Entry<?, ?> entry : map.entrySet()) {
			jsonObject.put(toString(entry.getKey()), entry.getValue());
		}
		return jsonObject;
	}

	/**
	 * @throws JsonReadException
	 *             In case the string is not formatted as a json object.
	 */
	public static JsonObject toJsonObject(String string, JsonFactory factory) {
		StringReader reader = new StringReader(string);
		JsonReader jsonReader = new JsonReader(reader, factory);
		return (JsonObject) jsonReader.readMap();
	}

	public static JsonArray toJsonArray(Object object, JsonFactory factory) {
		if (object instanceof JsonArray) {
			return (JsonArray) object;
		}
		if (object instanceof List<?>) {
			return toJsonArray((List<?>) object, factory);
		}
		if (object instanceof Object[]) {
			return toJsonArray((Object[]) object, factory);
		}
		if (object instanceof int[]) {
			return toJsonArray((int[]) object, factory);
		}
		if (object instanceof double[]) {
			return toJsonArray((double[]) object, factory);
		}
		if (object instanceof boolean[]) {
			return toJsonArray((boolean[]) object, factory);
		}
		return toJsonArray(toString(object), factory);
	}

	public static JsonArray toJsonArray(List<?> list, JsonFactory factory) {
		JsonArray JsonArray = factory.getJsonArray();
		for (Object object : list) {
			JsonArray.add(object);
		}
		return JsonArray;
	}

	public static JsonArray toJsonArray(Object[] array, JsonFactory factory) {
		JsonArray JsonArray = factory.getJsonArray();
		for (Object object : array) {
			JsonArray.add(object);
		}
		return JsonArray;
	}

	public static JsonArray toJsonArray(int[] array, JsonFactory factory) {
		JsonArray JsonArray = factory.getJsonArray();
		for (int object : array) {
			JsonArray.add(Integer.valueOf(object));
		}
		return JsonArray;
	}

	public static JsonArray toJsonArray(double[] array, JsonFactory factory) {
		JsonArray JsonArray = factory.getJsonArray();
		for (double object : array) {
			JsonArray.add(Double.valueOf(object));
		}
		return JsonArray;
	}

	public static JsonArray toJsonArray(boolean[] array, JsonFactory factory) {
		JsonArray JsonArray = factory.getJsonArray();
		for (boolean object : array) {
			JsonArray.add(Boolean.valueOf(object));
		}
		return JsonArray;
	}

	/**
	 * @throws JsonReadException
	 *             In case the string is not formatted as a json array.
	 */
	public static JsonArray toJsonArray(String string, JsonFactory factory) {
		StringReader reader = new StringReader(string);
		JsonReader jsonReader = new JsonReader(reader, factory);
		return (JsonArray) jsonReader.readList();
	}

	public static String toString(Object object) {
		if (object instanceof String) {
			return (String) object;
		}
		return object.toString();
	}

	public static Integer toInteger(Object object) {
		if (object instanceof Integer) {
			return (Integer) object;
		}
		if (object instanceof Number) {
			return Integer.valueOf(((Number) object).intValue());
		}
		return Integer.valueOf(toString(object));
	}

	public static int toInt(Object object) {
		if (object instanceof Integer) {
			return ((Integer) object).intValue();
		}
		if (object instanceof Number) {
			return ((Number) object).intValue();
		}
		return Integer.parseInt(toString(object));
	}

	public static Double toFraction(Object object) {
		if (object instanceof Double) {
			return (Double) object;
		}
		if (object instanceof Number) {
			return Double.valueOf(((Number) object).doubleValue());
		}
		return Double.valueOf(toString(object));
	}

	public static double toFrac(Object object) {
		if (object instanceof Double) {
			return ((Double) object).intValue();
		}
		if (object instanceof Number) {
			return ((Number) object).doubleValue();
		}
		return Double.parseDouble(toString(object));
	}

	public static Boolean toBoolean(Object object) {
		if (object instanceof Boolean) {
			return (Boolean) object;
		}
		return Boolean.valueOf(toString(object));
	}

	public static boolean toBool(Object object) {
		if (object instanceof Boolean) {
			return ((Boolean) object).booleanValue();
		}
		return Boolean.parseBoolean(toString(object));
	}
}
