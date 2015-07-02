package org.projects.LocationAnonymizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.projects.caches.ICache;
import org.projects.datamodel.RectEnv;

import com.alibaba.fastjson.JSON;

public final class QuadTreeCounter {
	final ArrayList<HashMap<String,Integer>> qtcounter= new ArrayList<HashMap<String,Integer>>();
	public final RectEnv rectenv;
	
	public QuadTreeCounter(RectEnv rectenv){
		this.rectenv = rectenv;
		this.initqtcounter();
	}
	
	public void initqtcounter(){
		for(int i=0;i<rectenv.getLevel().level;i++){
			qtcounter.add(new HashMap<String,Integer>());
		}
	}
	
	boolean check(String cellid){
		return StringUtil.isNotBlank(cellid);
	}
	
	public void putall(String cellid){
		
		if(cellid.length()<2){
			return;
		}
		for(int i=1;i<cellid.length();i++){
			String prefixcellid = cellid.substring(0,i);
			put(prefixcellid);
			//System.out.println(prefixcellid);
		}
		put(cellid);
	}
	
	public void put(String cellid){
		if(check(cellid)){
			int level = cellid.length()-1 ;
			put(qtcounter.get(level),cellid);
		}
	}
	
	void put(HashMap<String,Integer> hm,String cellid){
		if(hm.containsKey(cellid)){
			hm.put(cellid, hm.get(cellid)+1);
		}else{
			hm.put(cellid,1);
		}
	}
	
	public int get(String cellid){
		int level = cellid.length()-1 ;
		//System.out.println(level);
		//System.out.println(qtcounter.get(level));
		//System.out.println(cellid);
		int result = 0;
		if(qtcounter.get(level).containsKey(cellid)){
			result = qtcounter.get(level).get(cellid);
		}
		return result;
	}

	public List<Map.Entry<String, Integer>> findrondom(ICache cache,int cachesize){
		final int level = this.rectenv.getLevel().level;
		final HashMap<String,Integer> leafcounter = qtcounter.get(level-1);

		//≈≈–Ú
		List<Map.Entry<String, Integer>> infoIds =
			    new ArrayList<Map.Entry<String, Integer>>(leafcounter.entrySet());

		List<Map.Entry<String, Integer>> selective_cid = new ArrayList<Map.Entry<String, Integer>>();
		for(Map.Entry<String, Integer> e:infoIds){
			String cid = e.getKey();
			if(!cache.check(cid)){
				selective_cid.add(e);
				if(selective_cid.size()>cachesize){
					break;
				}
			}
		}
		
		/*
		for(String cid:leafcounter.keySet()){
			if(StringUtils.isBlank(selective_cid)){
				selective_cid = cid;
				counter = leafcounter.get(selective_cid);
			}else{
				
			}
		}
		*/
		return selective_cid;
	}		
	
	public List<Map.Entry<String, Integer>> findmostfreq(ICache cache,int cachesize){
		final int level = this.rectenv.getLevel().level;
		final HashMap<String,Integer> leafcounter = qtcounter.get(level-1);

		//≈≈–Ú
		List<Map.Entry<String, Integer>> infoIds =
			    new ArrayList<Map.Entry<String, Integer>>(leafcounter.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        //return (o2.getValue() - o1.getValue()); 
		        return (o1.getValue()).compareTo(o2.getValue());
		    }
		});
		List<Map.Entry<String, Integer>> selective_cid = new ArrayList<Map.Entry<String, Integer>>();
		for(Map.Entry<String, Integer> e:infoIds){
			String cid = e.getKey();
			if(!cache.check(cid)){
				selective_cid.add(e);
				if(selective_cid.size()>cachesize){
					break;
				}
			}
		}
		
		
		/*
		for(String cid:leafcounter.keySet()){
			if(StringUtils.isBlank(selective_cid)){
				selective_cid = cid;
				counter = leafcounter.get(selective_cid);
			}else{
				
			}
		}
		*/
		return selective_cid;
	}	
	
	public Map.Entry<String, Integer> findmostfreq(ICache cache){
		final int level = this.rectenv.getLevel().level;
		final HashMap<String,Integer> leafcounter = qtcounter.get(level-1);

		//≈≈–Ú
		List<Map.Entry<String, Integer>> infoIds =
			    new ArrayList<Map.Entry<String, Integer>>(leafcounter.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        //return (o2.getValue() - o1.getValue()); 
		        return (o1.getValue()).compareTo(o2.getValue());
		    }
		});
		Map.Entry<String, Integer> selective_cid = null;
		for(Map.Entry<String, Integer> e:infoIds){
			String cid = e.getKey();
			if(!cache.check(cid)){
				selective_cid = e;
				break;
			}
		}
		/*
		for(String cid:leafcounter.keySet()){
			if(StringUtils.isBlank(selective_cid)){
				selective_cid = cid;
				counter = leafcounter.get(selective_cid);
			}else{
				
			}
		}
		*/
		return selective_cid;
	}	
	
	public String summary(){
		String summary="";
		for(HashMap<String,Integer> counter :qtcounter){
			summary+=JSON.toJSONString(counter)+"\n";
		}
		return summary;
	}
}
