package org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public final class BufferManager
	{
	  private final Ehcache cache;
	  public BufferManager(Ehcache cache)
	  {
	    this.cache = cache;
	  }

	  public boolean check(String key)
	  {
	     return cache.isKeyInCache(key);
	  }
	  
	  /* read some data, check cache first, otherwise read from sor */
	  public CellData read(String key)
	  {
		 CellData value = null;
	     Element element;
	     if ((element = cache.get(key)) != null) {
	    	 value = (CellData) element.getObjectValue();
	     }
	     return value;
	  }
	  
	  /* write some data, write to sor, then update cache */
	  public void write(String key, CellData value)
	  {
	    // writeDataToDataStore(key, value);
	     cache.put(new Element(key, value));
	  }

}
