package org.projects.datamodel;

public abstract class AbsRect {
	private final AbsPoint leftbottom;
	private final RectSideLength sidelength;	
	public AbsRect(AbsPoint leftbottom, RectSideLength sidelength) {
		super();
		this.leftbottom = leftbottom;
		this.sidelength = sidelength;
	}
	public final AbsPoint getLeftbottom() {
		return leftbottom;
	}
	public final RectSideLength getSidelength() {
		return sidelength;
	}
	
}
