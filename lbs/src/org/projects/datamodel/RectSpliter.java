package org.projects.datamodel;

import org.tools.forLog.ILog;

public final class RectSpliter implements ILog{
	public final RectPlane rectPlane;
	private final RectSideLength childsidelength;
		
	public RectSpliter(RectPlane rectPlane) {
		super();
		this.rectPlane = rectPlane;
		this.childsidelength = childSideLength(rectPlane.getSidelength());
	}
	
	public RectSpliter obtainNextSpliter(final char num){
		return obtainNextSpliter(String.valueOf(num));
	}
	
	public RectSpliter obtainNextSpliter(final String num){
		double x = rectPlane.getLeftbottom().getX();
		double y = rectPlane.getLeftbottom().getY();
		final String bitXY = Coder.decode(num);
		switch(bitXY){
		case "00":
			//x+=0;
			//y+=0;
			break;
		case "01":
			y+=childsidelength.getLengthY();
			//x+=0;
			break;
		case "10":
			//y+=0;
			x+=childsidelength.getLengthX();
			break;
		case "11":
			x+=childsidelength.getLengthX();
			y+=childsidelength.getLengthY();
			break;			
		}
		final PointInPlane childleftbottom = new PointInPlane(x, y);
		final RectPlane childRectPlane = new RectPlane(childleftbottom, childsidelength);
		final RectSpliter onechildren = new RectSpliter(childRectPlane);
		return onechildren;
	}
	
	private RectSideLength childSideLength(final RectSideLength sidelength){
		final double childLengthX = sidelength.getLengthY()/2;
		final double childLengthY = sidelength.getLengthY()/2;
		final RectSideLength childsidelength = new RectSideLength(childLengthX,childLengthY);
		return childsidelength;
	}
	
	public String encoder(final PointInPlane point){
		//final SideLengthOfRect childsidelength = childSideLength();
		double x = (point.getX()-rectPlane.getLeftbottom().getX()) / childsidelength.getLengthX();
		double y = (point.getY()-rectPlane.getLeftbottom().getY()) / childsidelength.getLengthY();

		final String bitXY = String.valueOf((int)Math.floor(x))+String.valueOf((int)Math.floor(y));
		//logger.info("(x,y):"+x+","+y+";"+bitXY+";point="+point);
		final String quadtreecode = Coder.encode(bitXY);
		return quadtreecode;
	}
	
	
}
