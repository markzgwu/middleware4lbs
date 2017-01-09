package me.projects.LISG.methods;

import java.io.File;

import com.alibaba.fastjson.JSONObject;

public final class Parameters {
	public final String filepath = filepath();
	public final String tagStr = "20";// timestamp of tail
	public final String trimTag = "20";
	public final String tagTail = "19";
	public int pathLength = 20;
	final public int level;
	final public double Constant_Pr_A;
	
	final public int default_level = 4;
	final public double default_Constant_Pr_A = 0.05;
	
	public Parameters(int level,double constant_Pr_A){
		this.level = level;
		this.Constant_Pr_A = constant_Pr_A;
	}
	
	public Parameters(int level,int pathLength){
		this.level = level;
		this.Constant_Pr_A = default_Constant_Pr_A;
		this.pathLength = pathLength;
	}
	
	public Parameters(int level){
		this.level = level;
		this.Constant_Pr_A = default_Constant_Pr_A;
	}
	
	public Parameters(double constant_Pr_A){
		this.level = default_level;
		this.Constant_Pr_A = constant_Pr_A;
	}
	
	public Parameters(){
		this.level = default_level;
		this.Constant_Pr_A = default_Constant_Pr_A;
	}
	
	public String show(){
		return JSONObject.toJSONString(this);
	}
	
	public String filepath(){
		String filepath = "e:";
		filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
		return filepath;
	}	
}
