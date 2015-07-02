package org.projects.schemes;

import java.util.ArrayList;

import org.projects.datamodel.PointInPlane;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;

public final class BufferingV2 extends AbsScheme{
	
	void bufferframework(String oneline){
		this.demandcells++;

		final MovingObject mo = new MovingObject(oneline);
		final PointInPlane pPlane = MovingObjectBatch.mapping(mo, rectenv);
		final String cellid = rectenv.spatialSpliter.quadtreeEncoder(pPlane);
		final boolean isCached = cachezone.check(cellid);
		
		if(isCached){
			cachezone.load(cellid);
		}else{

			ArrayList<String> cellids =  cloaking(cellid);
			for(String cid:cellids){
				String data = dbprovider.retrieval(cid);
				cachezone.save(cid,data);
				this.downloadcells++;
			}

		}

	}
	
	ArrayList<String> cloaking(String cellid){
		ArrayList<String> cellids = new ArrayList<String>();
		cellids.add(cellid);
		return cellids;
	}
	
	void download(String oneline){
		bufferframework(oneline);
	}
	
	@Override
	public void download(){
		for(String oneline:dataset){
			download(oneline);
		}
	}
	
}
