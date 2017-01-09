package org.projects.datamodel;

import org.tools.forMaths.MathTool4BigDec;

public final class MapperV1 extends AbsMapper {
	
	public MapperV1(RectPlane rectPlane, RectEarth rectEarth) {
		super(rectPlane, rectEarth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PointInEarth toEarthfromPlane(PointInPlane pPlane){
		final PointInEarth leftbottomEarth = (PointInEarth) rectEarth.getLeftbottom();
		final PointInPlane leftbottomPlane = (PointInPlane) rectPlane.getLeftbottom();
		final double eX = rectEarth.getSidelength().getLengthX()/rectPlane.getSidelength().getLengthX();
		final double eY = rectEarth.getSidelength().getLengthY()/rectPlane.getSidelength().getLengthY();
		double xEarth = leftbottomEarth.getX() + eX * (pPlane.getX()-leftbottomPlane.getX());
		double yEarth = leftbottomEarth.getY() + eY * (pPlane.getY()-leftbottomPlane.getX());
		xEarth = MathTool4BigDec.Round12(xEarth);
		yEarth = MathTool4BigDec.Round12(yEarth);
		final PointInEarth pEarth = new PointInEarth(xEarth,yEarth);
		return pEarth;
	}
	
	@Override
	public PointInPlane toPlanefromEarth(PointInEarth pEarth){
		final PointInEarth leftbottomEarth = (PointInEarth) rectEarth.getLeftbottom();
		final PointInPlane leftbottomPlane = (PointInPlane) rectPlane.getLeftbottom();
		final double eX = rectPlane.getSidelength().getLengthX()/rectEarth.getSidelength().getLengthX();
		final double eY = rectPlane.getSidelength().getLengthY()/rectEarth.getSidelength().getLengthY();
		double xMath = leftbottomPlane.getX() + eX * (pEarth.getX()-leftbottomEarth.getX());
		double yMath = leftbottomPlane.getY() + eY * (pEarth.getY()-leftbottomEarth.getY());
		xMath = MathTool4BigDec.Round12(xMath);
		yMath = MathTool4BigDec.Round12(yMath);
		final PointInPlane pPlane = new PointInPlane(xMath,yMath);
		return pPlane;
	}
	
}
