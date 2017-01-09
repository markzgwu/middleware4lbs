package org.tools.forFormat;

import java.util.ArrayList;
import java.util.HashSet;

public final class FormatConvertor {
	static public HashSet<String> convert(final String[] source){
		HashSet<String> result = new HashSet<String>();
		for(String e:source){
			result.add(e);
		}
		return result;
	}
	static public String[] convert(final ArrayList<String> source){
		String[] result = new String[source.size()];
		result = source.toArray(result);
		return result;
	}
}
