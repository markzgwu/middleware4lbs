package org.experiments.baidumaps;

import org.constants.Const4Comp;
import org.parameters.I_constant;
import org.projects.LocationAnonymizer.*;
import org.projects.datamodel.AbsMapper;
import org.projects.datamodel.PointInEarth;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEnv;
import org.projects.datamodel.SpatialSpliter;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.privacymodel.PrivacyProfile;

public class NewcasperV3{

	String location = null;
	RectEnv rectenv = null;

	final String[] dataset;
	
	public NewcasperV3(int level,String tag,String[] dataset) {
		super();
		this.rectenv = new RectEnv(level,I_constant.downloader_rectangle);
		this.dataset = dataset;
	}
	
	public NewcasperV3(RectEnv rectenv,String tag,String[] dataset) {
		super();
		this.rectenv = rectenv;
		this.dataset = dataset;
	}
	
	public final RectEnv getRectenv() {
		return rectenv;
	}
	
	public void read(){
		read(dataset);
	}
	
	QuadTreeCounter qtcounter;
	
	public void read(final String[] buffer){
		    qtcounter = new QuadTreeCounter(rectenv);

			for(String oneline:buffer) {
				MovingObject mo = new MovingObject(oneline);
				//System.out.println(JSON.toJSONString(mo));
				//Y lat; Lng X
				this.location = mo.getLat()+","+mo.getLng();
				//System.out.println(location);
				//this.elapsedTime += this.oneRequestElapsedTime();
				String quadtreecode = oneEncode(mo);
				//System.out.println(quadtreecode);
				qtcounter.putall(quadtreecode);				
			}
	}

	public String oneEncode(MovingObject mo){
		final SpatialSpliter ss = rectenv.spatialSpliter;
		final AbsMapper mapper = rectenv.mapper;
		final PointInEarth pointInEarth  = new PointInEarth(Double.parseDouble(mo.getLng()),Double.parseDouble(mo.getLat()));
		final PointInPlane pointInMath = mapper.toPlanefromEarth(pointInEarth);
		final String quadtree = ss.quadtreeEncoder(pointInMath);
		//System.out.println(pointInEarth+"->"+pointInMath+";quadtree="+quadtree);
		return quadtree;
	}
	
	public void BasicLA(){
		for(String oneline:dataset) {
			MovingObject mo = new MovingObject(oneline);
			//System.out.println(JSON.toJSONString(mo));
			//Y lat; Lng X
			this.location = mo.getLat()+","+mo.getLng();
			System.out.println("CR:"+BasicLA2(mo));
		}
	}
	
	public String BasicLA2(MovingObject mo){
		GridBasicNewCasper basicla=new GridBasicNewCasper(qtcounter);
		String cellid=oneEncode(mo);
		//privacy profile
		final int k = 5;
		final int Amin = 2;
		String cr = basicla.BasicLA_v1(new PrivacyProfile(k,Amin,0),cellid).toString();
		return cr;
	}	
	
	public String BasicLA1(MovingObject mo){
		int k=5;
		String cellid=oneEncode(mo);
		int N = qtcounter.get(cellid);
		String cr = "";
		if(N>k){
			cr = cellid;
		}else{
			
		}
		
		return cr;
	}
	
	public void execute() {
		long startTime=System.nanoTime();   //获取开始时间 
		read();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		System.out.println("ALL Elapsed Time:"+(elapsedTime/Const4Comp.million)+"ms;"+(elapsedTime/Const4Comp.billion)+"s"); 
	}

	
	public static void main(String[] args) {
		final RectEnv rectenv = new RectEnv(8,I_constant.downloader_rectangle);
		
		System.out.println(rectenv.summary());
		
		String results = "";
		//String[] tags = {"1","5","10","19","19"};
		String[] tags = {"19"};
		final LoadingMO data = new LoadingMO("D:\\_workshop\\1338.txt");

		for(int i=0;i<tags.length;i++){
			final String tag = tags[tags.length-1-i];
			
			final String[] dataset = data.loaddata_totags(tag);
			System.out.println(dataset.length);
			
			//String time = "";
			//for(int l=1;l<11;l++){
			//for(int l=11;l>0;l--){
				final NewcasperV3 task = new NewcasperV3(rectenv,tag,dataset);
				task.read();
				System.out.println(task.qtcounter.summary());
				task.BasicLA();
				//time += task.elapsedTime()+",";
			//}
			//System.out.println(time);
			//time = StringTool.replaceLastChat("Y"+tag+"=["+time, "]");
			//results += time+"\n";
			
			//System.gc();
		}
		
		System.out.println(results);
		
	}

}
