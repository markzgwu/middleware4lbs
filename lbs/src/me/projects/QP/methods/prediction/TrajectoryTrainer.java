package me.projects.QP.methods.prediction;

import java.io.File;

import org.apache.commons.math3.stat.Frequency;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forLog.ILog;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class TrajectoryTrainer implements ILog{
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
	String objectid = "0";
	
	Frequency bulidFreq(String tagstr){
		final Frequency frequency = new Frequency();
		//double LR1 = 1;
		final String[] lines = data.loaddata_byobjectids(objectid);
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
	
	public static void main(String[] args) {
		final TrajectoryTrainer task = new TrajectoryTrainer();
		task.loadDataSource();
	}

}
