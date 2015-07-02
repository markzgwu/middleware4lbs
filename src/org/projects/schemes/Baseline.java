package org.projects.schemes;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;

import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forFormat.FormatConvertor;

public final class Baseline extends AbsScheme{
			
	void oneDownload(String oneline){
		//System.out.println(oneline);
		demandcells++;
		downloadcells++;
		MovingObject mo = new MovingObject(oneline);
		String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
		this.currentMovingObject = mo;
		ArrayList<String> cellids = new ArrayList<String>();
		cellids.add(cellid);
		
		final CRHistory oneCRHistory = new CRHistory(mo.getObject_Id(), cellid, cloakingregion, mo);
		TarReqRec.add(oneCRHistory);
		
		this.currentcell = cellid;
		this.cloakingregion = FormatConvertor.convert(cellids);
		
		dbprovider.retrieval(cloakingregion);
		//frequency.addValue(cellid);
	}
	
	@Override
	public void download(){
		for(String oneline:dataset){
			oneDownload(oneline);
		}
		//System.out.println(dataset.length);
	}
	
}
