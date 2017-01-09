package org.assistants.dbsp.baidumaps;

import org.zgwu4lab.lbs.datamodel.geodata.Granularity;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

public final class Tester {

	public static void main(String[] args) {

		Granularity g = new Granularity(0.01,0.01);
		RectBounds rect1 = new RectBounds(g);
		rect1.Load_lbYX_rtYX("39.902835,116.532647,39.912835,116.542647");
		Loader_POIs loader1 = new Loader_POIs(rect1);
		loader1.do_main();
		
		rect1.Load_lbYX_rtYX("39.902835,116.542647,39.912835,116.552647");
		loader1 = new Loader_POIs(rect1);
		loader1.do_main();		
		
	}

}
