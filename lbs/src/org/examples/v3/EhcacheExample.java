package org.examples.v3;

import java.net.URL;
import java.util.logging.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public final class EhcacheExample {
	private static final Logger logger = Logger.getAnonymousLogger(); 
	public static void main(String[] args) {
		logger.info("info");
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("ehcache1.xml");
		CacheManager manager = CacheManager.create(url);
		
		//CacheManager manager = CacheManager.getInstance();  
		String names[] = manager.getCacheNames();
		for(int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
		}
		Cache cache = manager.getCache(names[0]);
		cache.put(new Element("name", "admin"));
		System.out.println(cache.get("name").getObjectValue());

	}

}
