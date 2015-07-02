package testcases.projects.datamodel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.projects.datamodel.*;

public class SpatialSpliterTest {
	@Test
	public void test1(){
		SpatialSpliter test = new SpatialSpliter(new Level4qt(1));
		//[0,2)
		assertEquals("0",test.quadtreeEncoder(new PointInPlane(0,0)));
		assertEquals("1",test.quadtreeEncoder(new PointInPlane(0,1.9)));
		assertEquals("3",test.quadtreeEncoder(new PointInPlane(1.9,0)));
		assertEquals("2",test.quadtreeEncoder(new PointInPlane(1.9,1.9)));
		//assertEquals("0",test.quadtreeEncoder(new PointInPlane(4,4)));
		assertEquals(new PointInPlane(0,0).toString(),test.quadtreeDecoder("0").toString());
		assertEquals(new PointInPlane(0,1.0).toString(),test.quadtreeDecoder("1").toString());
		assertEquals(new PointInPlane(1.0,0).toString(),test.quadtreeDecoder("3").toString());
		assertEquals(new PointInPlane(1.0,1.0).toString(),test.quadtreeDecoder("2").toString());
	}
	
	@Test
	public void test2(){
		SpatialSpliter test = new SpatialSpliter(new Level4qt(2));
		//[0,4)
		assertEquals("00",test.quadtreeEncoder(new PointInPlane(0,0)));
		assertEquals("01",test.quadtreeEncoder(new PointInPlane(0,1.9)));
		assertEquals("03",test.quadtreeEncoder(new PointInPlane(1.9,0)));
		assertEquals("02",test.quadtreeEncoder(new PointInPlane(1.9,1.9)));
		assertEquals("13",test.quadtreeEncoder(new PointInPlane(1.9,2.9)));
		assertEquals("31",test.quadtreeEncoder(new PointInPlane(2.9,1.9)));
		//assertEquals(null,test.quadtreeEncoder(new PointInPlane(4,4)));
		assertEquals("22",test.quadtreeEncoder(new PointInPlane(4-0.0001,4-0.0001)));
		assertEquals("32",test.quadtreeEncoder(new PointInPlane(4-0.0001,1.9)));
		assertEquals("12",test.quadtreeEncoder(new PointInPlane(1.9,4-0.0001)));
		
		assertEquals(new PointInPlane(0.0,0.0).toString(),test.quadtreeDecoder("00").toString());
		assertEquals(new PointInPlane(0.0,1.0).toString(),test.quadtreeDecoder("01").toString());
		assertEquals(new PointInPlane(3.0,1.0).toString(),test.quadtreeDecoder("32").toString());
		assertEquals(new PointInPlane(1.0,3.0).toString(),test.quadtreeDecoder("12").toString());
	}
	
	@Test
	public void test3(){
		int level = 8;
		SpatialSpliter test = new SpatialSpliter(new Level4qt(level));
		String prefix = "";
		for(int i=0;i<level-1;i++){
			prefix+="0";
		}
		assertEquals(prefix+"0",test.quadtreeEncoder(new PointInPlane(0,0)));
		assertEquals(prefix+"1",test.quadtreeEncoder(new PointInPlane(0,1.9)));
		assertEquals(prefix+"3",test.quadtreeEncoder(new PointInPlane(1.9,0)));
		assertEquals(prefix+"2",test.quadtreeEncoder(new PointInPlane(1.9,1.9)));
		//assertEquals("0",test.quadtreeEncoder(new PointInPlane(4,4)));
		assertEquals(new PointInPlane(0.0,0.0).toString(),test.quadtreeDecoder(prefix+"0").toString());
		assertEquals(new PointInPlane(0.0,1.0).toString(),test.quadtreeDecoder(prefix+"1").toString());
		assertEquals(new PointInPlane(1.0,0.0).toString(),test.quadtreeDecoder(prefix+"3").toString());
		assertEquals(new PointInPlane(1.0,1.0).toString(),test.quadtreeDecoder(prefix+"2").toString());
	}
	
	@Test
	public void test4(){
		int level = 16;
		SpatialSpliter test = new SpatialSpliter(new Level4qt(level));
		String prefix = "";
		for(int i=0;i<level-1;i++){
			prefix+="0";
		}
		assertEquals(prefix+"0",test.quadtreeEncoder(new PointInPlane(0,0)));
		assertEquals(prefix+"1",test.quadtreeEncoder(new PointInPlane(0,1.9)));
		assertEquals(prefix+"3",test.quadtreeEncoder(new PointInPlane(1.9,0)));
		assertEquals(prefix+"2",test.quadtreeEncoder(new PointInPlane(1.9,1.9)));
		//assertEquals("0",test.quadtreeEncoder(new PointInPlane(4,4)));
		assertEquals(new PointInPlane(0.0,0.0).toString(),test.quadtreeDecoder(prefix+"0").toString());
		assertEquals(new PointInPlane(0.0,1.0).toString(),test.quadtreeDecoder(prefix+"1").toString());
		assertEquals(new PointInPlane(1.0,0.0).toString(),test.quadtreeDecoder(prefix+"3").toString());
		assertEquals(new PointInPlane(1.0,1.0).toString(),test.quadtreeDecoder(prefix+"2").toString());
	}	
}
