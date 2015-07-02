package org.experiments.baidumaps;

import org.assistants.dbsp.queries.AbsURL4LBS;
import org.assistants.dbsp.queries.URL4LBS_GeocodingAPI;
import org.constants.Const4Comp;
import org.parameters.I_constant;
import org.projects.datamodel.AbsMapper;
import org.projects.datamodel.PointInEarth;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEnv;
import org.projects.datamodel.SpatialSpliter;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.tools.forString.StringTool;

public class Noprivacyprotection extends AbsExp{

	String location = null;
	RectEnv rectenv = null;
	MovingObject mo = null;
	final String[] dataset;
	
	public Noprivacyprotection(int level,String tag,String[] dataset) {
		super();
		this.rectenv = new RectEnv(level,I_constant.downloader_rectangle);
		this.dataset = dataset;
	}
	
	public Noprivacyprotection(RectEnv rectenv,String tag,String[] dataset) {
		super();
		this.rectenv = rectenv;
		this.dataset = dataset;
	}
	
	public final RectEnv getRectenv() {
		return rectenv;
	}
	
	public void read(){
		read_v2(dataset);
	}
	
	public void read_v2(final String[] buffer){
			for(String oneline:buffer) {
				this.mo = new MovingObject(oneline);
				//System.out.println(JSON.toJSONString(mo));
				//Y lat; Lng X
				this.location = mo.getLat()+","+mo.getLng();
				//System.out.println(location);
				this.elapsedTime += this.oneEncodeElapsedTime();
				//this.elapsedTime += this.oneRequestElapsedTime();
			}
	}
	
	@Override
	public String oneEncode(){
		final SpatialSpliter ss = rectenv.spatialSpliter;
		final AbsMapper mapper = rectenv.mapper;
		final PointInEarth pointInEarth  = new PointInEarth(Double.parseDouble(mo.getLng()),Double.parseDouble(mo.getLat()));
		final PointInPlane pointInMath = mapper.toPlanefromEarth(pointInEarth);
		final String quadtree = ss.quadtreeEncoder(pointInMath);
		//System.out.println(pointInEarth+"->"+pointInMath+";quadtree="+quadtree);
		return quadtree;
	}

	@Override
	public String oneRequest(){
		AbsURL4LBS client = new URL4LBS_GeocodingAPI(this.location);
		return client.operation();
	}
	
	@Override
	public void execute() {
		long startTime=System.nanoTime();   //获取开始时间 
		read();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		System.out.println("ALL Elapsed Time:"+(elapsedTime/Const4Comp.million)+"ms;"+(elapsedTime/Const4Comp.billion)+"s"); 
	}

	
	
	public static void main(String[] args) {
		int l_max = 11;
		
		for(int l=1;l<l_max;l++){
			final RectEnv rectenv = new RectEnv(l,I_constant.downloader_rectangle);
			System.out.println(rectenv.summary());
		}
		
		String results = "";
		//String[] tags = {"1","5","10","19","19"};
		String[] tags = {"1"};
		final LoadingMO data = new LoadingMO("D:\\_workshop\\1338.txt");

		for(int i=0;i<tags.length;i++){
			final String tag = tags[tags.length-1-i];
			
			final String[] dataset = data.loaddata_totags(tag);
			System.out.println(dataset.length);
			
			String time = "";
			for(int l=1;l<l_max;l++){
			//for(int l=11;l>0;l--){	
				final Noprivacyprotection task = new Noprivacyprotection(l,tag,dataset);
				time += task.elapsedTime()+",";
			}
			//System.out.println(time);
			time = StringTool.replaceLastChat("Y"+tag+"=["+time, "]");
			results += time+"\n";
			
			//System.gc();
		}
		
		System.out.println(results);
	}

}
