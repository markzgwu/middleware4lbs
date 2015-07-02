package org.zgwu4lab.lbsn.trajectory;

import java.util.ArrayList;

public final class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String output = "";
		for(int i = 1;i<=20;i++){
			long time = test(i);
			output += time+",";
		}
		System.out.println(output);
	}

	static PST pst = new PST();
	
	public static long test(int size){
		
		String seed = "X";
		ArrayList<String> A = new ArrayList<String>();
		ArrayList<String> B = new ArrayList<String>();
		int size1 = size * 500;
		for(int i = 0;i<size1;i++){
			A.add(seed+i);
		}
		for(int i = 500;i<(500+size1);i++){
			B.add(seed+i);
		}
		
		
		
		String[] a = pst.FromStringArrayToString(A);
		a = A.toArray(a);
		String[] b = pst.FromStringArrayToString(B);
		
		
		
		long time1 = System.currentTimeMillis();
		float sim = pst.Similarity(a, b);
		long time2 = System.currentTimeMillis();
		System.out.println(sim);
		long time = (time2-time1);
		//System.out.println(time1);
		//System.out.println(time2);
		System.out.println(time+" ms");
		return time;
	}
	
}
