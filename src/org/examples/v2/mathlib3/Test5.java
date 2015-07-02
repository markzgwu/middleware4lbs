package org.examples.v2.mathlib3;

import org.apache.commons.math3.stat.Frequency;

public class Test5 {

	public static void test(){
		
		 Frequency f = new Frequency();
		 for(int i=0;i<10;i++){
			 f.addValue(1);
		 }

		 System.out.println(f.getCount(1));   // displays 3
		 System.out.println(f.getCumPct(0));  // displays 0.2
		 System.out.println(f.getPct(1));  // displays 0.6
		 System.out.println(f.getCumPct(-2));   // displays 0
		 System.out.println(f.getCumPct(10));  // displays 1
		 System.out.println(f.getCumFreq(1));
		
	}
	
	public static void main(String[] args) {
		test();

	}

}
