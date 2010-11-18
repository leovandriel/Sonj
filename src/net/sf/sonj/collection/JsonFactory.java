package net.sf.sonj.collection;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.sonj.io.JsonReadException;

public class JsonFactory implements CollectionFactory {
	private CollectionFactory baseFactory;

	public static class UtilFactory implements CollectionFactory {
		private static JsonFactory singleton;

		public List<Object> getList() {
			return new LinkedList<Object>();
		}

		public Map<String, Object> getMap() {
			return new LinkedHashMap<String, Object>();
		}

		public static JsonFactory get() {
			if (singleton == null) {
				singleton = new JsonFactory(new UtilFactory());
			}
			return singleton;
		}
	}

	/**
	 * 
	 * @param baseFactory
	 *            A factory that does <em>not</em> return json objects and
	 *            arrays.
	 * @throws JsonException
	 *             In case the given factory is a json factory.
	 */
	public JsonFactory(CollectionFactory baseFactory) {
		if (baseFactory instanceof JsonFactory) {
			throw new JsonReadException("Circular construction");
		}
		this.baseFactory = baseFactory;
	}

	/**
	 * @throws JsonException
	 *             In case the base factory return json arrays.
	 */
	public JsonArray getJsonArray() {
		return new JsonArray(baseFactory.getList());
	}

	/**
	 * @throws JsonException
	 *             In case the base factory return json objects.
	 */
	public JsonObject getJsonObject() {
		return new JsonObject(baseFactory.getMap());
	}

	/**
	 * @throws JsonException
	 *             In case the base factory return json arrays.
	 */
	public List<Object> getList() {
		return getJsonArray();
	}

	/**
	 * @throws JsonException
	 *             In case the base factory return json objects.
	 */
	public Map<String, Object> getMap() {
		return getJsonObject();
	}
}
