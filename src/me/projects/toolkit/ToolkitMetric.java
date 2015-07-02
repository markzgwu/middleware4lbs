package me.projects.toolkit;

public final class ToolkitMetric {
	public static double getEntropy(double[] Pr){
		double entropy = 0;;
		for(double p:Pr){
			entropy+=p*((-1)*Math.log(p)/Math.log(2));
		}
		return entropy;
	}
	
	public static double getAverage(double[] Pr){
		double average = 0;;
		for(double p:Pr){
			average+=p;
		}
		average = average/Pr.length;
		return average;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
