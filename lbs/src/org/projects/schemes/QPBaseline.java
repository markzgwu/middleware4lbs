package org.projects.schemes;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;
import me.projects.QP.methods.prediction.CRSequence;

import org.parameters.I_constant;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forFormat.FormatConvertor;

import com.alibaba.fastjson.JSON;

public final class QPBaseline extends AbsScheme{
			
	void framework(final String oneline){
		//System.out.println(oneline);
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
		
		//frequency.addValue(cellid);
	}
	
	public void oneRequest(final MovingObject mo){
		
		demandcells++;
		final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
		currentcell = cellid;
		currentMovingObject = mo;
		
		final ArrayList<String> cellids = new ArrayList<String>();
		cellids.add(cellid);
		cloakingregion = FormatConvertor.convert(cellids);
		
		dbprovider.retrieval(cloakingregion);
		downloadcells+=getNumCell();
		
		final CRHistory oneCRHistory = new CRHistory(mo.getObject_Id(), cellid, cloakingregion, mo);
		//System.out.println(JSONObject.toJSONString(oneCRHistory));
		TarReqRec.add(oneCRHistory);
		//System.out.println(TarReqRec.recorder.keySet());
		//System.out.println(mo.getObject_Id());
		//System.out.println(TarReqRec.recorder.keySet().contains(mo.getObject_Id()));


		//System.out.println("QPSTAT.current_QPStatus="+this.QPSTAT.current_QPStatus);
	}
	
	public void oneMetric(final MovingObject mo){
		final ArrayList<String[]> RecentCRs=TarReqRec.getRecent(mo.getObject_Id(),I_constant.subpath_length);
		if(RecentCRs==null){
			QPSTAT.current_QPStatus="short";
			System.out.println("WARN:Recent Null!"+TarReqRec.recorder.get(mo.getObject_Id()).size()+"TarReqRec.recorder.get("+mo.getObject_Id()+")=\n"+JSON.toJSON(TarReqRec.recorder.get(mo.getObject_Id())));
		}else{
			QPSTAT.current_QPStatus="work";
			final CRSequence CRSequence = new CRSequence(RecentCRs);
			QPSTAT.current_QPResult = CRChecker.check(CRSequence);
			if(QPSTAT.current_QPResult.PredictionPaths.length>0){
				QPSTAT.availcount++;
			}else{
				final String msg = "WARN:Prediction Null!"
						+TarReqRec.recorder.get(mo.getObject_Id()).size()
						+"TarReqRec.recorder.get("+mo.getObject_Id()+")=\n"
						+JSON.toJSON(TarReqRec.recorder.get(mo.getObject_Id()));
						//msg+="\n"+JSON.toJSONString(TarReqRec.recorder.);
				System.out.println(msg);
			}
			System.out.println("QPSTAT="+JSON.toJSONString(QPSTAT));
		}
		QPSTAT.safety+=QPSTAT.current_QPResult.QPsafety;
		QPSTAT.totalentropy+=QPSTAT.current_QPResult.Entropy;
	}
	
	@Override
	public void download(){
		for(String oneline:dataset){
			framework(oneline);
		}
		//System.out.println(dataset.length);
	}
	
}
