package org.projects.datamodel;

public abstract class AbsPoint {
	private final double x;
	private final double y;
	
	public AbsPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public String toString(){
		return x+","+y;
	}
	
}
