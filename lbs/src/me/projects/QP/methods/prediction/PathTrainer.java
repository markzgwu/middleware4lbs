package me.projects.QP.methods.prediction;

import java.util.HashMap;

import me.projects.LISG.methods.DataSampler;
import me.projects.QP.methods.CacheTable;

import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;

public final class PathTrainer implements ILog{

	final DataSampler datasampler;

	public PathTrainer(final DataSampler datasampler){
		this.datasampler = datasampler;
	}
	
	//final int max_objectid = 19+1;
	String title = null;
	final StringBuffer result = new StringBuffer();
	
	HashMap<String,String[]> allpaths;
	HashMap<String,String> allcells;
	
	//final AbsMatcher matcher = new StrictMatcher();
	final SubPathMatcher matcher = new SubPathMatcher();

	public void init(){
		allpaths = this.datasampler.getAllPaths();
		//logger.info(allpaths.toString());
		allcells= getAllTails();
		//logger.info(allcells.toString());
		System.out.println("allpaths:"+allpaths.size()+";allcells/alltails"+allcells.size());
	}
	
	public HashMap<String,String> getAllTails(){
		//return this.datasampler.getAllCells();
		final HashMap<String,String> alltails = new HashMap<String,String>();
		for(String ObjectID : allpaths.keySet()){
			final String[] onepath = allpaths.get(ObjectID);
			final String onetail = onepath[onepath.length-1];
			alltails.put(ObjectID,onetail);
		}
		return alltails;
	}

	String[] getHistory(final String[] onepath,final int length){
		final String [] subpath=new String[length];
		for(int i=0;i<length;i++){
			if(i==onepath.length-1){
				break;
			}else{
				subpath[i]=onepath[i];
			}
			
		}
		//Log.info(ToolkitString.ShowStringArray(History));
		return subpath;
	}
	
	String getTail(final String[] onepath){
		return onepath[onepath.length-1];
	}
	
	String output = "";
	
	double stat_count=0;
	double outTestResult = 0;
	
	public String oneDecision(final String[] onepath){
		String result = "";
		String[] currentpath = onepath;
		
		int SubPathLength = datasampler.para.pathLength ;
		
		//获取测试数据
		String[] testSubpath = getHistory(currentpath,SubPathLength);
		String testTail = getTail(currentpath);
		
		//String debug = "DEBUG:\n";
		
		outTestResult = oneTest(testTail,testSubpath);
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

	final int TestPathNumber = 100;
	public final double[] outputPosteriorBeliefPr= new double[TestPathNumber];
	public String worker(){
		String result = "";
		for(int i=0;i<TestPathNumber;i++){
			final String[] onepath = allpaths.get(i);
			oneDecision(onepath);
			outputPosteriorBeliefPr[i]=outTestResult;
		}
		result +=stat_count;
		output = Format4Matlab.format("L"+datasampler.level, output);
		return result;
	}
	
	public double oneTest(final String testTail,final String[] testSubpath){
		double onetest = 0;
		String key = PathSpliter.getPredictionPath(testTail,testSubpath);
		final HashMap<String,Double> CachePostBeliefs = CacheTable.INSTANCE.CachePostBeliefs;
		if(CachePostBeliefs.containsKey(key)){
			onetest = CachePostBeliefs.get(key);
		}else{
			onetest = PosteriorBelief_Pr(testTail,testSubpath);
		}
		return onetest;
	}
	
	public final double PosteriorBelief_Pr(String onetail,String[] subpath){
		double Pr = 0;
		double var1 = PriorBelief_Pr_SubpathAndTail(subpath, onetail);
		double var2 = 0;
		for(String cellid:allcells.values()){
			var2+=PriorBelief_Pr_SubpathAndTail(subpath, cellid);
		}
		Pr = var1 / var2;
		return Pr;
	}
	
	public final double PriorBelief_Pr_SubpathAndTail(String[] subpath,String cellid1){
		double Pr = 0;
		double var1 = matcher.countSubpathAndTail(subpath,cellid1,allpaths);
		double var2 = allpaths.size();
		Pr = var1 / var2;
		//logger.info(Pr+"="+var1+"/"+var2);
		//System.out.println(Pr+"="+var1+"/"+var2);
		return Pr;
	}
	
	public final double PriorBelief_Pr_Subpath_Tail(String[] subpath,String cellid1){
		double Pr = 0;
		double var1 = matcher.countSubpath(subpath, allpaths);
		double var2 = matcher.countTail(cellid1, allpaths);
		Pr = var1 / var2;
		//logger.info(Pr+"="+var1+"/"+var2);
		return Pr;
	}
	
	public final double PriorBelief_Pr_Tail(String cellid1){
		double Pr = 0;
		double var1 = matcher.countTail(cellid1, allpaths);
		double var2 = allpaths.size();
		Pr = var1 / var2;
		return Pr;
	}
	
	public String getOutput(){
		return output;
	}

}
