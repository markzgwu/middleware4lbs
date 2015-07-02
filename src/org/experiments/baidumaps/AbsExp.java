package org.experiments.baidumaps;

import org.constants.Const4Comp;

public abstract class AbsExp {
	final StringBuffer messages = new StringBuffer();
	double elapsedTime = 0;
	String quadtreecode;
	public abstract String oneEncode();
	public double oneEncodeElapsedTime(){
		long startTime=System.nanoTime();   //��ȡ��ʼʱ�� 
		quadtreecode=oneEncode();
		long endTime=System.nanoTime(); //��ȡ����ʱ��  
		long elapsedTime = (endTime-startTime);
		//System.out.println("Elapsed Time:"+(elapsedTime/Const4Comp.million)+"ms;"+(elapsedTime/Const4Comp.billion)+"s"); 
		return elapsedTime;
	}	
	
	public abstract String oneRequest();
	public double oneRequestElapsedTime(){
		long startTime=System.nanoTime();   //��ȡ��ʼʱ�� 
		oneRequest();
		long endTime=System.nanoTime(); //��ȡ����ʱ��  
		long elapsedTime = (endTime-startTime);
		//System.out.println("Elapsed Time:"+(elapsedTime/Const4Comp.million)+"ms;"+(elapsedTime/Const4Comp.billion)+"s"); 
		return elapsedTime;
	}
	
	public abstract void execute();
	public double elapsedTime(){
		execute();
		System.out.println("Sum Elapsed Time:"+(elapsedTime/Const4Comp.million)+"ms;"+(elapsedTime/Const4Comp.billion)+"s");
		return (elapsedTime/Const4Comp.million);
	}
}
