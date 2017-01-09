package me.projects.LISG.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.math3.stat.Frequency;
import org.jfree.util.Log;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.Convertor;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forLog.ILog;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class DataSampler implements ILog{
	public final String tagstr;
	public final int level;
	public final String filepath;
	public final Parameters para;
	public DataSampler(Parameters para) {
		super();
		this.tagstr = para.tagStr;
		this.level = para.level;
		this.filepath = para.filepath;
		this.para = para;
		this.loadData();
		this.bulidFreq();
	}
	
	private LoadingMO data = null;
	private RectEnv rectenv = null;
	private Rectangle rect = null;
	private String[] lines = null;
	private Frequency freq = null;
	public final Frequency getFreq() {
		return freq;
	}

	public final LoadingMO getData() {
		return data;
	}

	public final RectEnv getRectenv() {
		return rectenv;
	}

	public final Rectangle getRect() {
		return rect;
	}

	public final int getLevel() {
		return level;
	}
	
	public final String[] getLines() {
		return lines;
	}

	public final String getTagstr() {
		return tagstr;
	}
	
	private void loadData(){
		logger.info(filepath);
		data = new LoadingMO(filepath);
		final String tag4Trim = para.trimTag;
		rect = data.rectangelTrim(tag4Trim);
		rectenv = new RectEnv(level,rect);
		
		//lines = data.loaddata_bytags(tagstr);
		lines = data.loaddata_totags(tagstr);
		
		System.out.println(para.show());
		logger.info(rectenv.summary());
	}
	
	private void bulidFreq(){
		freq = new Frequency();
		for(String oneline:lines){
			final MovingObject mo = new MovingObject(oneline);
			final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
			freq.addValue(cellid);
		}
	}
	
	public String[] getCellIDsOfTimestamps(final String ObjectID,final String[] timestamps){
		final String[] CellIDsOfTimestamps = new String[timestamps.length];
		for(int i=0;i<timestamps.length;i++){
			for(String oneline:lines){
				final MovingObject mo = new MovingObject(oneline);
				if(ObjectID.equals(mo.getObject_Id())&&timestamps[i].equals(mo.getTimestamp())){
					final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
					CellIDsOfTimestamps[i] = cellid;
				}
			}
		}
		return CellIDsOfTimestamps;
	}
	
	public HashMap<String,String[]> getAllSubpaths(final String[] timestamps){
		final HashMap<String,String[]> AllCellIDsOfTimestamps = new HashMap<String,String[]>();
		for(int i=0;i<timestamps.length;i++){
			for(String oneline:lines){
				final MovingObject mo = new MovingObject(oneline);
				final String ObjectID = mo.getObject_Id();

				if(timestamps[i].equals(mo.getTimestamp())){
					final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
					if(!AllCellIDsOfTimestamps.containsKey(ObjectID)){
						AllCellIDsOfTimestamps.put(ObjectID, new String[timestamps.length]);
					}
					AllCellIDsOfTimestamps.get(ObjectID)[i] = cellid;
				}
			}
		}
		return AllCellIDsOfTimestamps;
	}
	
	public HashMap<String,String> getAllCellsOfTimestamp(final String Timestamp){
		final HashMap<String,String> AllCellsOfTail = new HashMap<String,String>();
			for(String oneline:lines){
				final MovingObject mo = new MovingObject(oneline);
				final String ObjectID = mo.getObject_Id();
				if(Timestamp.equals(mo.getTimestamp())){
					final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
					AllCellsOfTail.put(ObjectID, cellid);
				}
			}
		return AllCellsOfTail;
	}
	
	public HashMap<String,String> getAllCellsOfTail(){
		return getAllCellsOfTimestamp(para.tagTail);
	}
	
	public ArrayList<String> getNormalObjectIDs(){
		final ArrayList<String> AllObjectIDs = new ArrayList<String>();
			for(String oneline:lines){
				final MovingObject mo = new MovingObject(oneline);
				final String ObjectID = mo.getObject_Id();
				if(!AllObjectIDs.contains(ObjectID)){
					AllObjectIDs.add(ObjectID);
				}
			}
		final HashMap<String,Integer> counter =new HashMap<String,Integer>();
		for(String oneline:lines){
			final MovingObject mo = new MovingObject(oneline);
			final String ObjectID = mo.getObject_Id();
			if(counter.containsKey(ObjectID)){
				counter.put(ObjectID,counter.get(ObjectID)+1);
			}else{
				counter.put(ObjectID,1);
			}
		}
		final ArrayList<String> NormalObjectIDs = new ArrayList<String>();
		for(String ObjectID:AllObjectIDs){
			if(counter.get(ObjectID)>=20){
				NormalObjectIDs.add(ObjectID);
			}
		}
		return NormalObjectIDs;
	}
	
	public ArrayList<String> getAllObjectIDs(){
		final ArrayList<String> AllObjectIDs = new ArrayList<String>();
			for(String oneline:lines){
				final MovingObject mo = new MovingObject(oneline);
				final String ObjectID = mo.getObject_Id();
				if(!AllObjectIDs.contains(ObjectID)){
					AllObjectIDs.add(ObjectID);
				}
			}
		return AllObjectIDs;
	}	
	
	public HashSet<String> AllCells(){
		return data.AllCells(lines, rectenv);
	}
	
	public String[] getSnapshot(String timestamp){
		Log.debug(this.getClass().getCanonicalName()+":timestamp="+timestamp);
		return this.data.loadcellids_bytags(timestamp, rectenv);
	}
	
	public ArrayList<String> getTotalCells(){
		Log.debug(this.getClass().getCanonicalName()+":"+level);
		return Convertor.extend("", level);
	}
	
	public ArrayList<String> getExistingCells(){
		return data.getAllCells(para.trimTag, rectenv);
	}
	
	public ArrayList<String> getAllCells(){
		return getExistingCells();
	}
	
	public ArrayList<String[]> getTotalPaths(){
		final ArrayList<String[]> AllPaths = new ArrayList<String[]>();
		final HashMap<String, ArrayList<MovingObject>> allpaths = data.loadAllPaths();
		for(ArrayList<MovingObject> onepath : allpaths.values()){
			AllPaths.add(Convertor.convert(onepath,rectenv));
		}
		return AllPaths;
	}
	
	public HashMap<String,String[]> getAllPaths(){
		//System.out.println("SubPathLength Limit:"+para.pathLength);
		return getProperPaths(para.pathLength);
	}
	
	public HashMap<String,String[]> getProperPaths(final int length){
		final HashMap<String,String[]> AllPaths = new HashMap<String,String[]>();
		final HashMap<String, ArrayList<MovingObject>> allpathsfromdata = data.loadAllPaths();
		for(String ObjectID:allpathsfromdata.keySet()){
			final ArrayList<MovingObject> onepath = allpathsfromdata.get(ObjectID);
			if(onepath.size()>length){
				AllPaths.put(ObjectID, Convertor.convert(onepath,rectenv));
			}
		}
		return AllPaths;
	}
	
	
}
