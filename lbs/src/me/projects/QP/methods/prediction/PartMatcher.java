package me.projects.QP.methods.prediction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.projects.toolkit.ToolkitString;

public class PartMatcher extends AbsMatcher {
	
	//cellid1 是否是 轨迹中经过的位置
	public int isPast(String cellid1, final String[] onepath){
		int r = 0;
		for(int i=0;i<onepath.length;i++){
			if(cellid1.equals(onepath[i])){
				r=1;
				break;
			}
		}
		return r;
	}

	boolean isOversize(final String[] History,final String[] onepath){
		//超长
		//System.out.println(History.length+";"+onepath.length);
		return History.length>(onepath.length-1);
	}
	
	boolean isTail(final String Xf,final String[] onepath){
		return Xf.equals(onepath[onepath.length-1]);
	}
	
	String getRegEx(final String[] History){
		String strRegEx = "";
		for(String a:History){
			strRegEx+=a+"[0-9|;]*";
		}
		return strRegEx;
	}
	
	public boolean isRegExMatch(final String[] History,final String[] onepath){
		String strOnepath=ToolkitString.ShowStringArray(onepath);
		String regEx=getRegEx(History); //表示a或f 
		Pattern p=Pattern.compile(regEx);
		Matcher m=p.matcher(strOnepath);
		boolean result=m.find();
		//System.out.println(result);
		System.out.println(strOnepath+"\n"+regEx);
		return result;
	}
	
	public boolean isMatch(final String[] History,final String[] onepath){
		String strOnepath=ToolkitString.ShowStringArray(onepath);
		String strHistory=ToolkitString.ShowStringArray(History);
		return strOnepath.contains(strHistory);
	}
	
	public int isHistoryAndCellid(final String[] History,final String Xf,final String[] onepath){
		int r = 0;
		if(isOversize(History,onepath)){
			//超长
			//System.out.println("isOversize!"+History.length+";"+onepath.length);
			return 0;
		}
		
		if(!isTail(Xf,onepath)){
			//System.out.println("isNotTail");
			return 0;
		}
		
		r = isHistory(History,onepath);

		return r;
	}
	
	//history是否是轨迹的一部分
	public int isHistory(final String[] History,final String[] onepath){
		int r = 0;
		
		boolean b = isMatch(History,onepath);
		if(b){
			r=1;
		}

		return r;
	}
	
	public static void main(String[] args) {
		String[] onepath={"C0000","C0001","C0002"};
		String Xf = "C0002";
		AbsMatcher a = new PartMatcher();
		//String[] History = {"C0002"};
		System.out.println(a.isHistory(new String[]{"C0000"},onepath));
		System.out.println(a.isHistory(new String[]{"C0001"},onepath));
		System.out.println(a.isHistory(new String[]{"C0002"},onepath));
		System.out.println(a.isHistory(new String[]{"C0001","C0002"},onepath));
		System.out.println(a.isHistory(new String[]{"C0000","C0001"},onepath));
		System.out.println(a.isHistory(new String[]{"C0000","C0001","C0002"},onepath));
		System.out.println(a.isHistory(new String[]{"C0000","C0002"},onepath));
		System.out.println();
		System.out.println(a.isHistoryAndCellid(new String[]{"C0000","C0001"},Xf,onepath));
		System.out.println(a.isHistoryAndCellid(new String[]{"C0000"},Xf,onepath));
		System.out.println(a.isHistoryAndCellid(new String[]{"C0001"},Xf,onepath));
		System.out.println(a.isHistoryAndCellid(new String[]{"C0002"},Xf,onepath));
		System.out.println(a.isHistoryAndCellid(new String[]{"C0003"},Xf,onepath));
		System.out.println();
		System.out.println(a.isRegExMatch(new String[]{"C0000","C0001","C0002"},onepath));
		System.out.println(a.isRegExMatch(new String[]{"C0000","C0001"},onepath));
		System.out.println(a.isRegExMatch(new String[]{"C0000","C0002"},onepath));

	}

}
