package org.projects.datamodel;

import org.tools.forLog.ILog;

public final class Coder implements ILog{
	static public String encode(final String bitXY){
		String output = null;
		//xy 00 01 10 11
		switch(bitXY){
		case "00":
			output = "0";
			break;
		case "01":
			output = "1";
			break;
		case "10":
			output = "3";
			break;
		case "11":
			output = "2";
			break;			
		}
		return output;
	}
	static public String decode(final String num){
		String output = null;
		//xy 00 01 10 11
		//logger.info(num);
		switch(num){
		case "0":
			output = "00";
			break;
		case "1":
			output = "01";
			break;
		case "2":
			output = "11";
			break;
		case "3":
			output = "10";
			break;			
		}
		return output;
	}
}
