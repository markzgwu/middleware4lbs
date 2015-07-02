package org.projects.datamodel;

public abstract class AbsMapper {
	final RectPlane rectPlane;
	final RectEarth rectEarth;
	public AbsMapper(RectPlane rectPlane, RectEarth rectEarth) {
		super();
		this.rectPlane = rectPlane;
		this.rectEarth = rectEarth;
	}
	
	public abstract PointInEarth toEarthfromPlane(PointInPlane pPlane);
	public abstract PointInPlane toPlanefromEarth(PointInEarth pEarth);
	
}
