package net.sf.sonj.collection;

import java.util.List;
import java.util.Map;

public interface CollectionFactory {
	public Map<String, Object> getMap();

	public List<Object> getList();
}
