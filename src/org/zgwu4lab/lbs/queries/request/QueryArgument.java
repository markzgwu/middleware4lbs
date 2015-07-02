package org.zgwu4lab.lbs.queries.request;

public class QueryArgument {
	public double value;
	public String name;
	
	public QueryArgument(){
		
	}
	
	public final double getValue() {
		return value;
	}

	public final void setValue(double value) {
		this.value = value;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public QueryArgument(String name,double value){
		this.name = name;
		this.value = value;
	}
	public String toString(){
		return name+"="+value;
	}
}
