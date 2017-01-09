package org.projects.schemes;

import java.util.ArrayList;

import org.projects.LocationAnonymizer.GridBasicNewCasper;
import org.projects.LocationAnonymizer.ILocationAnonymizer;
import org.projects.LocationAnonymizer.QuadTreeCounter;
import org.projects.datamodel.*;
import org.projects.preprocessing.Convertor;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.projects.privacymodel.PrivacyProfile;

public class BufferedNewcasper extends AbsScheme {
	final PrivacyProfile privacyprofile;
	public BufferedNewcasper(final PrivacyProfile privacyprofile){
		this.privacyprofile = privacyprofile;
	}
	
	@Override
	public void init(RectEnv rectenv){
		this.rectenv = rectenv;
		this.qtcounter = new QuadTreeCounter(rectenv);
	}	
	
	@Override
	public void download(){
		index();
		
		for(String oneline:dataset){
			bufferframework(oneline);
		}
	}
	
	void bufferframework(String oneline){
		this.demandcells++;

		final MovingObject mo = new MovingObject(oneline);
		final PointInPlane pPlane = MovingObjectBatch.mapping(mo, rectenv);
		//final Grid tmpgrid1 = Convertor.convert(pPlane);
		//final boolean isCached = bufferindex.readBit(tmpgrid1.i,tmpgrid1.j);
		
		final String cellid = rectenv.spatialSpliter.quadtreeEncoder(pPlane);
		final boolean isCached = cachezone.check(cellid);

		if(isCached){
			cachezone.load(cellid);
		}else{

			final ArrayList<String> cellids =  cloaking(cellid);
			
			for(String cid:cellids){
				downloadcells++;
				String data = dbprovider.retrieval(cid);
				cachezone.save(cid,data);
				//final PointInPlane pPlane1 = rectenv.spatialSpliter.quadtreeDecoder(cid);
				//final Grid tmpgrid2 = Convertor.convert(pPlane1);
				//bufferindex.writeBit(tmpgrid2.i, tmpgrid2.j);
			}

		}

	}
	
	private ArrayList<String> cloaking(String cellid){
		ArrayList<String> nodeids = BasicLA2(cellid);
		return nodeids;
	}	
	
	private QuadTreeCounter qtcounter = null;

	private ArrayList<String> BasicLA2(String cellid){
		final ILocationAnonymizer basicla=new GridBasicNewCasper(qtcounter);
		//String cellid=rectenv.quadTreeEncoder(mo);
		//privacy profile
		//final int k = 10;
		//final int Amin = 2;
		final ArrayList<String> anonymityset = basicla.anonymization(privacyprofile,cellid);
		//System.out.println(LogJSON.json(anonymityset));
		final ArrayList<String> extendanonymityset = Convertor.extend(anonymityset, rectenv.getLevel().level);
		//logger.info("size:"+extendanonymityset.size()+";"+LogJSON.json(extendanonymityset));
		//System.out.println("cellids.size() = "+cellids.size());
		return extendanonymityset;
	}

	
	public void index(){
		for(String oneline:dataset) {
			
			MovingObject mo = new MovingObject(oneline);
			PointInEarth pEarth = Convertor.convert(mo);
			PointInPlane pPlane = rectenv.mapper.toPlanefromEarth(pEarth);
			String cellid = rectenv.spatialSpliter.quadtreeEncoder(pPlane);			
			
			//System.out.println(JSON.toJSONString(mo));
			//Y lat; Lng X
			//String location = mo.getLat()+","+mo.getLng();
			//System.out.println(location);
			//this.elapsedTime += this.oneRequestElapsedTime();
			//System.out.println(cellid);
			qtcounter.putall(cellid);				
		}
	}	
	
}
