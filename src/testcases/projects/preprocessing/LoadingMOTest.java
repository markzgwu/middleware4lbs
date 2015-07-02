package testcases.projects.preprocessing;

import static org.junit.Assert.*;

import org.junit.Test;
import org.projects.preprocessing.LoadingMO;

public class LoadingMOTest {

	@Test
	public void testLoaddata_totags() {
		LoadingMO mo = new LoadingMO("D:\\_workshop\\1338.txt");
		assertEquals(100,mo.loaddata_bytags("1").length);
		assertEquals(89,mo.loaddata_bytags("5").length);
		assertEquals(74,mo.loaddata_bytags("19").length);
	}

}
