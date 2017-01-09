package testcases.projects.datamodel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projects.datamodel.*;

public final class MapperTest {
	final double level = 4;
	final double length = Math.pow(2, level);
	final PointInPlane leftbottom_math = new PointInPlane(0,0);
	final RectSideLength sidelength_math = new RectSideLength(length,length);
	final RectPlane rectMath = new RectPlane(leftbottom_math, sidelength_math);
	final PointInEarth leftbottom_earth = new PointInEarth(39.0,119.0);
	final RectSideLength sidelength_earth = new RectSideLength(1,1);
	final RectEarth rectEarth = new RectEarth(leftbottom_earth, sidelength_earth);		
	
	final AbsMapper mapper = new MapperV1(rectMath, rectEarth);
	
	@Test
	public void test1(){
		PointInPlane pointInMath = leftbottom_math;
		PointInEarth pointInEarth = mapper.toEarthfromPlane(pointInMath);
		//System.out.println(pointInMath.toString()+";"+mapper.toMathfromEarth(pointInEarth).toString());
		assertEquals(pointInMath.toString(),mapper.toPlanefromEarth(pointInEarth).toString());
		//System.out.println(leftbottom_earth.toString()+","+pointInEarth.toString());
		assertEquals(leftbottom_earth.toString(),pointInEarth.toString());
	}
	
	@Test
	public void test2(){
		PointInPlane pointInMath = new PointInPlane(4.123,7.4563);
		PointInEarth pointInEarth = mapper.toEarthfromPlane(pointInMath);
		System.out.println(pointInMath.toString()+";"+mapper.toPlanefromEarth(pointInEarth).toString());
		assertEquals(pointInMath.toString(),mapper.toPlanefromEarth(pointInEarth).toString());
	}	
}
