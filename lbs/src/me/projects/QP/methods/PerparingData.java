package me.projects.QP.methods;

import java.io.File;

import me.projects.toolkit.ToolkitFrequency;

import org.apache.commons.math3.stat.Frequency;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class PerparingData implements ILog{

	LoadingMO data = null;
	RectEnv rectenv = null;
	Rectangle rect = null;
	int level = 0;
	void mount(int level){
		this.level = level;
	}
	
	void loadDataSource(){
		String filepath = "d:\\";
		//final String filepath = "..";
		filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
		//filepath+=File.separator+"_workshop"+File.separator+"1336.txt";
		data = new LoadingMO(filepath);
		final String tag = "20";
		rect = data.rectangelTrim(tag);
	}
	
	//final int max_objectid = 19+1;
	String title = null;
	final StringBuffer result = new StringBuffer();
	//统计每个网格单元内的请求次数
	Frequency bulidFreq(String tagstr){
		final Frequency frequency = new Frequency();
		//double LR1 = 1;
		final String[] lines = data.loaddata_bytags(tagstr);
		//final String[] lines = data.loaddata_totags(tagstr);
		//final String[] cellids = new String[lines.length];
		//System.out.println("lines.length:"+lines.length);
		
		for(String oneline:lines){
			final MovingObject mo = new MovingObject(oneline);
			final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
			frequency.addValue(cellid);
		}
		return frequency;
	}
	
	void printFreq(final Frequency freq){
		System.out.println(freq);
		System.out.println(freq.getUniqueCount()+";"+freq.getSumFreq());
	}
	
	void printResult(){
		String title = "empty";
		String output = Format4Matlab.output4matlab(title, result.toString());
		System.out.println(output);
	}
	
	String worker(){
		String output = "";
		final int end_tag = 20;
		final int start_tag = 0;
		rectenv = new RectEnv(level,rect);
		logger.info(rectenv.summary());
		for(int tag=start_tag;tag<end_tag;tag++){
			final String tagstr = String.valueOf(tag);
			final Frequency freq = bulidFreq(tagstr);
			double entropy = ToolkitFrequency.getEntropy(freq);
			output+=entropy+",";
			//printFreq(freq);
		}
		output = Format4Matlab.output4matlab(title, output);
		return output;
	}
	
	public static void main(String[] args) {
		final PerparingData task = new PerparingData();
		task.loadDataSource();
		//double result = 0;
		//String result = "";
		String alloutput = "";
		for(int l=2;l<4;l++){
			task.mount(l);
			task.title = "L"+l;
			alloutput += task.worker()+"\n";
			//result = task.worker("L"+l);
			//output+=result+"\n";
		}
		System.out.println(alloutput);
	}

}
