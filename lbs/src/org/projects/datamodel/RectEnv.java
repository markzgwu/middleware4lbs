package org.projects.datamodel;

import org.Hilbert.HilbertBigint;
import org.projects.preprocessing.Convertor;
import org.tools.forLog.*;
import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class RectEnv implements ILog{
	private final Level4qt level;
	private final Rectangle rectangle;
	private final HilbertBigint hilbert;
	public final Level4qt getLevel() {
		return level;
	}

	public RectEnv(final int level,Rectangle rectangle) {
		super();
		this.level = new Level4qt(level);
		this.rectangle = rectangle;
		init();
		
		this.hilbert = new HilbertBigint(level);
		hilbert.hilbertCurve();
	}
	
	public HilbertBigint getHilbert(){
		return hilbert;
	}
	
	public String quadtreeEncoder(LocationCoordinate location){
		PointInEarth pEarth = location.toPointInEarth();
		PointInPlane pPlane = mapper.toPlanefromEarth(pEarth);
		String QuadTreeCode = spatialSpliter.quadtreeEncoder(pPlane);;
		return QuadTreeCode;
	}
	
	public double getRectArea(){
		double rectarea = MapsTool.mapmeasure.getArea_Rect(rectangle.left_bottom,rectangle.right_top);
		return rectarea;
	}
	
	public double getCellArea(){
		return getRectArea()/level.powerLevelOf4;
	}	
	
	public SpatialSpliter spatialSpliter = null;
	public AbsMapper mapper = null;

	public void init(){
		
		spatialSpliter = initSpatialSpliter();
		mapper = initMapper();
	}
	
	private SpatialSpliter initSpatialSpliter(){
		return new SpatialSpliter(level);
	}
	
	private RectPlane initRectOnMathPlane(){
		final double length = level.powerLevelOf2;
		final PointInPlane leftbottom_math = new PointInPlane(0,0);
		final RectSideLength sidelength_math = new RectSideLength(length,length);
		final RectPlane plane = new RectPlane(leftbottom_math, sidelength_math);
		return plane;
	}
	
	private AbsMapper initMapper(){
		final RectPlane rect1 = initRectOnMathPlane();
		final RectEarth rect2 = Convertor.convert(rectangle);
		final AbsMapper mapper = new MapperV1(rect1, rect2);
		logger.info(LogJSON.json(rect2)+";"+LogJSON.json(rectangle));
		return mapper;
	}

	public String summary(){
		String message = rectangle.toString();
		message+="\nArea="+this.getRectArea()+"km2;CellArea="+this.getCellArea()+"km2";
		message+="\n# of Cells:"+this.getLevel().powerLevelOf4+"=("+this.getLevel().powerLevelOf2+")^2";
		return message;
	}
	
}
