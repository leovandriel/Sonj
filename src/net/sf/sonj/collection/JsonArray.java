package net.sf.sonj.collection;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.sonj.io.JsonWriteException;
import net.sf.sonj.io.JsonWriter;
import net.sf.sonj.util.JsonConvert;
import net.sf.sonj.util.JsonException;

public class JsonArray implements List<Object> {
	private static final long serialVersionUID = 1L;
	private List<Object> list;

	/**
	 * 
	 * @param list
	 * @throws JsonException
	 *             In case the given list is a json array.
	 */
	public JsonArray(List<Object> list) {
		if (list instanceof JsonArray) {
			throw new JsonException("Circular construction");
		}
		this.list = list;
	}

	public JsonObject getJsonObject(int index) {
		return (JsonObject) list.get(index);
	}

	public JsonArray getJsonArray(int index) {
		return (JsonArray) list.get(index);
	}

	public String getString(int index) {
		return (String) list.get(index);
	}

	public int getInt(int index) {
		return ((Integer) list.get(index)).intValue();
	}

	public double getFrac(int index) {
		return ((Double) list.get(index)).doubleValue();
	}

	public boolean getBool(int index) {
		return ((Boolean) list.get(index)).booleanValue();
	}

	public JsonObject getAsJsonObject(int index, JsonFactory factory) {
		return JsonConvert.toJsonObject(list.get(index), factory);
	}

	public JsonArray getAsJsonArray(int index, JsonFactory factory) {
		return JsonConvert.toJsonArray(list.get(index), factory);
	}

	public String getAsString(int index) {
		return JsonConvert.toString(list.get(index));
	}

	public int getAsInt(int index) {
		return JsonConvert.toInt(list.get(index));
	}

	public double getAsFrac(int index) {
		return JsonConvert.toFrac(list.get(index));
	}

	public boolean getAsBool(int index) {
		return JsonConvert.toBool(list.get(index));
	}

	public JsonObject optJsonObject(int index) {
		Object value = list.get(index);
		if (value instanceof JsonObject) {
			return (JsonObject) value;
		}
		return null;
	}

	public JsonArray optJsonArray(int index) {
		Object value = list.get(index);
		if (value instanceof JsonArray) {
			return (JsonArray) value;
		}
		return null;
	}

	public String optString(int index) {
		Object value = list.get(index);
		if (value instanceof String) {
			return (String) value;
		}
		return null;
	}

	public int optInt(int index) {
		Object value = list.get(index);
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}
		return 0;
	}

	public double optFrac(int index) {
		Object value = list.get(index);
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		}
		return 0;
	}

	public boolean optBool(int index) {
		Object value = list.get(index);
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}
		return false;
	}

	public void addAsJsonObject(Object value, JsonFactory factory) {
		list.add(JsonConvert.toJsonObject(value, factory));
	}

	public void addAsJsonArray(Object value, JsonFactory factory) {
		list.add(JsonConvert.toJsonArray(value, factory));
	}

	public void addAsString(Object value) {
		list.add(JsonConvert.toString(value));
	}

	public void addAsInt(Object value) {
		list.add(JsonConvert.toInteger(value));
	}

	public void addAsDouble(Object value) {
		list.add(JsonConvert.toFraction(value));
	}

	public void addAsBool(Object value) {
		list.add(JsonConvert.toBoolean(value));
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

	public Object get(int index) {
		return list.get(index);
	}

	public boolean add(Object value) {
		return list.add(value);
	}

	public boolean add(int value) {
		return list.add(Integer.valueOf(value));
	}

	public boolean add(double value) {
		return list.add(Double.valueOf(value));
	}

	public boolean add(boolean value) {
		return list.add(Boolean.valueOf(value));
	}

	public Iterator<Object> iterator() {
		return list.iterator();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean addAll(Collection<? extends Object> c) {
		return list.addAll(c);
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean remove(Object o) {
		return list.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public int size() {
		return list.size();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public void add(int index, Object element) {
		list.add(index, element);
	}

	public boolean addAll(int index, Collection<? extends Object> c) {
		return list.addAll(index, c);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<Object> listIterator() {
		return list.listIterator();
	}

	public ListIterator<Object> listIterator(int index) {
		return list.listIterator(index);
	}

	public Object remove(int index) {
		return list.remove(index);
	}

	public Object set(int index, Object element) {
		return list.set(index, element);
	}

	public List<Object> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
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
		JsonArray other = (JsonArray) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}
}
