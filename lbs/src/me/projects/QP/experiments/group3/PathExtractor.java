package me.projects.QP.experiments.group3;

import java.util.HashMap;

import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;
import me.projects.QP.experiments.comparison.QPComparison;
import me.projects.QP.methods.CacheTable;
import me.projects.QP.methods.prediction.CRPrediction;
import me.projects.QP.methods.prediction.SynSubPathMatcher;
import me.projects.toolkit.ToolkitString;

import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;

import com.alibaba.fastjson.JSON;

public final class PathExtractor implements ILog{

	final DataSampler datasampler;

	public PathExtractor(final DataSampler datasampler){
		this.datasampler = datasampler;
	}
	
	//final int max_objectid = 19+1;
	String title = null;
	final StringBuffer result = new StringBuffer();
	final SynSubPathMatcher Matcher = new SynSubPathMatcher();
	
	HashMap<String,String[]> allsubpaths;
	HashMap<String,String> alltails;
	final String[] TimeStamps_SubPath = {"0","1"};
	final String TimeStamp_Tail = "19";
	
	public void init(){
		allsubpaths = load_allsubpaths();
		//logger.info(allpaths.toString());
		alltails= load_alltails();
		//logger.info(allcells.toString());
		System.out.println("allsubpaths:"+allsubpaths.size()+";alltails"+alltails.size());
	}
	
	public HashMap<String,String[]> load_allsubpaths(){
		return datasampler.getAllSubpaths(TimeStamps_SubPath);
	}
	
	public HashMap<String,String> load_alltails(){
		return datasampler.getAllCellsOfTimestamp(TimeStamp_Tail);
	}
	
	public void ShowData(){
		for(int i=0;i<this.TestPathNumber;i++){
			String key = String.valueOf(i);
			System.out.println(allsubpaths.get(key)+"->"+alltails.get(key));
		}
	}
	
	String output = "";
	
	double stat_count=0;
	double outTestResult = 0;
	
	public String oneTest(final String ObjectID){
		String result = "";
		
		//int SubPathLength = datasampler.para.pathLength ;
		
		//获取测试数据
		String[] testSubpath = allsubpaths.get(ObjectID);
		String testTail = alltails.get(ObjectID);
		
		//String debug = "DEBUG:\n";
		
		outTestResult = PosteriorBelief(testTail,testSubpath);
		//System.out.println(j+":"+showHistory+" "+tmpcellid1+":"+outresult+";"+this.datasampler.getFreq().getPct(tmpcellid1));
		output+=outTestResult+",";
		
		//决策方法
		String estimated_tail = "unknown";
		double estimated_PosteriorBelief_Pr = 0;
		final double threshold = 0.5;
		if(outTestResult>threshold){
			estimated_PosteriorBelief_Pr = outTestResult;
			estimated_tail = testTail;
			stat_count++;
		}
		result=estimated_tail+";"+estimated_PosteriorBelief_Pr;

		return result;
	}

	final int TestPathNumber = 10;
	public final double[] outputPosteriorBeliefPr= new double[TestPathNumber];
	public String worker(){
		String result = "";
		for(int i=0;i<TestPathNumber;i++){
			String key = String.valueOf(i);
			oneTest(key);
			outputPosteriorBeliefPr[i]=outTestResult;
		}
		result +=stat_count;
		output = Format4Matlab.format("L"+datasampler.level, output);
		return result;
	}
	
	public final double PosteriorBelief(String onetail,String[] subpath){
		double Pr = 0;
		int var1 = 0;
		int var2 = 0;
		
		for(int i=0;i<1200;i++){
			String key = String.valueOf(i);
			String[] samplesubpathStrings = allsubpaths.get(key);
			if(samplesubpathStrings==null){
				break;
			}
			String s1 = ToolkitString.ShowStringArray(samplesubpathStrings);
			String s2 = ToolkitString.ShowStringArray(subpath);
			String t1 = alltails.get(key);
			String t2 = onetail;
			if(t1==null){
				break;
			}
			System.out.println(s1+"->"+t1);
			System.out.println(s2+"->"+t2);
			System.out.println();
			if(s2.equals(s1)&&(t2.equals(t1))){
				var1++;
			}
			if(s2.equals(s1)){
				var2++;
			}
		}
		Pr = (double)var1 / (double)var2;
		System.out.println(Pr+"="+var1+"/"+var2);
		return Pr;
	}
	
	
	public String getOutput(){
		return output;
	}

	public static void main(String[] args) {
		Parameters para = new Parameters();
		ParameterTable.INSTANCE.init(para);
		DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();	
		PathExtractor me = new PathExtractor(datasampler);
		me.init();
		me.worker();
		
		System.out.println(me.output);
		
	}
	
}

