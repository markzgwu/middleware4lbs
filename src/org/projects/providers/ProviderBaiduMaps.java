package org.projects.providers;

import org.assistants.dbsp.baidumaps.Loader_POIs;
import org.zgwu4lab.lbs.datamodel.geodata.Granularity;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

public final class ProviderBaiduMaps extends AbsProvider implements IDBProvider{

	@Override
	public String retrieval(String cellid) {
		Granularity g = new Granularity(0.01,0.01);
		RectBounds rect1 = new RectBounds(g);
		rect1.Load_lbYX_rtYX("39.902835,116.532647,39.912835,116.542647");
		Loader_POIs loader1 = new Loader_POIs(rect1);
		loader1.do_main();
		return "Data("+cellid+")";
	}

}
