package org.examples.v2.mathlib3;

import org.apache.commons.math3.random.RandomDataGenerator;;

public class Test2 {

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			RandomDataGenerator randomData = new RandomDataGenerator(); 
		    long value = randomData.nextLong(1, 1000000);
		    System.out.println(value);
		}
	}

}
