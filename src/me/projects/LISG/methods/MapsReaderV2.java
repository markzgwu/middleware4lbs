package me.projects.LISG.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.math3.stat.Frequency;
import org.projects.preprocessing.Convertor;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;

public final class MapsReaderV2 implements MapsReaderInterface{
	final HashMap<String,POIentry> POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
	final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
	public final Frequency freq_detail = new Frequency();
	public final Frequency freq_catalog = new Frequency();
	public final HashSet<String> cellsInPOIDB = new HashSet<String>();
	final MapsReaderToolV2 MapsreaderTool = new MapsReaderToolV2();
	public ArrayList<String> getSemanticLabels(){
		return MapsreaderTool.initCatalogArray();
	}
	
	public MapsReaderV2(){
		init();
	}
	
	public void init(){
		worker();
	}
	
	public void worker(){
		//System.out.println(this.getClass().getSimpleName()+":Loading...");
		reader();
		check();
		System.out.println(this.getClass().getSimpleName()+":Loading Success!");
		//ShowResults();
	}	
	
	void reader(){
		int count = 0;
		for(String uid:POIDB.keySet()){
			POIentry onepoientry = POIDB.get(uid);
			//String msg = "Uid:"+uid+";"+onepoientry.getData();
			//System.out.println(msg);
			boolean isWithinTheRect = datasampler.getRect().isWithinTheRect(onepoientry.getLocation());
			if(isWithinTheRect){
				count++;
				String catalog = MapsreaderTool.filter(onepoientry.getName());
				freq_catalog.addValue(catalog);
				String cid = datasampler.getRectenv().quadtreeEncoder(onepoientry.getLocation());
				cellsInPOIDB.add(cid);
				String e = cid+"="+catalog;
				//freq.addValue(catalog);
				//freq.addValue(cid);
				freq_detail.addValue(e);
				//System.out.println(e+";"+isWithinTheRect+";"+msg);
			}
			
		}
		System.out.println("Number of POI Items:"+count);
	}

	void check(){
		//填充每一个空间单元都有分配语义标签，若没有POI则为others。
		ArrayList<String> cells = Convertor.extend("", datasampler.level);
		for(String cid:cells){
			if(!cellsInPOIDB.contains(cid)){
				String default_catalog = "others";
				freq_catalog.addValue(default_catalog);
				String e = cid+"="+default_catalog;
				freq_detail.addValue(e);
			}
		}
	}
	
	public void ShowResults(){
		System.out.println("freq_detail="+this.freq_detail);
		System.out.println("freq_catalog="+this.freq_catalog);
	}
	
	public double queryrisk(String cid,String catalog){
		String key = cid+"="+catalog;
		double risk = (double)freq_detail.getCount(key)/(double)freq_catalog.getCount(catalog);
		if(b_output_detail){
			System.out.println(cid+";"+catalog+";"+key+";"+risk);
		}
		
		return risk;
	}
	
	public double CatalogRiskLevel(String catalog){
		return MapsreaderTool.getSemanticLabelMapperConfigRiskLevel(catalog);
	}
	
	public static void main(String[] args) {
		MapsReaderV2 m = new MapsReaderV2();
		m.worker();
		String cid = "0000";
		String catalog = "others";
		System.out.println(m.queryrisk(cid, catalog));

	}

}
