package org.projects.caches;

public class CacheSimple implements ICache{
	@Override
	public String load(String cellid){
		String dataInBuffer = "Data("+cellid+") Buffer";
		return dataInBuffer;
	}
	@Override
	public boolean save(String cellid,String data){
		//System.out.println("saving "+cellid);
		return true;
	}

	@Override
	public boolean check(String key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
