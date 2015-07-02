package org.experiments.simqueries;

public final class Test1 {

	public static void main(String[] args) {
		double a=16,b=8;
		double y = Math.pow(4, a)*(Math.pow(10, b)+1);
		System.out.println(y);
		System.out.println(Math.log(y)/Math.log(2));
	}

}
