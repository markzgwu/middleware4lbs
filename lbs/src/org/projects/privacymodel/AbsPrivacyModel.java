package org.projects.privacymodel;

import org.projects.datamodel.PointInPlane;

public abstract class AbsPrivacyModel {
	public final PointInPlane realLocation;
	public final PointInPlane observedLocation;
	abstract double privacy();
	public AbsPrivacyModel(PointInPlane realLocation,
			PointInPlane observedLocation) {
		super();
		this.realLocation = realLocation;
		this.observedLocation = observedLocation;
	}
	
}
