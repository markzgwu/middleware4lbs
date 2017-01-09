package edu.toyexamples;

import org.apache.commons.math3.ml.distance.EarthMoversDistance;

public class ComputeEMD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EarthMoversDistance EMD = new EarthMoversDistance();
		double[] a = {0.1,0.2};
		double[] b = {0.2,0.1};
		double r = EMD.compute(a, b);
		System.out.println(r);
	}

}
