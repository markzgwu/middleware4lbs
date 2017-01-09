package org.tools.forMaths;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class MathTool {
	
	public static double round(double number,double g){
		double tmp = Arith.round_down((number/g),0);
		//double tmp = Arith.round_down(number, g);
		//double round = tmp * g;
		double round = tmp;
		return round;
	}
	
	public static String round(String number,double g){
		double num = Double.parseDouble(number);
		num = round(num,g);
		String r = ""+num;
		return r;
	}	
	
	public static String round(String number){
		int i = number.indexOf(".");
		int j = number.length();
		String str = number;
		if (j>(i+3)) {
			j = i+3;
			str=number.substring(0,j);
		}
		return str;
	}
	
	public static double round(double value, int scale, int roundingMode) {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(scale, roundingMode);
			double d = bd.doubleValue();
			bd = null;
			return d;
	}
	
	public static boolean isInScope(String scope, String value) {
	    Pattern pattern = Pattern.compile("^(\\(|\\[)\\d+,\\s*\\d+(\\)|\\])$");
	    Matcher matcher = pattern.matcher(scope);
	    if (!matcher.find() && StringUtils.isNumeric(value)) {
	      return false;
	    }

	    String[] scopes = scope.split(",");
	    Float valueF = Float.valueOf(value);
	    Float min = Float.valueOf(scopes[0].substring(1));

	    if ("(".equals(String.valueOf(scopes[0].charAt(0)))) {
	      if (valueF <= min)
	        return false;
	    } else if ("[".equals(String.valueOf(scopes[0].charAt(0)))) {
	      if (valueF < min)
	        return false;
	    }

	    Float max = Float.valueOf(scopes[1].substring(0, scopes[1].length() - 1));
	    if (")".equals(String.valueOf(scopes[1].charAt(scopes[1].length() - 1)))) {
	      if (valueF >= max)
	        return false;
	    } else if ("]".equals(String.valueOf(scopes[1].charAt(scopes[1].length() - 1)))) {
	      if (valueF > max)
	        return false;
	    }

	    return true;
	  }	
}
