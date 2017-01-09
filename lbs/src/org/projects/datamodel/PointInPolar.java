package org.projects.datamodel;

public final class PointInPolar {
	double radius;
	double angle;
	public PointInPolar(double radius, double angle) {
		super();
		this.radius = radius;
		this.angle = angle;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
}
