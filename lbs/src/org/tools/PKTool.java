package org.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PKTool {
	
	public static String PK(String str){
		return PK_md5(str);
		//return PK_sha1(str);
		//return PK_hashcode(str);
	}
	
	public static String PK_md5(String str){
		return PK_digest(str,"md5");
	}
	
	public static String PK_sha1(String str){
		return PK_digest(str,"SHA-1");
	}
	
	private static String PK_digest(String str,String algname){
		String pk = null;
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance(algname);
			//digest.update(str.getBytes());
			//byte[] hash = digest.digest();
		    byte[] hash = digest.digest(str.getBytes());
		    pk = (ByteTool.byte2HexStr(hash));
		    digest = null;
		    hash = null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pk;
	}
	
	public static String PK_hashcode(String str){
		return String.valueOf(str.hashCode());
	}	
	
	public static void main(String[] args) {
		System.out.println(PK("123123123"));
		System.out.println(PK(new String("123123123")));

	}

}
