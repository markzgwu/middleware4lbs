package org.projects.preprocessing;

import org.projects.datamodel.PointInEarth;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEnv;
import org.tools.forLog.*;

public final class MovingObjectBatch implements ILog{

	public static final String mapping4cellid(final MovingObject mo,final RectEnv rectenv){
		//logger.info(LogJSON.json(mo));
		final PointInEarth pEarth = Convertor.convert(mo);
		final PointInPlane pPlane = rectenv.mapper.toPlanefromEarth(pEarth);
		final String cellid = rectenv.spatialSpliter.quadtreeEncoder(pPlane);
		return cellid;
	}
	
	public static final PointInPlane mapping(final MovingObject mo,final RectEnv rectenv){
		final PointInEarth pEarth = Convertor.convert(mo);
		return rectenv.mapper.toPlanefromEarth(pEarth);
	}
	
	public static final int mapping4hilbertid(final MovingObject mo,final RectEnv rectenv){
		final PointInEarth pEarth = Convertor.convert(mo);
		PointInPlane p = rectenv.mapper.toPlanefromEarth(pEarth);
		int x = (int)Math.floor(p.getX());
		int y = (int)Math.floor(p.getY());
		int hid = rectenv.getHilbert().hilbertCurve(x,y);
		return hid;
	}
}
