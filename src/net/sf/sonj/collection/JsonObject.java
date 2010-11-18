package net.sf.sonj.collection;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import net.sf.sonj.io.JsonWriteException;
import net.sf.sonj.io.JsonWriter;
import net.sf.sonj.util.JsonConvert;
import net.sf.sonj.util.JsonException;

public class JsonObject implements Map<String, Object> {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> map;

	public JsonObject(Map<String, Object> map) {
		if (map instanceof JsonObject) {
			throw new JsonException("Circular construction");
		}
		this.map = map;
	}

	public JsonObject getJsonObject(String key) {
		return (JsonObject) map.get(key);
	}

	public JsonArray getJsonArray(String key) {
		return (JsonArray) map.get(key);
	}

	public String getString(String key) {
		return (String) map.get(key);
	}

	public int getInt(String key) {
		return ((Integer) map.get(key)).intValue();
	}

	public double getFrac(String key) {
		return ((Double) map.get(key)).doubleValue();
	}

	public boolean getBool(String key) {
		return ((Boolean) map.get(key)).booleanValue();
	}

	public JsonObject getAsJsonObject(String key, JsonFactory factory) {
		return JsonConvert.toJsonObject(map.get(key), factory);
	}

	public JsonArray getAsJsonArray(String key, JsonFactory factory) {
		return JsonConvert.toJsonArray(map.get(key), factory);
	}

	public String getAsString(String key) {
		return JsonConvert.toString(map.get(key));
	}

	public int getAsInt(String key) {
		return JsonConvert.toInt(map.get(key));
	}

	public double getAsFrac(String key) {
		return JsonConvert.toFrac(map.get(key));
	}

	public boolean getAsBool(String key) {
		return JsonConvert.toBool(map.get(key));
	}

	public JsonObject optJsonObject(String key) {
		Object value = map.get(key);
		if (value instanceof JsonObject) {
			return (JsonObject) value;
		}
		return null;
	}

	public JsonArray optJsonArray(String key) {
		Object value = map.get(key);
		if (value instanceof JsonArray) {
			return (JsonArray) value;
		}
		return null;
	}

	public String optString(String key) {
		Object value = map.get(key);
		if (value instanceof String) {
			return (String) value;
		}
		return null;
	}

	public int optInt(String key) {
		Object value = map.get(key);
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}
		return 0;
	}

	public double optFrac(String key) {
		Object value = map.get(key);
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		}
		return 0;
	}

	public boolean optBool(String key) {
		Object value = map.get(key);
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}
		return false;
	}

	public void putAsJsonObject(String key, Object value, JsonFactory factory) {
		map.put(key, JsonConvert.toJsonObject(value, factory));
	}

	public void putAsJsonArray(String key, Object value, JsonFactory factory) {
		map.put(key, JsonConvert.toJsonArray(value, factory));
	}

	public void putAsString(String key, Object value) {
		map.put(key, JsonConvert.toString(value));
	}

	public void putAsInt(String key, Object value) {
		map.put(key, JsonConvert.toInteger(value));
	}

	public void putAsFrac(String key, Object value) {
		map.put(key, JsonConvert.toFraction(value));
	}

	public void putAsBool(String key, Object value) {
		map.put(key, JsonConvert.toBoolean(value));
	}

	@Override
	public String toString() {
		return toString(null);
	}

	public String toString(String indent) {
		StringWriter writer = new StringWriter();
		JsonWriter jsonWriter = new JsonWriter(writer);
		jsonWriter.setIndent(indent);
		try {
			jsonWriter.write(this);
		} catch (JsonWriteException e) {
			writer.write("...");
		}
		return writer.toString();
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Object put(String key, Object value) {
		return map.put(key, value);
	}

	public Object put(String key, int value) {
		return map.put(key, Integer.valueOf(value));
	}

	public Object put(String key, double value) {
		return map.put(key, Double.valueOf(value));
	}

	public Object put(String key, boolean value) {
		return map.put(key, Boolean.valueOf(value));
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection<Object> values() {
		return map.values();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonObject other = (JsonObject) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}
}
