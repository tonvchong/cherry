package com.tonvchong.core.cache;

import java.util.Date;

public interface ICacheManager {
	public void init();

	public void put(String key, Object obj);

	public void remove(String key);

	public boolean exist(String key);

	public void put(String key, Object obj, Date expiry);

	public void destroy();

	public Object get(String key);
}
