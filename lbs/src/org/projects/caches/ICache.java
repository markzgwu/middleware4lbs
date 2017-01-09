package org.projects.caches;

public interface ICache {
	
	public boolean check(String key);
	public String load(String key);
	public boolean save(String key,String value);
	public void clear();
}
