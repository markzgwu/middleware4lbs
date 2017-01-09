package me.projects.QP.methods.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import me.projects.LISG.methods.DataSampler;
import me.projects.QP.methods.CacheTable;
import me.projects.QP.methods.QPResult;
import me.projects.QP.methods.SimpleBloomFilter;
import me.projects.toolkit.ToolkitMetric;
import me.projects.toolkit.ToolkitNumber;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public final class CRPrediction extends AbsCRPrediction{


	
	public CRPrediction(DataSampler datasampler) {
		super(datasampler);
		// TODO Auto-generated constructor stub
	}

	//final int max_objectid = 19+1;
	
	public HashMap<String,String[]> allpaths;
	public HashMap<String,String> alltails;
	public HashSet<String> tailset,cellset;
	
	final HashMap<String,Double> CachePostBeliefs = CacheTable.INSTANCE.CachePostBeliefs;
	final HashSet<String> ZeroPostBeliefsInSet = CacheTable.INSTANCE.ZeroPostBeliefsInSet;
	//final AbsMatcher matcher = new StrictMatcher();
	final SubPathMatcher matcher = new SubPathMatcher();

	public void setup(){
		allpaths=datasampler.getAllPaths();
		//logger.info(allpaths.toString());
		alltails=getAllTails();
		//logger.info(allcells.toString());
		tailset=getTailSet();
		cellset=datasampler.AllCells();
		System.out.println("allpaths:"+allpaths.size()+";alltails"+alltails.size());
	}
	
	HashMap<String,String> getAllTails(){
		//return this.datasampler.getAllCells();
		HashMap<String,String> alltails = new HashMap<String,String>();
		for(String ObjectID : allpaths.keySet()){
			String[] onepath = allpaths.get(ObjectID);
			String onetail = onepath[onepath.length-1];
			alltails.put(ObjectID,onetail);
		}
		return alltails;
	}
	
	HashSet<String> getTailSet(){
		return new HashSet<String>(alltails.values());
	}
	
	String output = "";
	
	double stat_count=0;
	double outTestResult = 0;
	
	public double checkrealpath(final String ObjectID,final String[] realsubpath){
		final String realtail = alltails.get(ObjectID);
		if(StringUtils.isBlank(realtail)){
			System.out.println("WARN: NO TAIL! ObjectID="+ObjectID);
		}
		double postpr = oneTest(realtail, realsubpath);
		return postpr;
	}
	
	public QPResult check(final CRSequence CRSequence){
		final HashMap<String,Double> details = check_postpr_details(CRSequence);
		final int length = details.size();
		final String[] predictionpaths = new String[length];
		final double[] postprs = new double[length];
		ExtractDetails(predictionpaths,postprs,details);
		//final double[] postpr = check_postpr(CRSequence);
		final QPResult oneQPResult = new QPResult();
		oneQPResult.PredictionPaths = predictionpaths;
		oneQPResult.PostBeliefs = postprs;
		oneQPResult.QPsafety = postprs.length;
		oneQPResult.Entropy = ToolkitMetric.getEntropy(postprs);
		oneQPResult.Averagepostpr = ToolkitMetric.getAverage(postprs);
		return oneQPResult;
	}
	
	public double[] check_postpr(final CRSequence CRSequence){
		final String[] strsubpathlist = CRSequence.decompose();
		//StringTool.Show(strsubpathlist);
		final ArrayList<Double> postpr  = new ArrayList<Double>();
		for(String testtail:tailset){
			for(String strsubpath:strsubpathlist){
				//System.out.println("checksafety SUBPATH :"+strsubpath);
				String[] subpath = strsubpath.split(";");
				double testpostpr = oneTest(testtail, subpath);
				if(testpostpr>0){
					postpr.add(testpostpr);
				}
			}
		}
		return ToolkitNumber.convert(postpr);
	}
	
	HashMap<String,Double> check_postpr_details(final CRSequence CRSequence){
		final HashMap<String,Double> details = new HashMap<String,Double>();
		for(String testtail:tailset){
			final Double testpostpr = PosteriorBelief_CR(testtail, CRSequence.CloakingRegionList);
			if(testpostpr>0){
				details.put(testtail, testpostpr);
			}
		}
		return details;
	}	
	
	HashMap<String,Double> check_postpr_details_old(final CRSequence CRSequence){
		final String[] strsubpathlist = CRSequence.decompose();
		//StringTool.Show(strsubpathlist);
		//int n = strsubpathlist.length*allcells.size();
		HashMap<String,Double> details = new HashMap<String,Double>();
		for(String testtail:tailset){
			for(String strsubpath:strsubpathlist){
				//System.out.println("checksafety SUBPATH :"+strsubpath);
				String[] subpath = strsubpath.split(";");
				String key = strsubpath+"->"+testtail;
				Double testpostpr = oneTest(testtail, subpath);
				if(testpostpr>0){
					details.put(key, testpostpr);
				}
			}
		}
		return details;
	}
	
	public void ExtractDetails(final String[] subpaths, final double[] postprs, final HashMap<String,Double> postpr_details){
		int i=0;
		for(String subpath:postpr_details.keySet()){
			subpaths[i]=subpath;
			postprs[i]=postpr_details.get(subpath);
			i++;
		}
	}
	
	public double check_averagepostpr(final CRSequence CRSequence){
		double[] postpr = check_postpr(CRSequence);
		System.out.println("check_averagepostpr outputs pr:"+ToolkitNumber.show(postpr));
		double r = ToolkitMetric.getAverage(postpr);
		return r;
	}
	
	public double check_entropy(final CRSequence CRSequence){
		double[] postpr = check_postpr(CRSequence);
		double r = ToolkitMetric.getEntropy(postpr);
		return r;
	}
	
	String oneDecision(final String[] onepath){
		String result = "";
		String[] currentpath = onepath;
		
		//获取测试数据
		String[] testSubpath = PathSpliter.getRealsubpath(0, 2, currentpath);
		String testTail = PathSpliter.getTail(currentpath);
		
		//String debug = "DEBUG:\n";
		
		outTestResult = oneTest(testTail,testSubpath);
		//System.out.println(j+":"+showHistory+" "+tmpcellid1+":"+outresult+";"+this.datasampler.getFreq().getPct(tmpcellid1));
		output+=outTestResult+",";
		
		//决策方法
		String estimated_tail = "unknown";
		double estimated_PosteriorBelief_Pr = 0;
		final double threshold = 0;
		if(outTestResult>threshold){
			estimated_PosteriorBelief_Pr = outTestResult;
			estimated_tail = testTail;
			stat_count++;
		}
		result=estimated_tail+";"+estimated_PosteriorBelief_Pr;

		return result;
	}
	
	public double oneTest(final String testTail,final String[] testSubpath){
		double onetest = 0;
		boolean b = true;
		for(String cell:testSubpath){
			if(!cellset.contains(cell)){
				b = false;
				break;
			}
		}
		
		if(b){
			onetest = oneTestExact(testTail,testSubpath);
		}
		return onetest;
		
	}
	
	public double oneTestExact(final String testTail,final String[] testSubpath){
		double onetest = 0;
		String key = PathSpliter.getPredictionPath(testTail,testSubpath);
		
		if(ZeroPostBeliefsInSet.contains(key)){
			return 0;
		}else{
			if(CachePostBeliefs.containsKey(key)){
				onetest = CachePostBeliefs.get(key);
			}else{
				onetest = PosteriorBelief_Pr(testTail,testSubpath);
				if(onetest>0){
					CachePostBeliefs.put(key, onetest);
				}else{
					ZeroPostBeliefsInSet.add(key);
				}
						
			}
		}

		return onetest;
	}	
	
	public double oneTestBloomFilter(final String testTail,final String[] testSubpath){
		double onetest = 0;
		String key = PathSpliter.getPredictionPath(testTail,testSubpath);
		final HashMap<String,Double> CachePostBeliefs = CacheTable.INSTANCE.CachePostBeliefs;
		final SimpleBloomFilter ZeroPostBeliefs = CacheTable.INSTANCE.ZeroPostBeliefs;
		if(ZeroPostBeliefs.contains(key)){
			onetest = 0;
		}else if(CachePostBeliefs.containsKey(key)){
			onetest = CachePostBeliefs.get(key);
		}else{
			onetest = PosteriorBelief_Pr(testTail,testSubpath);
			if(onetest > 0){
				CachePostBeliefs.put(key, onetest);
			}else{
				ZeroPostBeliefs.add(key);
			}
			
		}
		return onetest;
	}
	
	public final double PosteriorBelief_CR(final String onetail,final ArrayList<String[]> cloakingregionlist){
		//System.out.println(onetail+"\n"+JSON.toJSONString(cloakingregionlist));
		double Pr = 0;
		double var1 = matcher.countCloakingRegionListAndTail(cloakingregionlist,onetail,allpaths);
		double var2 = matcher.countCloakingRegionList(cloakingregionlist, allpaths);
		
		if(var2==0){
			//System.out.println("WARN:subpath="+JSONArray.toJSONString(subpath)+";Tail="+onetail);
		}else{
			Pr = var1 / var2;
		}
		//System.out.println(Pr+"="+var1+"/"+var2);
		return round(Pr);
	}
	
	public final double PosteriorBelief_Pr(final String onetail,final String[] subpath){
		return PosteriorBelief_Pr3(onetail,subpath);
	}
	
	public final double PosteriorBelief_Pr3(final String onetail,final String[] subpath){
		double Pr = 0;
		double var1 = matcher.countSubpathAndTail(subpath,onetail,allpaths);
		double var2 = matcher.countSubpath(subpath, allpaths);
		if(var2==0){
			//System.out.println("WARN:subpath="+JSONArray.toJSONString(subpath)+";Tail="+onetail);
		}else{
			Pr = var1 / var2;
		}
		return round(Pr);
	}
	
	public final double PosteriorBelief_Pr2(final String onetail,final String[] subpath){
		double Pr = 0;
		double var1 = matcher.countSubpathAndTail(subpath,onetail,allpaths);
		double var2 = 0;
		for(String tail:tailset){
			var2+=matcher.countSubpathAndTail(subpath,tail,allpaths);
		}
		if(var2==0){
			//System.out.println("WARN:subpath="+JSONArray.toJSONString(subpath)+";Tail="+onetail);
		}else{
			Pr = var1 / var2;
		}
		return round(Pr);
	}	
	
	public final double PosteriorBelief_Pr1(final String onetail,final String[] subpath){
		double Pr = 0;
		double var1 = PriorBelief_Pr_SubpathAndTail(subpath, onetail);
		double var2 = 0;
		for(String tail:tailset){
			var2+=PriorBelief_Pr_SubpathAndTail(subpath, tail);
		}
		if(var2==0){
			//System.out.println("WARN:subpath="+JSONArray.toJSONString(subpath)+";Tail="+onetail);
		}else{
			Pr = var1 / var2;
		}
		return round(Pr);
	}
	
	protected final double PriorBelief_Pr_SubpathAndTail(String[] subpath,String tail){
		double Pr = 0;
		double var1 = matcher.countSubpathAndTail(subpath,tail,allpaths);
		double var2 = allpaths.size();
		Pr = var1 / var2;
		//logger.info(Pr+"="+var1+"/"+var2);
		//System.out.println(Pr+"="+var1+"/"+var2);
		return Pr;
	}
	
	protected final double PriorBelief_Pr_Subpath_Tail(String[] subpath,String cellid1){
		double Pr = 0;
		double var1 = matcher.countSubpath(subpath, allpaths);
		double var2 = matcher.countTail(cellid1, allpaths);
		Pr = var1 / var2;
		//logger.info(Pr+"="+var1+"/"+var2);
		return Pr;
	}
	
	protected final double PriorBelief_Pr_Tail(String cellid1){
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
