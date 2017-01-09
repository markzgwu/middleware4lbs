package org.projects.privacymodel;

import org.projects.datamodel.PointInPlane;

public final class EuclideanDistance extends AbsDistance{
	public EuclideanDistance(PointInPlane realLocation,
			PointInPlane observedLocation) {
		super(realLocation, observedLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	double distacne() {
		double a = Math.pow(observedLocation.getY()-realLocation.getY(),2);
		double b = Math.pow(observedLocation.getX()-realLocation.getX(),2);
		b = Math.sqrt(a+b);
		return b;
	}

}
