package org.projects.schemes;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;
import me.projects.QP.methods.prediction.CRSequence;
import me.projects.toolkit.ToolkitRecorder;

import org.parameters.I_constant;
import org.projects.LocationAnonymizer.GridBasicNewCasper;
import org.projects.LocationAnonymizer.GridPredictionProtection;
import org.projects.LocationAnonymizer.IImprovedLocationAnonymizer;
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

public class QPPredictionProtection extends AbsScheme {
	
	final PrivacyProfile privacyprofile;
	public QPPredictionProtection(final PrivacyProfile privacyprofile){
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
			oneRequest(mo);
			//oneMetric(mo);
			oneMetric(mo);
		}else{
			//System.out.println("Skipping abnormal objectid="+mo.getObject_Id());
		}
	}
	
	public void oneRequest(final MovingObject mo){
		final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
		//System.out.println("mo.getObject_Id()="+mo.getObject_Id());
		this.demandcells++;
		
		this.currentcell = cellid;
		
		long startTime=System.nanoTime();   //获取开始时间 
		this.cloakingregion = cloaking(cellid,mo.getObject_Id());
		long endTime=System.nanoTime(); //获取结束时间 
		this.elapsedTime += (endTime-startTime);
		
		if(cloakingregion==null){
			this.cloakingfailure++;
			System.out.println("Cloaking Fails!");
		}else{
			int n_dl_cells= getNumCell();
			//System.out.println(n_dl_cells);
			for(String cid:cloakingregion){
				dbprovider.retrieval(cid);
			}
			this.downloadcells+=n_dl_cells;
		}

	}
	
	public void oneMetric(final MovingObject mo){
		final CRHistory oneCRHistory = new CRHistory(mo.getObject_Id(), currentcell, cloakingregion, mo);
		TarReqRec.add(oneCRHistory);
		final ArrayList<CRHistory> CRL = TarReqRec.recorder.get(mo.getObject_Id());
		final int objectid_CRL_length = CRL.size();
		if(objectid_CRL_length<=I_constant.subpath_length){
			QPSTAT.current_QPStatus="short";
		}else{
			QPSTAT.current_QPStatus="work";
			final ArrayList<String[]> RecentCRs = ToolkitRecorder.getRecent(CRL, I_constant.subpath_length);			
			if(RecentCRs==null){
				System.out.println("CRL="+JSON.toJSONString(CRL));
				System.out.println("RecentCRs="+JSON.toJSONString(RecentCRs));
			}else{
				final CRSequence CRSequence = new CRSequence(RecentCRs);
				System.out.println("CRSequence="+JSON.toJSONString(CRSequence));
				QPSTAT.current_QPResult = CRChecker.check(CRSequence);
				if(QPSTAT.current_QPResult.PredictionPaths.length>0){
					QPSTAT.availcount++;
				}
				System.out.println("QPSTAT="+JSON.toJSONString(QPSTAT));
			}

		}
		QPSTAT.safety+=QPSTAT.current_QPResult.QPsafety;
		QPSTAT.totalentropy+=QPSTAT.current_QPResult.Entropy;
	}
	
	private String[] cloaking(final String cellid,final String ObjectID){
		final ArrayList<String> nodeids = LASwitcher(cellid,ObjectID);
		if(nodeids==null){
			return null;
		}else{
			return FormatConvertor.convert(nodeids);
		}
	}
	
	private QuadTreeCounter qtcounter = null;

	public ArrayList<String> LASwitcher(final String cellid,final String ObjectID){
		
		final IImprovedLocationAnonymizer QPLA
				= new GridPredictionProtection(qtcounter, CRChecker);
		
		final ILocationAnonymizer NCLA
				= new GridBasicNewCasper(qtcounter);
		
		final ArrayList<CRHistory> CRL = TarReqRec.recorder.get(ObjectID);
		
		ArrayList<String> anonymityset;
		if((CRL!=null)&&(CRL.size()>=I_constant.subpath_length)){
			System.out.println("CRL="+JSON.toJSONString(CRL));
			final ArrayList<String[]> RecentCRs = ToolkitRecorder.getRecent(CRL,I_constant.subpath_length);

			if(RecentCRs==null){
				anonymityset = NCLA.anonymization(privacyprofile,cellid);
			}else{
				System.out.println("Size="+RecentCRs.size()+";RecentCRs="+JSON.toJSONString(RecentCRs));
				final CRSequence CRSequence = new CRSequence(RecentCRs);
				anonymityset = QPLA.anonymization(privacyprofile,cellid,CRSequence);

			}
			//}
		}else{
			anonymityset = NCLA.anonymization(privacyprofile,cellid);
		}
		/*
		ArrayList<String> extendanonymityset = null;
		if(anonymityset != null){
			extendanonymityset = Convertor.extend(anonymityset, rectenv.getLevel().level);
		}
		*/
		return anonymityset;
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
