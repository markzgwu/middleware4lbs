package org.projects.measurement;

import org.constants.Const4Comp;

public final class Measurement {
	public static double elapsedTime_ns(Imeasurable method){
		long startTime=System.nanoTime();   //��ȡ��ʼʱ�� 
		method.measurable();
		long endTime=System.nanoTime(); //��ȡ����ʱ��  
		long elapsedTime = (endTime-startTime);
		//System.out.println("Elapsed Time:"+(elapsedTime/Const4Comp.million)+"ms;"+(elapsedTime/Const4Comp.billion)+"s"); 
		return elapsedTime;
	}
	
	public static double elapsedTime_ms(Imeasurable method){
		return toMSfromNS(elapsedTime_ns(method));
	}
	
	public static double toMSfromNS(double ns){
		return ns/Const4Comp.million;
	}
	
	public static double toSfromNS(double ns){
		return ns/Const4Comp.billion;
	}
	
	public static String formatTime(double ns){
		String formattime = "Elapsed Time:"+toMSfromNS(ns)+"ms;"+toSfromNS(ns)+"s";
		return formattime;
	}
	public static String time(Imeasurable method){
		return formatTime(elapsedTime_ns(method));
	}
	
}
