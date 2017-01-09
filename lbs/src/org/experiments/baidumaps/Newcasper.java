package org.experiments.baidumaps;

import java.io.BufferedReader;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.assistants.dbsp.queries.AbsURL4LBS;
import org.assistants.dbsp.queries.URL4LBS_GeocodingAPI;
import org.constants.Const4Comp;
import org.parameters.I_constant;
import org.projects.datamodel.AbsMapper;
import org.projects.datamodel.PointInEarth;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEnv;
import org.projects.datamodel.SpatialSpliter;
import org.projects.preprocessing.MovingObject;
import org.tools.forFile.FileTool;

public class Newcasper extends AbsExp{

	String location;
	final HashMap<String,Integer> counter = new HashMap<String,Integer>();
	
	void put(String quadtreecode){
		if(counter.containsKey(quadtreecode)){
			Integer n = counter.get(quadtreecode);
			counter.put(quadtreecode, n+1);
		}else{
			counter.put(quadtreecode, 1);
		}
	}
	
	public void read(){
		
		final int level = 8;
		final RectEnv rectenv = new RectEnv(level,I_constant.downloader_rectangle);
		final SpatialSpliter ss = rectenv.spatialSpliter;
		final AbsMapper mapper = rectenv.mapper;
		
		final String filepath = "D:\\_workshop\\1336.txt";
		try {
			BufferedReader br = FileTool.getBufferedReader(filepath);
			String oneline = br.readLine();
			//int count = 0;
			System.out.println(oneline);//Read the first line;
			while(StringUtils.isNotBlank( oneline = br.readLine()) ){
				MovingObject mo = new MovingObject(oneline);
				
				if(mo.getTimestamp().equals("1")){
					break;
				}
				
				//System.out.println(JSON.toJSONString(mo));
				//Y lat; Lng X
				this.location = mo.getLat()+","+mo.getLng();
				System.out.println(location);
				PointInEarth pointInEarth  = new PointInEarth(Double.parseDouble(mo.getLng()),Double.parseDouble(mo.getLat()));
				PointInPlane pointInMath = mapper.toPlanefromEarth(pointInEarth);
				System.out.println(pointInEarth+"->"+pointInMath);
				
				String quadtreecode = ss.quadtreeEncoder(pointInMath);
				put(quadtreecode);
				System.out.println("quadtree="+quadtreecode);
				//this.elapsedTime += this.oneRequestElapsedTime();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(counter);
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
		
		new Newcasper().elapsedTime();

	}

	@Override
	public String oneEncode() {
		return null;
	}

}
