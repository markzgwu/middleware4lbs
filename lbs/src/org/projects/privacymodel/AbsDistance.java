package org.projects.privacymodel;

import org.projects.datamodel.PointInPlane;

public abstract class AbsDistance {
	public final PointInPlane realLocation;
	public final PointInPlane observedLocation;
	abstract double distacne();
	public AbsDistance(PointInPlane realLocation,
			PointInPlane observedLocation) {
		super();
		this.realLocation = realLocation;
		this.observedLocation = observedLocation;
	}
}
