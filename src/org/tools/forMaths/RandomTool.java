package org.tools.forMaths;

import java.util.Random;

public final class RandomTool {

	final public static Random random = new Random(System.currentTimeMillis());
	public static double RandomDouble(){
		return random.nextDouble();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
