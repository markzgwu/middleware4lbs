package org.projects.schemes;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;
import me.projects.QP.methods.prediction.CRSequence;

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

import com.alibaba.fastjson.JSON;

public class QPNewcasper extends AbsScheme {
	
	final PrivacyProfile privacyprofile;
	public QPNewcasper(final PrivacyProfile privacyprofile){
		this.privacyprofile = privacyprofile;
	}
	
	@Override
	public void init(RectEnv rectenv){
		this.rectenv = rectenv;
		this.qtcounter = new QuadTreeCounter(rectenv);
	}
	
	@Override
	public void download(){
		index();
		for(String oneline:dataset){
			framework(oneline);
		}
	}
	
	void framework(String oneline){
		final MovingObject mo = new MovingObject(oneline);
		if(this.TargetObjectID.contains(mo.getObject_Id())){
			long startTime=System.nanoTime();   //获取开始时间 
			oneRequest(mo);
			//oneMetric(mo);
			long endTime=System.nanoTime(); //获取结束时间 
			this.elapsedTime += (endTime-startTime);
			oneMetric(mo);
		}else{
			//System.out.println("Skipping abnormal objectid="+mo.getObject_Id());
		}
	}
	
	public void oneRequest(final MovingObject mo){
		final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
		this.demandcells++;
		
		this.currentcell = cellid;
		this.cloakingregion = cloaking(cellid);
		
		final CRHistory oneCRHistory = new CRHistory(mo.getObject_Id(), cellid, cloakingregion, mo);
		TarReqRec.add(oneCRHistory);
		
		int n_dl_cells = getNumCell();
		this.downloadcells+=n_dl_cells;
		//System.out.println(n_dl_cells);
		for(String cid:cloakingregion){
			dbprovider.retrieval(cid);
		}		
	}
	
	public void oneMetric(final MovingObject mo){
		final ArrayList<String[]> RecentCRs=TarReqRec.getRecent(mo.getObject_Id(),2);
		if(RecentCRs==null){
			QPSTAT.current_QPStatus="short";
		}else{
			QPSTAT.current_QPStatus="work";
			final CRSequence CRSequence = new CRSequence(RecentCRs);
			QPSTAT.current_QPResult = CRChecker.check(CRSequence);
			System.out.println("QPSTAT="+JSON.toJSONString(QPSTAT));
			if(QPSTAT.current_QPResult.PredictionPaths.length>0){
				QPSTAT.availcount++;
			}
		}
		QPSTAT.safety+=QPSTAT.current_QPResult.QPsafety;
		QPSTAT.totalentropy+=QPSTAT.current_QPResult.Entropy;
	}
	
	private String[] cloaking(String cellid){
		final ArrayList<String> nodeids = BasicLA2(cellid);
		return FormatConvertor.convert(nodeids);
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
