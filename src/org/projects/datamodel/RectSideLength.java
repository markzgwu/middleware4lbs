package org.projects.datamodel;

public final class RectSideLength {
	private final double LengthX;
	private final double LengthY;
	public RectSideLength(double lengthX, double lengthY) {
		super();
		this.LengthX = lengthX;
		this.LengthY = lengthY;
	}
	public double getLengthX() {
		return LengthX;
	}
	public double getLengthY() {
		return LengthY;
	}
	public String toString(){
		return LengthX+","+LengthY;
	}
}
