package org.projects.schemes;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;

import org.projects.LocationAnonymizer.GridBasicNewCasper;
import org.projects.LocationAnonymizer.ILocationAnonymizer;
import org.projects.LocationAnonymizer.QuadTreeCounter;
import org.projects.datamodel.PointInEarth;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.Convertor;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.projects.privacymodel.PrivacyProfile;
import org.tools.forFormat.FormatConvertor;

public class PrivacyRiskNewcasper extends AbsScheme {
	
	final PrivacyProfile privacyprofile;
	public PrivacyRiskNewcasper(final PrivacyProfile privacyprofile){
		this.privacyprofile = privacyprofile;
	}
	
	@Override
	public void init(RectEnv rectenv){
		this.rectenv = rectenv;
		this.qtcounter = new QuadTreeCounter(rectenv);
	}
	
	double risk_sum = 0;
	double risk_oneEntity = 0;
	@Override
	public void download(){
		index();
		risk_sum = 0;
		for(String oneline:dataset){
			framework(oneline);
			risk_sum += risk_oneEntity;
			if(b_output_detail){
			System.out.println("risk_oneEntity:"+risk_oneEntity);
			}
		}
		if(b_output_detail){
		System.out.println("risk_sum:"+risk_sum);
		}
		risk = risk_sum / downloadcells;
		if(b_output_detail){
		System.out.println("risk:"+risk);
		}
	}
	

	String framework(String oneline){
		
		final MovingObject mo = new MovingObject(oneline);
		final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
		this.demandcells++;
		ArrayList<String> cellids = cloaking(cellid);
		this.currentcell = cellid;
		this.cloakingregion = FormatConvertor.convert(cellids);
		
		final CRHistory oneCRHistory = new CRHistory(mo.getObject_Id(), cellid, cloakingregion, mo);
		TarReqRec.add(oneCRHistory);
		
		int n_dl_cells= cellids.size();
		this.downloadcells+=n_dl_cells;
		//System.out.println(n_dl_cells);
		risk_oneEntity = varLSM.PosteriorBelief_Pr_A_Oloc(cellids);
		for(String cid:cellids){
			data = dbprovider.retrieval(cid);
		}
		return data;
	}
	
	ArrayList<String> cloaking(String cellid){
		ArrayList<String> nodeids = BasicLA2(cellid);
		return nodeids;
	}	
	
	private QuadTreeCounter qtcounter = null;

	public ArrayList<String> BasicLA2(String cellid){
		final ILocationAnonymizer basicla=new GridBasicNewCasper(qtcounter);
		//String cellid=rectenv.quadTreeEncoder(mo);
		//privacy profile
		//final int k = 10;
		//final int Amin = 2;	
		ArrayList<String> anonymityset = basicla.anonymization(privacyprofile,cellid);
		final ArrayList<String> extendanonymityset = Convertor.extend(anonymityset, rectenv.getLevel().level);
		return extendanonymityset;
	}
	
	public void index(){
		for(String oneline:dataset) {
			
			MovingObject mo = new MovingObject(oneline);
			PointInEarth pEarth = Convertor.convert(mo);
			PointInPlane pPlane = rectenv.mapper.toPlanefromEarth(pEarth);
			String cellid = rectenv.spatialSpliter.quadtreeEncoder(pPlane);			
			
			//System.out.println(JSON.toJSONString(mo));
			//Y lat; Lng X
			//String location = mo.getLat()+","+mo.getLng();
			//System.out.println(location);
			//this.elapsedTime += this.oneRequestElapsedTime();
			//System.out.println(cellid);
			qtcounter.putall(cellid);				
		}
	}	
	
}
