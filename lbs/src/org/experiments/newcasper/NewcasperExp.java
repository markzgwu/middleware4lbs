package org.experiments.newcasper;

import org.experiments.AbsExperiment;
import org.parameters.I_constant;
import org.projects.LocationAnonymizer.GridBasicNewCasper;
import org.projects.LocationAnonymizer.QuadTreeCounter;
import org.projects.datamodel.RectEnv;
import org.projects.measurement.Imeasurable;
import org.projects.measurement.Measurement;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.projects.privacymodel.PrivacyProfile;

public class NewcasperExp extends AbsExperiment implements Imeasurable{

	final RectEnv rectenv;
	final String[] dataset;
	final QuadTreeCounter qtcounter;
	public NewcasperExp(RectEnv rectenv,String tag,String[] dataset) {
		super();
		this.rectenv = rectenv;
		this.dataset = dataset;
		this.qtcounter = new QuadTreeCounter(rectenv);
	}

	public void indexdataset(){
			for(String oneline:dataset) {
				MovingObject mo = new MovingObject(oneline);
				String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
				//System.out.println(JSON.toJSONString(mo));
				//Y lat; Lng X
				//String location = mo.getLat()+","+mo.getLng();
				//System.out.println(location);
				//this.elapsedTime += this.oneRequestElapsedTime();

				//System.out.println(this.getClass().getCanonicalName()+":cellid:"+cellid);
				qtcounter.putall(cellid);				
			}
	}
	
	public void BasicLA(){
		for(String oneline:dataset) {
			MovingObject mo = new MovingObject(oneline);
			//System.out.println(JSON.toJSONString(mo));
			//Y lat; Lng X
			//String jsonmo = JSON.toJSONString(mo);
			//System.out.println(jsonmo+";CR:"+BasicLA2(mo));
			String result="CR:"+BasicLA2(mo);
			//System.out.println(result);
			this.outputResults.add(result);
		}
	}
	
	public String BasicLA2(MovingObject mo){
		GridBasicNewCasper basicla=new GridBasicNewCasper(qtcounter);
		String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
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
		//String[] tags = {"1","5","10","19","19"};
		String[] tags = {"1"};
		final LoadingMO data = new LoadingMO("D:\\_workshop\\1338.txt");
		for(int i=0;i<tags.length;i++){
			final String tag = tags[tags.length-1-i];
			final String[] dataset = data.loaddata_totags(tag);
			System.out.println("dataset.length="+dataset.length);
			final NewcasperExp task = new NewcasperExp(rectenv,tag,dataset);
			task.indexdataset();
			task.outputMessages.append(task.qtcounter.summary());
			task.outputMessages.append("\n");
			task.outputMessages.append(Measurement.time(task));
			task.output("1");
			task.output("2");
		}
		
		System.out.println(results);
		
	}

	@Override
	public void measurable() {
		this.BasicLA();
	}

}
