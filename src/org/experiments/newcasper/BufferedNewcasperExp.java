package org.experiments.newcasper;

import org.experiments.AbsExperiment;
import org.parameters.I_constant;
import org.projects.LocationAnonymizer.GridBasicNewCasper;
import org.projects.LocationAnonymizer.QuadTreeCounter;
import org.projects.caches.CacheBitmap;
import org.projects.datamodel.PointInEarth;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEnv;
import org.projects.measurement.Imeasurable;
import org.projects.measurement.Measurement;
import org.projects.preprocessing.Convertor;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.projects.preprocessing.TagsBatch;
import org.projects.privacymodel.PrivacyProfile;
import org.projects.providers.ProviderPOIDB;
import org.tools.forMatlab.Format4Matlab;

public class BufferedNewcasperExp extends AbsExperiment implements Imeasurable{

	final RectEnv rectenv;
	final String[] dataset;
	final QuadTreeCounter qtcounter;
	final CacheBitmap bufferindex; 
	public BufferedNewcasperExp(RectEnv rectenv,String[] dataset) {
		super();
		this.rectenv = rectenv;
		this.dataset = dataset;
		this.qtcounter = new QuadTreeCounter(rectenv);
		this.bufferindex = new CacheBitmap(rectenv);
	}

	final ProviderPOIDB poidb = new ProviderPOIDB();
	
	public void indexdataset(){
			for(String oneline:dataset) {
				MovingObject mo = new MovingObject(oneline);
				String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);		
				//System.out.println(JSON.toJSONString(mo));
				//Y lat; Lng X
				//String location = mo.getLat()+","+mo.getLng();
				//System.out.println(location);
				//this.elapsedTime += this.oneRequestElapsedTime();
				//System.out.println(cellid);
				qtcounter.putall(cellid);				
			}
	}
	
	public void BasicLA(){
		for(String oneline:dataset) {
			
			MovingObject mo = new MovingObject(oneline);
			PointInEarth pEarth = Convertor.convert(mo);
			PointInPlane pPlane = rectenv.mapper.toPlanefromEarth(pEarth);
			String cellid = rectenv.spatialSpliter.quadtreeEncoder(pPlane);
			
			//System.out.println(cellid+"<---"+JSON.toJSONString(mo));
			//Y lat; Lng X
			
			boolean b = bufferindex.readBit((int)pPlane.getX(), (int)pPlane.getY());
			String result="CR:";
			if(b){
				result+="cached!";
			}else{
				result+=BasicLA2(cellid);
				bufferindex.writeBit((int)pPlane.getX(), (int)pPlane.getY());
				poidb.retrieval(rectenv.spatialSpliter.quadtreeEncoder(pPlane));
			}
			
			this.outputResults.add(result);
		}
	}
	
	public String BasicLA2(String cellid){
		
		GridBasicNewCasper basicla=new GridBasicNewCasper(qtcounter);
		//String cellid=rectenv.quadTreeEncoder(mo);
		//privacy profile
		final int k = 5;
		final int Amin = 2;
		PrivacyProfile privacyprofile = new PrivacyProfile(k,Amin,0);
		String cr = basicla.BasicLA_v1(privacyprofile,cellid).toString();
		return cr;
	}
	
	public static void main(String[] args) {
		final RectEnv rectenv = new RectEnv(8,I_constant.downloader_rectangle);
		System.out.println(rectenv.summary());
		String results = "";
		String counter1 = "";
		String counter2 = "";
		String ratio1 = "";
		String ratio2 = "";
		
		String[] tags;
		//String[] tags = {"1","5","10","19"};
		//String[] tags = {"1","2","3","4","5","6","7","10","19"};
		
		tags = TagsBatch.getContinousTimestamps(20);
		
		//String[] tags = {"1"};
		final LoadingMO data = new LoadingMO("D:\\_workshop\\1338.txt");
		
		//for(int j=0;j<tags.length;j++){
		
			for(int i=0;i<tags.length;i++){
				
				//final String tag = tags[tags.length-1-i];
				final String tag = tags[i];
				final String[] dataset = data.loaddata_bytags(tag);
				System.out.println(tag+"<="+dataset.length);
				final BufferedNewcasperExp task = new BufferedNewcasperExp(rectenv,dataset);
				task.indexdataset();
				task.outputMessages.append(task.qtcounter.summary());
				task.outputMessages.append("\n");
				double ms = Measurement.elapsedTime_ms(task);
				//task.outputMessages.append();
				task.output("1");
				results+=ms+",";
				counter1+=dataset.length+",";
				counter2+=task.poidb.getCounter()+",";
				ratio1+="1.0,";
				ratio2+=((double)task.poidb.getCounter()/(double)dataset.length)+",";
				
			}//for
			
			System.out.println(results);
			System.out.println(Format4Matlab.output4matlab("c1",counter1));
			System.out.println(Format4Matlab.output4matlab("c2",counter2));
			System.out.println(Format4Matlab.output4matlab("r1",ratio1));	
			System.out.println(Format4Matlab.output4matlab("r2",ratio2));	
			
		//}

		
	}

	
	
	@Override
	public void measurable() {
		this.BasicLA();
	}

}
