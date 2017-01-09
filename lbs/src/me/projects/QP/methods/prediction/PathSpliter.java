package me.projects.QP.methods.prediction;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import me.projects.toolkit.ToolkitString;

public final class PathSpliter {
	
	public final static String[] getSubpathBeforeTail(final String[] onepath){
		final String[] subpath=new String[onepath.length-1];
		for(int i=0;i<onepath.length-1;i++){
			subpath[i]=onepath[i];
		}
		return subpath;
	}
	
	public final static String getTail(final String[] onepath){
		return onepath[onepath.length-1];
	}	

	
	public final static String[] getRealsubpath(final int start_pos,final int subpath_length,final String[] onepath){
		final String[] realsubpath = new String[subpath_length];
		for(int s=0;s<subpath_length;s++){
			realsubpath[s]=onepath[start_pos-subpath_length+s];
		}
		return realsubpath;
	}
	
	public final static String[] getRealsubpath(final int start_pos,final int subpath_length, final ArrayList<CRHistory> CRHistoryList){
		final String[] realsubpath = new String[subpath_length];
		for(int s=0;s<subpath_length;s++){
			realsubpath[s]=CRHistoryList.get(start_pos-subpath_length+s).cell;
		}
		return realsubpath;
	}

	public final static ArrayList<String[]> getCRList(final int start_pos,final int subpath_length, final ArrayList<CRHistory> CRHistoryList){
		final ArrayList<String[]> CRList = new ArrayList<String[]>();
		for(int s=0;s<subpath_length;s++){
			CRList.add(CRHistoryList.get(start_pos-subpath_length+s).CR);
		}
		return CRList;
	}
	
	public static String getPredictionPath(final String testTail,final String[] testSubpath){
		String PredictionPath = "";
		for(String cellid:testSubpath){
			PredictionPath+=cellid+";";
		}
		PredictionPath+="->"+testTail;
		return PredictionPath;
	}
	
	public static final String[] product(final String[] a, final String[] b){
		final String[] c = new String[a.length*b.length];
		int k=0;
		for(int i=0;i<a.length;i++){
			for(int j=0;j<b.length;j++){
				c[k]=a[i]+";"+b[j];
				k++;
			}
		}
		//System.out.println("product a*b:"+JSON.toJSONString(a)+JSON.toJSONString(b));
		//System.out.println("product =c:"+JSON.toJSONString(c));
		return c;
	}	
	
}
