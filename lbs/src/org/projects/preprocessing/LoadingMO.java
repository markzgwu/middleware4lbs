package org.projects.preprocessing;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.projects.datamodel.RectEnv;
import org.tools.forFile.FileTool;
import org.tools.forLog.ILog;
import org.tools.forString.StringTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class LoadingMO implements ILog{
	final String filepath;
	
	public LoadingMO(String filepath) {
		super();
		this.filepath = filepath;
		logger.info(filepath);
	}
	
	//读取从开始到tag处的的所有记录
	//public Rectangle rectangelTrim(final int tag){
	//	return rectangelTrim(loaddata_totags(String.valueOf(tag)));
	//}
	
	public Rectangle rectangelTrim(final String tag){
		return rectangelTrim(loaddata_totags(tag));
	}
	
	public HashSet<String> AllCells(final String[] dataset,final RectEnv rectenv){
		HashSet<String> allcells = new HashSet<String>();
		MovingObject mo = null;
		for(String oneline:dataset){
			if(StringUtils.isNotBlank(oneline)){
				mo = new MovingObject(oneline);
				String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
				if(StringUtils.isNotBlank(cellid)){
					allcells.add(cellid);
				}
			}			
		}
		return allcells;
	}
	
	
	
	public ArrayList<String> getAllCells(final String[] dataset,final RectEnv rectenv){
		final HashSet<String> sourceallcells = AllCells(dataset,rectenv);
		final ArrayList<String> allcells = new ArrayList<String>();
		for(String cellid:sourceallcells){
			allcells.add(cellid);
		}
		return allcells;
	}
	
	public ArrayList<String> getAllCells(final String tag,final RectEnv rectenv){
		return getAllCells(loaddata_totags(tag),rectenv);
	}
	
	public Rectangle rectangelTrim(final String[] dataset){
		Double latmin=null,latmax=null,lngmin=null,lngmax=null;
		Rectangle rect = null;
		MovingObject mo = null;
		for(String oneline:dataset){
			if(StringUtils.isNotBlank(oneline)){
				mo = new MovingObject(oneline);
				double lat = Double.valueOf(mo.getLat());
				double lng = Double.valueOf(mo.getLng());
				
				if(latmin==null){
					latmin = lat;
				}else{
					latmin = Math.min(latmin, lat);
				}
				
				if(lngmin==null){
					lngmin = lng;
				}else{
					lngmin = Math.min(lngmin, lng);
				}
				
				if(latmax==null){
					latmax = lat;
				}else{
					latmax = Math.max(latmax, lat);
				}				
				
				if(lngmax==null){
					lngmax = lng;
				}else{
					lngmax = Math.max(lngmax, lng);
				}
				
			}			
			
		}
		double margin = 0.00001;//防止出现正好在最外边框上的点
		LocationCoordinate leftbottom = new LocationCoordinate(lngmin-margin,latmin-margin);
		LocationCoordinate righttop = new LocationCoordinate(lngmax+margin,latmax+margin);
		rect = new Rectangle(leftbottom, righttop);
		return rect;				
	}
	
	public final String[] loadcellids_bytags(final String tag,final RectEnv rectenv){
		final ArrayList<String> dataset = new ArrayList<String>();
		MovingObject mo = null;
		try {
			BufferedReader br = FileTool.getBufferedReader(filepath);
			String oneline = br.readLine();
			while(( oneline = br.readLine()) != null ){
				if(StringUtils.isNotBlank(oneline)){
					mo = new MovingObject(oneline);
					if(mo.getTimestamp().equals(tag)){
						dataset.add(MovingObjectBatch.mapping4cellid(mo, rectenv));
					}
				}
			}			
			br.close();
			br = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return StringTool.toStringArray(dataset);				
	}	
	
	//读取tag处的记录
	public String[] loaddata_bytags(final String tag){
		final ArrayList<String> dataset = new ArrayList<String>();
		MovingObject mo = null;
		try {
			BufferedReader br = FileTool.getBufferedReader(filepath);
			String oneline = br.readLine();
			while(( oneline = br.readLine()) != null ){
				if(StringUtils.isNotBlank(oneline)){
					mo = new MovingObject(oneline);
					if(mo.getTimestamp().equals(tag)){
						dataset.add(oneline);
					}
				}
			}			
			br.close();
			br = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return StringTool.toStringArray(dataset);				
	}
	
	//读取从开始到tag处的的所有记录
	public String[] loaddata_totags(final String tag){
		final ArrayList<String> dataset = new ArrayList<String>();
		MovingObject mo = null;
		try {
			BufferedReader br = FileTool.getBufferedReader(filepath);
			String oneline = br.readLine();
			while(( oneline = br.readLine()) != null ){
				if(StringUtils.isNotBlank(oneline)){
					mo = new MovingObject(oneline);
					if(mo.getTimestamp().equals(tag)){
						break;
					}
					dataset.add(oneline);
				}
			}			
			br.close();
			br = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return StringTool.toStringArray(dataset);				
	}
	
	//读取tag处的记录
	public String[] loaddata_byobjectids(final String objectid){
		final ArrayList<String> dataset = new ArrayList<String>();
		MovingObject mo = null;
		try {
			BufferedReader br = FileTool.getBufferedReader(filepath);
			String oneline = br.readLine();
			while(( oneline = br.readLine()) != null ){
				if(StringUtils.isNotBlank(oneline)){
					mo = new MovingObject(oneline);
					if(mo.getObject_Id().equals(objectid)){
						dataset.add(oneline);
					}
				}
			}			
			br.close();
			br = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return StringTool.toStringArray(dataset);				
	}
	
	//按照轨迹读取记录
	public HashMap<String,ArrayList<MovingObject>> loadAllPaths(){
		final HashMap<String,ArrayList<MovingObject>> paths = new HashMap<String,ArrayList<MovingObject>>();
		MovingObject mo = null;
		try {
			BufferedReader br = FileTool.getBufferedReader(filepath);
			String oneline = br.readLine();
			while(( oneline = br.readLine()) != null ){
				if(StringUtils.isNotBlank(oneline)){
					mo = new MovingObject(oneline);
					String key = mo.Object_Id;
					if(paths.containsKey(key)){
						paths.get(key).add(mo);
					}else{
						final ArrayList<MovingObject> onepath = new ArrayList<MovingObject>();
						onepath.add(mo);
						paths.put(key, onepath);
					}
				}
			}			
			br.close();
			br = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paths;				
	}	

}
