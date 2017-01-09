package me.projects.QP.methods;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;
import me.projects.QP.methods.prediction.CRPrediction;
import me.projects.QP.methods.prediction.CRSequence;
import me.projects.QP.methods.prediction.PathSpliter;
import me.projects.QP.methods.prediction.TargetReguestRecorder;

import com.alibaba.fastjson.JSONObject;

public final class QPOfflineAnalyzer {
	final public TargetReguestRecorder TarReqRec;
	final public CRPrediction CRChecker;
	public QPOfflineAnalyzer(final TargetReguestRecorder oneTarReqRec,final CRPrediction oneCRChecker){
		this.TarReqRec = oneTarReqRec;
		this.CRChecker = oneCRChecker;
	}
	
	public String analyze(String ObjectID){
		ArrayList<CRHistory> crhl = TarReqRec.recorder.get(ObjectID);
		int a = 2;
		int path_length = crhl.size();
		String r0 = "";
		String r1 = "";
		String r2 = "";
		String r3 = "";
		String r4 = "";
		for(int i=a;i<path_length;i++){
			final ArrayList<String[]> crl = PathSpliter.getCRList(i,a,crhl);
			final String[] realpath = PathSpliter.getRealsubpath(i,a,crhl);
			final CRSequence crs = new CRSequence(crl);
			//final String[] subpaths = crs.decompose();
			r0 += CRChecker.checkrealpath(ObjectID, realpath)+",";
			r1 += "undefined"+",";
			r2 += CRChecker.check_averagepostpr(crs)+",";
			r3 += CRChecker.check_entropy(crs)+",";
			r4 += "undefined"+",";
		}
		
		r0 = "checkrealpath="+r0;
		r1 = "checksafety="+r1;
		r2 = "check_averagepostpr="+r2;
		r3 = "check_entropy="+r3;
		r4 = "check_postpr_details="+r4;
		
		
		String r = r0+"\n"+r1+"\n"+r2+"\n"+r3+"\n"+r4+"\n";
		return r;
	}

	public void show(){
		for(ArrayList<CRHistory> CRL :TarReqRec.recorder.values()){
			System.out.println(JSONObject.toJSONString(CRL));
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
