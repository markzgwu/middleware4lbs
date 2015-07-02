package org.zgwu4lab.lbsn.trajectory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

import org.examples.v1.ntru.ntru_sign;
import org.tools.ByteTool;

public final class PST {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	//检查完整性
	public String Check(String[] A) throws NoSuchAlgorithmException{
		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
		String s = "";
		for(int i=0;i<A.length;i++){
			s+=A[i]+"#";
		}
	    digest.update(s.getBytes());
	    byte[] hash = digest.digest();
	    String r = new String(ByteTool.byte2HexStr(hash));
	    return r;
	}
	
	public String[] FromStringArrayToString(ArrayList<String> A){
		String[] a = new String[A.size()];
		a = A.toArray(a);
		return a;
	}
	
	public int Intersection_size(String[]A, String[]B) {
		// TODO Auto-generated method stub
		int count=0;
		HashSet<String> hs = new HashSet<String>();
		for(int i = 0;i<A.length;i++){
			hs.add(A[i]);
		}
		for(int j = 0;j<B.length;j++){
			if(hs.contains(B[j])){
				count++;
			}
		}
		return count;
	}
	
	ntru_sign ntru = new ntru_sign();
	PST(){
		ntru.sign("test");
	}
	public float Similarity(String[]A, String[]B) {
		///*
		
		try {
			String a1 = Check(A);
			System.out.println(a1);
			String b1 = Check(B);
			System.out.println(b1);
			
			//ntru.sign(a1);
			//ntru.sign(b1);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		//*/
		
		// TODO Auto-generated method stub
		float intersection_size = Intersection_size(A,B);
		float similarity = intersection_size / (A.length + B.length - intersection_size);
		return similarity;
	}

}
