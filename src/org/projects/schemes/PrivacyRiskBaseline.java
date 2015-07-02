package org.projects.schemes;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;

import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forFormat.FormatConvertor;

public final class PrivacyRiskBaseline extends AbsScheme{
			
	void download(String oneline){
		//System.out.println(oneline);
		demandcells++;
		downloadcells++;
		MovingObject mo = new MovingObject(oneline);
		String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
		ArrayList<String> cellids = new ArrayList<String>();
		cellids.add(cellid);
		
		this.currentcell = cellid;
		this.cloakingregion = FormatConvertor.convert(cellids);
		
		final CRHistory oneCRHistory = new CRHistory(mo.getObject_Id(), cellid, cloakingregion, mo);
		TarReqRec.add(oneCRHistory);
		
		risk_oneEntity = varLSM.PosteriorBelief_Pr_A_Oloc(cellids);
		dbprovider.retrieval(cellid);
		//frequency.addValue(cellid);
	}
	double risk_sum = 0;
	double risk_oneEntity = 0;
	@Override
	public void download(){
		risk_sum = 0;
		for(String oneline:dataset){
			download(oneline);
			risk_sum += risk_oneEntity;
			System.out.println("risk_oneEntity:"+risk_oneEntity);
		}
		System.out.println("risk_sum:"+risk_sum);
		risk = risk_sum / downloadcells;
		System.out.println("risk:"+risk);
	}
	
}
