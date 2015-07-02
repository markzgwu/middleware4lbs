package org.projects.caches;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.constants.Constants;
import org.parameters.I_constant;

import com.alibaba.fastjson.JSON;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public final class CacheEhcache implements ICache {
	private static final Logger logger = Logger.getAnonymousLogger(); 
	

	public CacheEhcache(String ehchachename){
		init(ehchachename);
	}
	
	void init(final String ehcachename){
		//ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//URL url = loader.getResource(I_constant.cache_configfile);
		String filepath = (Constants.getInst().getClassPath()+I_constant.cache_configfile);
		CacheManager manager = CacheManager.create(filepath);
		logger.info(JSON.toJSONString(manager.getCacheNames()));
		cache = manager.getCache(ehcachename);
		logger.info("init cache="+cache+" from "+filepath);
	}
	@Override
	public void clear(){
		cache.removeAll();
	}	
	
	Cache cache = null;
	
	@Override
	public boolean check(String key) {
		return cache.isKeyInCache(key);
	}
	@Override
	public String load(String key) {
		return (String) cache.get(key).getObjectValue();
	}
	@Override
	public boolean save(String key,String value) {
		boolean b = StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value);
		if(b){
			cache.put(new Element(key,value));
		}
		return b;
	} 
}
