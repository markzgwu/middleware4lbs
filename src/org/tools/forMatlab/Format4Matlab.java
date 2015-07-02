package org.tools.forMatlab;

import org.tools.forString.StringTool;

public final class Format4Matlab {
	public static String output4matlab(String arg,String content){
		String value = StringTool.replaceLastChat(arg+"=["+content, "];");
		return value;
	}
	public static String format(String arg,String content){
		String value = StringTool.replaceLastChat(arg+"=["+content, "];");
		return value;
	}	
}
