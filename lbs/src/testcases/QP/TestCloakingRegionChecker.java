package testcases.QP;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;
import me.projects.QP.methods.prediction.CRPrediction;
import me.projects.QP.methods.prediction.PathSpliter;
import me.projects.QP.methods.prediction.SubPathMatcher;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class TestCloakingRegionChecker {
	
	
	//@Test
	public void testerror(){
		final Parameters para = new Parameters(4);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final CRPrediction CRChecker = new CRPrediction(datasampler);	
		//int a = 2;//敌手获取的连续子路径长度
		HashMap<String,String> allcells=CRChecker.alltails;
        HashMap<String,String[]> allpaths=CRChecker.allpaths;
        assertEquals(allpaths.size(),allcells.size());
        
        String[] subpatherror = {"3103","3012"};
        String[] subpatherror1 = {"3012","3103"};
        String tailerror="3103";
        System.out.println(CRChecker.PosteriorBelief_Pr(tailerror, subpatherror));
        System.out.println(CRChecker.PosteriorBelief_Pr(tailerror, subpatherror1));
	}
	
	@Test
	public void testerror1(){
		final Parameters para = new Parameters(5);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final CRPrediction CRChecker = new CRPrediction(datasampler);			
		SubPathMatcher matcher = new SubPathMatcher();
		//int a = 2;//敌手获取的连续子路径长度
		HashMap<String,String> alltails=CRChecker.alltails;
        HashMap<String,String[]> allpaths=CRChecker.allpaths;
        assertEquals(allpaths.size(),alltails.size());
        ArrayList<String[]> CRLerror = new ArrayList<String[]>();
        CRLerror.add(new String[]{"21320"});
        CRLerror.add(new String[]{"21320"});
        CRLerror.add(new String[]{"21321"});
        String objectid = "39";
        String tailerror=alltails.get(objectid);
        final String[] onepath = allpaths.get(objectid);
        
        System.out.println("CRLerror="+JSON.toJSON(CRLerror));
        System.out.println("tailerror="+JSON.toJSON(tailerror));
        System.out.println("allpaths.get("+objectid+"))="+JSON.toJSON(allpaths.get(objectid)));
        System.out.println("onepath="+JSON.toJSON(onepath));
        System.out.println();
        System.out.println(CRChecker.PosteriorBelief_CR(tailerror, CRLerror));
        System.out.println("matcher.isCloakingRegionList="+matcher.isCloakingRegionList(CRLerror, onepath));
        System.out.println("matcher.isCellInCloakingRegion="+matcher.isCellInCloakingRegion(new String[]{"21320"}, "21320"));
        System.out.println("matcher.isTail(tailerror, onepath)="+matcher.isTail(tailerror, onepath));
        //System.out.println(CRChecker.PosteriorBelief_CR(tailerror, subpatherror1));
	}	
	
	@Test
	public void testerror2(){
		final Parameters para = new Parameters(5);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final CRPrediction CRChecker = new CRPrediction(datasampler);			
		SubPathMatcher matcher = new SubPathMatcher();
		//int a = 2;//敌手获取的连续子路径长度
		HashMap<String,String> alltails=CRChecker.alltails;
        HashMap<String,String[]> allpaths=CRChecker.allpaths;
        assertEquals(allpaths.size(),alltails.size());
        ArrayList<String[]> CRLerror = new ArrayList<String[]>();
        CRLerror.add(new String[]{"21230"});
        CRLerror.add(new String[]{"21232"});
        CRLerror.add(new String[]{"21232"});
        CRLerror.add(new String[]{"21232"});
        CRLerror.add(new String[]{"21233"});
        String objectid = "39";
        String tailerror=alltails.get(objectid);
        final String[] onepath = allpaths.get(objectid);
        
        System.out.println("CRLerror="+JSON.toJSON(CRLerror));
        System.out.println("tailerror="+JSON.toJSON(tailerror));
        System.out.println("allpaths.get("+objectid+"))="+JSON.toJSON(allpaths.get(objectid)));
        System.out.println("onepath="+JSON.toJSON(onepath));
        System.out.println();
        System.out.println(CRChecker.PosteriorBelief_CR(tailerror, CRLerror));
        System.out.println(matcher.isCloakingRegionList(CRLerror, onepath));
        System.out.println(matcher.isCellInCloakingRegion(new String[]{"2132"}, "21320"));
        System.out.println(matcher.isTail(tailerror, onepath));
        //System.out.println(CRChecker.PosteriorBelief_CR(tailerror, subpatherror1));
	}	
	
	//@Test
	public void test1() {
		final Parameters para = new Parameters(4);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final CRPrediction CRChecker = new CRPrediction(datasampler);			
		
		int a = 2;//敌手获取的连续子路径长度
		HashMap<String,String> alltails=CRChecker.alltails;
        HashMap<String,String[]> allpaths=CRChecker.allpaths;
        assertEquals(allpaths.size(),alltails.size());
        
		String alloutput = "";
		int limit = 0;
		for(String objectid:allpaths.keySet()){
			limit++;
			if(limit==50){
				break;
			}
			String[] onepath = allpaths.get(objectid);
			String realtail = onepath[onepath.length-1];
			
			assertEquals(realtail,alltails.get(objectid));
			
			String output = "";
			for(int t=a;t<onepath.length-1;t++){
				/*
				String[] realsubpath = new String[a];
				for(int s=0;s<a;s++){
					realsubpath[s]=onepath[t-a+s];
				}
				*/
				final String[] realsubpath = PathSpliter.getRealsubpath(t,a,onepath);
				double postpr1 = CRChecker.PosteriorBelief_Pr1(realtail, realsubpath);
				double postpr2 = CRChecker.PosteriorBelief_Pr3(realtail, realsubpath);
				assertEquals(postpr1+";"+postpr2+":"+realsubpath+"->"+realtail+";",""+postpr1,""+postpr2);
				output+=postpr1+",";
			}
			alloutput+=output+"\n";
		}
		System.out.println(alloutput);
		
	}
	
	//@Test
	public void test0() {

		final Parameters para = new Parameters(4);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final CRPrediction CRChecker = new CRPrediction(datasampler);			
		
		int a = 2;//敌手获取的连续子路径长度
		HashMap<String,String> alltails=CRChecker.alltails;
        HashMap<String,String[]> allpaths=CRChecker.allpaths;
        assertEquals(allpaths.size(),alltails.size());
        
		String alloutput = "";
		int limit = 0;
		for(String objectid:allpaths.keySet()){
			limit++;
			if(limit==50){
				break;
			}
			String[] onepath = allpaths.get(objectid);
			String realtail = onepath[onepath.length-1];
			
			assertEquals(realtail,alltails.get(objectid));
			
			String output = "";
			for(int t=a;t<onepath.length-1;t++){
				/*
				String[] realsubpath = new String[a];
				for(int s=0;s<a;s++){
					realsubpath[s]=onepath[t-a+s];
				}
				*/
				final String[] realsubpath = PathSpliter.getRealsubpath(t,a,onepath);
				double postpr = CRChecker.PosteriorBelief_Pr(realtail, realsubpath);
				output+=postpr+",";
			}
			System.out.println(JSON.toJSONString(onepath));
			alloutput+=output+"\n";
		}
		System.out.println(alloutput);
		
	}

}
