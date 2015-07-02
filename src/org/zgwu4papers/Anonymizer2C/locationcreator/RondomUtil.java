package org.zgwu4papers.Anonymizer2C.locationcreator;

import java.util.Random;

public class RondomUtil {
	public static int random(int limit){
		Random r = new Random();
		int a = Math.abs(r.nextInt())%limit;
		return a; 
	}
	
	public static void main(String[] args) {
		System.out.println(random(100));
		
	}

}
