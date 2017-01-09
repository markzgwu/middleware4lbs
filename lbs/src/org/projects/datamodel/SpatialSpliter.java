package org.projects.datamodel;

import org.tools.forLog.*;

public final class SpatialSpliter implements ILog{
	final Level4qt level;
	final RectPlane rectPlane;
	public SpatialSpliter(Level4qt level){
		this.level = level;
		int length = this.level.powerLevelOf2;
		this.rectPlane = new RectPlane(new PointInPlane(0,0),new RectSideLength(length,length));
	}
	
	public String quadtreeEncoder(PointInPlane pPlane){
		String quadtreecode = "";
		RectSpliter qt = new RectSpliter(rectPlane);
		for(int l=0;l<level.level;l++){
			String coder = qt.encoder(pPlane);
			//logger.info(LogJSON.json(pPlane)+"coder:"+coder);
			quadtreecode+=coder;
			qt = qt.obtainNextSpliter(coder);
		}
		//logger.info(quadtreecode);
		return quadtreecode;
	}
	
	public PointInPlane quadtreeDecoder(String quadtreecode){
		RectSpliter qt = new RectSpliter(rectPlane);
		for(int index=0;index<quadtreecode.length();index++){
			char onecode = quadtreecode.charAt(index);
			qt = qt.obtainNextSpliter(onecode);
		}
		final PointInPlane leftbottomPlane = (PointInPlane) qt.rectPlane.getLeftbottom();
		return leftbottomPlane;
	}

}
