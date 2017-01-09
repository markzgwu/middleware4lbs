package me.projects.QP.methods.prediction;

import java.util.ArrayList;

import org.parameters.I_constant;

import com.alibaba.fastjson.JSONObject;

public final class CRSequence {

	public CRSequence(final ArrayList<String[]> CloakingRegionList){
		this.CloakingRegionList = CloakingRegionList;
	}
	
	public final ArrayList<String[]> CloakingRegionList;
	
	public CRSequence putnew(final ArrayList<String> CloakingRegion){
		if(CloakingRegion==null){
			return null;
		}else{
			
		}
		final String[] ArrayCR = new String[CloakingRegion.size()];
		for(int i=0;i<CloakingRegion.size();i++){
			ArrayCR[i] = CloakingRegion.get(i);
		}
		return putnew(ArrayCR);
	}	
	
	public CRSequence putnew(final String[] CloakingRegion){
		if(CloakingRegion==null){
			return null;
		}
		
		final ArrayList<String[]> newCloakingRegionList = new ArrayList<String[]>(CloakingRegionList);
		newCloakingRegionList.add(CloakingRegion);
		if(newCloakingRegionList.size()>I_constant.subpath_length){
			newCloakingRegionList.remove(0);
		}
		return new CRSequence(newCloakingRegionList);
	}
	
	String[] decompose(){
		String[] subpathlist = CloakingRegionList.get(0);
		final int CRLLength = CloakingRegionList.size();
		if(CRLLength<I_constant.subpath_length){
			return subpathlist;
		}
		
		for(int i=1;i<I_constant.subpath_length;i++){
			subpathlist = PathSpliter.product(subpathlist,CloakingRegionList.get(i));
		}

		return subpathlist;
	}
	
	public static void main(String[] args) {
		
		{
		ArrayList<String[]> CloakingRegionList = new ArrayList<String[]>();
		CloakingRegionList.add(new String[]{"a","b"});
		CloakingRegionList.add(new String[]{"a","b"});
		CRSequence crc = new CRSequence(CloakingRegionList);
		System.out.println(JSONObject.toJSONString(crc.decompose()));
		}
		
		{
		ArrayList<String[]> CloakingRegionList = new ArrayList<String[]>();
		CloakingRegionList.add(new String[]{"a"});
		CloakingRegionList.add(new String[]{"a","b"});
		CloakingRegionList.add(new String[]{"a","b","c"});
		CRSequence crc = new CRSequence(CloakingRegionList);
		System.out.println(JSONObject.toJSONString(crc.decompose()));
		}
		
		{
		ArrayList<String[]> CloakingRegionList = new ArrayList<String[]>();
		CloakingRegionList.add(new String[]{"a","b","c"});
		CloakingRegionList.add(new String[]{"a","b"});
		CloakingRegionList.add(new String[]{"a"});
		CRSequence crc = new CRSequence(CloakingRegionList);
		System.out.println(JSONObject.toJSONString(crc.decompose()));
		}		

	}

}
