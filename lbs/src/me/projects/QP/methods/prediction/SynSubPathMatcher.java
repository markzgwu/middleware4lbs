package me.projects.QP.methods.prediction;

import java.util.HashMap;

import me.projects.toolkit.ToolkitString;

public final class SynSubPathMatcher{
	int check=0;
	public void check(final String[] subpath,final String[] onepath){
		if(subpath.length>onepath.length-1){
			check++;
			String msg = "subpath:("+subpath.length+")"+ToolkitString.output(subpath);
			msg += "onepath:("+onepath.length+")"+ToolkitString.output(onepath);
			System.out.println("WARN:"+msg);
		}
	}
	
	public int countSubpathAndTail(final String[] subpath,final String onetail,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			final String[] onepath = allpaths.get(ObjectID);
			//check(subpath, onepath);
			if(isSubpathAndTail(subpath,onetail,onepath)){
				n++;
			}
		}
		return n;
	}
	
	public int countTail(final String onetail,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			final String[] onepath = allpaths.get(ObjectID);
			if(isTail(onetail,onepath)){
				n++;
			}
		}
		return n;
	}
	
	public int countSubpath(final String[] subpath,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			String[] onepath = allpaths.get(ObjectID);
			if(isSubpath(subpath, onepath)){
				n++;
			}
		}
		return n;
	}	
	
	boolean isTail(final String onetail,final String[] onepath){
		return onetail.equals(splittail(onepath));
	}
	
	public boolean isMatch(final String[] subpath,final String[] onepath){
		final String strOnepath=ToolkitString.ShowStringArray(onepath);
		final String strSubpath=ToolkitString.ShowStringArray(subpath);
		//System.out.println(strOnepath+"\n"+strSubpath);
		//System.out.println("strOnepath="+strOnepath);
		return strOnepath.equals(strSubpath);
	}
	
	public boolean isSubpathAndTail(final String[] subpath,final String onetail,final String[] onepath){
		return isTail(onetail,onepath) && isSubpath(subpath,onepath);
	}
	
	//history是否是轨迹的一部分
	public boolean isSubpath(final String[] subpath,final String[] onepath){
		return isMatch(subpath,splithistory(onepath));
	}
	
	public String[] splithistory(String[] onepath){
		final int splitlength = onepath.length-1;
		final String[] historypath = new String[splitlength];
		for(int i=0;i<splitlength;i++){
			historypath[i] = onepath[i];
		}
		return historypath;
	}
	
	public String splittail(String[] onepath){
		return onepath[onepath.length-1];
	}
	
	public static void main(String[] args) {
		String[] onepath={"C0000","C0001","C0002"};
		String Xf = "C0002";
		SynSubPathMatcher a = new SynSubPathMatcher();
		//String[] History = {"C0002"};
		System.out.println(a.isSubpath(new String[]{"C0000"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0001"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0002"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0001","C0002"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0000","C0001"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0000","C0001","C0002"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0000","C0002"},onepath));
		System.out.println();
		System.out.println(a.isSubpathAndTail(new String[]{"C0000","C0001"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0000"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0001"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0002"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0003"},Xf,onepath));
		System.out.println();

	}
}
