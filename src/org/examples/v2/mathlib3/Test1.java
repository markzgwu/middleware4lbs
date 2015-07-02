package org.examples.v2.mathlib3;

import org.apache.commons.math3.stat.Frequency;

public final class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Frequency f = new Frequency();
		 f.addValue(1);
		 f.addValue(new Integer(1));
		 f.addValue(new Long(1));
		 f.addValue(2);
		 f.addValue(new Integer(-1));
		 System.out.println(f.getCount(1));   // displays 3
		 System.out.println(f.getCumPct(0));  // displays 0.2
		 System.out.println(f.getPct(new Integer(1)));  // displays 0.6
		 System.out.println(f.getCumPct(-2));   // displays 0
		 System.out.println(f.getCumPct(10));  // displays 1
	}

}
