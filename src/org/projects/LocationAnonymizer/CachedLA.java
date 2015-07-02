package org.projects.LocationAnonymizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.projects.caches.ICache;
import org.projects.privacymodel.PrivacyProfile;

public final class CachedLA implements ILocationAnonymizer{
	
	final QuadTreeCounter qtcounter;
	final ICache cache;
	public CachedLA(QuadTreeCounter qtcounter,ICache cachezone) {
		super();
		this.qtcounter = qtcounter;
		this.cache = cachezone;
	}

	Cell findLeafCell(String cid){
		Cell c = new Cell();
		c.cid = cid;
		c.N = qtcounter.get(cid);
		//c.Area = MathTool4Integer.pow(4, qtcounter.rectenv.getLevel().level-cid.length());
		c.Area = 1;
		return c;
	}
	
	final int cachesize = 100;//需要和实际的cache一致
	ArrayList<String> BasicLA_v1(int k,int Amin,String cid){
		Cell cell = findLeafCell(cid);
		//String[] area = null;
		ArrayList<String> area =  new ArrayList<String>();
		
		area.add(cid);
		int N = cell.N;
		int Area = cell.Area;
		
		boolean b = (N>=k) && (Area>= Amin);
		
		if(b){
			
		}else{
			
			for(int i=1;i<cachesize;i++){
				final Entry<String, Integer> selective_cell = qtcounter.findmostfreq(cache);
				if(StringUtils.isNotBlank(selective_cell.getKey())){
					area.add(selective_cell.getKey());
					N += selective_cell.getValue();
					Area += 1;
					b = (N>=k) && (Area>= Amin);
					if(b){
						break;
					}
				}else{
					return null;
				}
				
			}
		}


		return area;
	}
	ArrayList<String> BasicLA_v2(PrivacyProfile privacyprofile,String cid){
		int k = privacyprofile.k;
		int Amin = privacyprofile.A;
		
		Cell cell = findLeafCell(cid);
		//String[] area = null;
		ArrayList<String> area =  new ArrayList<String>();
		
		area.add(cid);
		int N = cell.N;
		int Area = cell.Area;
		
		boolean b = (N>=k) && (Area>= Amin);
		
		if(b){
			
		}else{
			final List<Entry<String, Integer>> selective_cells = qtcounter.findmostfreq(cache,cachesize);
			//final List<Entry<String, Integer>> selective_cells = qtcounter.findrondom(cache,cachesize);
			for(Entry<String, Integer> selective_cell:selective_cells){
				
				if(StringUtils.isNotBlank(selective_cell.getKey())){
					area.add(selective_cell.getKey());
					N += selective_cell.getValue();
					Area += 1;
					b = ( (N>=k) && (Area>= Amin) ) || (Area>cachesize);
					if(b){
						break;
					}
				}else{
					return null;
				}
				
			}
		}


		return area;
	}		
	
	HashSet<String> Area(String cid){
		HashSet<String> area = new HashSet<String>();
		area.add(cid);
		return area;
	}
	
	String Parent(String cid){
		String parent = cid.substring(0, cid.length()-1);
		//System.out.println(parent);
		return parent;
	}

	@Override
	public ArrayList<String> anonymization(PrivacyProfile privacyprofile, String cid) {
		// TODO Auto-generated method stub
		return BasicLA_v2(privacyprofile,cid);
	}
}
