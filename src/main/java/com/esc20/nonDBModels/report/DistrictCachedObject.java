package com.esc20.nonDBModels.report;

import java.util.HashMap;
import java.util.Map;

import com.esc20.nonDBModels.DatabaseContextHolder;


public abstract class DistrictCachedObject<T> implements IDistrictCachedObject<T> {
	
	private final Map<String, T> _cachedObjects = new HashMap<String, T>();
	
	public final synchronized T getItem() {
		if (!isReady()) {
			throw new RuntimeException("District context is not yet set.");
		}
		if (!_cachedObjects.containsKey(DatabaseContextHolder.getDatabase())) {
			_cachedObjects.put(DatabaseContextHolder.getDatabase(), createItem(DatabaseContextHolder.getDatabase()));
		}
		return _cachedObjects.get(DatabaseContextHolder.getDatabase());
	}
	
	public final boolean isReady() {
		return DatabaseContextHolder.getDatabase() != null;
	}
	
	protected abstract T createItem(String pDatabase);
	
}
