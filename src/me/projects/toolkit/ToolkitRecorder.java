package me.projects.toolkit;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;

import com.alibaba.fastjson.JSON;

public class ToolkitRecorder {
	static public ArrayList<String[]> getRecent(final ArrayList<CRHistory> CRL,final int recent_length){
		//System.out.println("recorder="+JSON.toJSONString(recorder));
		//System.out.println("TargetCRList="+TargetCRList);
		//System.out.println("CRL="+JSON.toJSONString(CRL));
		final int TargetCRL_length = CRL.size();
		if(TargetCRL_length<recent_length){
			System.out.println("Recent is too short! CRL.size()="+CRL.size()+";"+JSON.toJSONString(CRL));
			return null;
		}
		
		final ArrayList<String[]> RecentCRList = new ArrayList<String[]>();
		final int start_pos = TargetCRL_length-recent_length;
		System.out.println("start_pos="+start_pos);
		for(int i=0;i<recent_length;i++){
			final CRHistory CRH = CRL.get(start_pos+i);
			final String[] OneCR = CRH.getCR();
			if(OneCR==null){
				//System.out.println("CR is NULL:CRHistory="+JSON.toJSONString(CRH));
				//System.out.println("CR is NULL:CRHistoryList="+JSON.toJSONString(CRL));
				//System.out.println("CR is NULL:RecentCRList="+JSON.toJSONString(RecentCRList));
				return null;
			}else{
				RecentCRList.add(OneCR);
			}
			
		}
		return RecentCRList;
	}
}
