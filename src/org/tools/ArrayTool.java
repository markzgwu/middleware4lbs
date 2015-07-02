package org.tools;

import java.util.ArrayList;

import org.parameters.I_constant;
import org.tools.forFile.FileTool;

public final class ArrayTool {
	
	public static void main(String[] args) {
		//final String[] keywords = {"饭店","银行","旅游","车站","学校"};
		final String[] keywords = I_constant.keywords;
		System.out.println(printKeywords(keywords));
	}
	
    public static String printKeywords(String[] keywords){
    	String printKeywords = "";
    	for(String k: keywords){
    		printKeywords+=k+";";
    	}
    	return printKeywords;
    }
	
	public static String ArraytoString(ArrayList<String> array_str) {
		final StringBuffer buf = new StringBuffer();
		for(String str:array_str){
			buf.append(str+"\n");
		}
		return buf.toString();
	}
	
	public static void ArraytoFile(String filePath, ArrayList<String> array_str) {
		String buf = ArraytoString(array_str);
		FileTool.saveManyLinestoFile(filePath,buf);
	}	
	
}
