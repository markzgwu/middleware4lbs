package org.projects.queries.complex;

import java.util.HashSet;

public final class SetSimilarity {

	HashSet<String> SetA,SetB;
	public SetSimilarity(HashSet<String> A,HashSet<String> B){
		this.SetA = A;
		this.SetB = B;
		System.out.println("(SetA,SetB)=("+ SetA.size()+","+ SetB.size()+")");
	}
	
	public double getJI(){
		double value1 = intersection().size();
		double value2 = unionsection().size();
		double JI = value1/value2;
		System.out.println("value1/value2="+value1+"/"+value2);
		return JI;
	}
	
	public HashSet<String> intersection(){
		HashSet<String> result = new HashSet<String>();
		result.addAll(this.SetA);
	    result.retainAll(this.SetB);
		return result;
	}
	
	public HashSet<String> unionsection(){
		HashSet<String> result = new HashSet<String>();
		result.addAll(this.SetA);
	    result.addAll(this.SetB);
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
