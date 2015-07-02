package org.assistants.poidb;

public final class AdvisorPartition {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double area = 16000;
		double g_area=10/(double)(1000*1000);
		double ratio = area/g_area;
		double length = Math.log10(ratio)/Math.log10(4);
		System.out.println(ratio+";"+length);
		double h = 16;
		double g = area*1000*1000/Math.pow(4, h);
		System.out.println(h+";"+g);
	}

}
