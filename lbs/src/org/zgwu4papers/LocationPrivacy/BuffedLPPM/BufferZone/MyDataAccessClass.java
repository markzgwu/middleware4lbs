package org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone;

import net.sf.ehcache.*;

public class MyDataAccessClass
{
  private final Ehcache cache;
  public MyDataAccessClass(Ehcache cache)
  {
    this.cache = cache;
  }

  /* read some data, check cache first, otherwise read from sor */
  public String readSomeData(String key)
  {
	 String value = null;
     Element element;
     if ((element = cache.get(key)) != null) {
    	 value = (String) element.getObjectValue();
     }
      // note here you should decide whether your cache
     // will cache 'nulls' or not
     //if (value = readDataFromDataStore(key)) != null) {
     //    cache.put(new Element(key, value));
     //}
     
     return value;
  }
  /* write some data, write to sor, then update cache */
  public void writeSomeData(String key, String value)
  {
    // writeDataToDataStore(key, value);
     cache.put(new Element(key, value));
  }
}
