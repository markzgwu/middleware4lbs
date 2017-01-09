package org.tools;

public class ByteTool {
	public static String byte2HexStrUpperCase(byte[] b){
        return byte2HexStr(b).toUpperCase();
	}	
	
	public static String byte2HexStr(byte[] b){
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < b.length; i++) {  
	            String s = Integer.toHexString(b[i] & 0xFF);  
	            if (s.length() == 1) {  
	                sb.append("0");  
	            }
	            //sb.append(s.toUpperCase());
	            sb.append(s);
	        }
	        String r = sb.toString();
	        sb = null;
	        return r;
	 }
}
