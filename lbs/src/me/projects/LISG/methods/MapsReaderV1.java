package me.projects.LISG.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.Frequency;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;

public final class MapsReaderV1 implements MapsReaderInterface{
	final HashMap<String,POIentry> POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
	final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
	final Frequency freq = new Frequency();
	final MapsReaderToolV2 MapsreaderTool = new MapsReaderToolV2();
	public ArrayList<String> getSemanticLabels(){
		return MapsreaderTool.initCatalogArray();
	}	
	public void reader(){
		int count = 0;
		for(String uid:POIDB.keySet()){
			POIentry onepoientry = POIDB.get(uid);
			//String msg = "Uid:"+uid+";"+onepoientry.getData();
			//System.out.println(msg);
			boolean isWithinTheRect = datasampler.getRect().isWithinTheRect(onepoientry.getLocation());
			if(isWithinTheRect){
				count++;
				String cid = datasampler.getRectenv().quadtreeEncoder(onepoientry.getLocation());
				String catalog = MapsreaderTool.filter(onepoientry.getName());
				String e = cid+"="+catalog;
				//freq.addValue(catalog);
				//freq.addValue(cid);
				freq.addValue(e);
				//System.out.println(e+";"+isWithinTheRect+";"+msg);
			}
			
		}
		System.out.println(count);		
	}

	public void ShowResults(){
		System.out.println(this.freq);
	}
	
	public void Show(){
		int count = 0;
		for(String uid:POIDB.keySet()){
			POIentry onepoientry = POIDB.get(uid);
			String msg = "Uid:"+uid+";"+onepoientry.getData();
			//System.out.println(msg);
			boolean isWithinTheRect = datasampler.getRect().isWithinTheRect(onepoientry.getLocation());
			if(isWithinTheRect){
				count++;
				System.out.println(datasampler.getRectenv().quadtreeEncoder(onepoientry.getLocation()));
			}
			System.out.println(isWithinTheRect+";"+msg);
		}
		System.out.println(count);
	}
	
	public void worker(){
		reader();
		//ShowResults();
	}
	
	public double queryrisk(String cid,String catalog){
		String key = cid+"="+catalog;
		double risk = freq.getCount(key)/countPOIbyCatalog(catalog);
		return risk;
	}
	
	double countPOIbyCatalog(String catalog){
		long count = 0;
		Iterator<Entry<Comparable<?>, Long>> iter = freq.entrySetIterator();
		while(iter.hasNext()){
			Entry<Comparable<?>, Long> obj = iter.next();
			String key = (String)obj.getKey();
			String key1 = key.substring(key.lastIndexOf("=")+1);
			//System.out.println(key+";"+key1);
			boolean b = catalog.equals(key1);
			if(b){
				count+=obj.getValue();
			}
		}

		return count;
	}
	
	double countPOIbyCell(String cid){
		long count = 0;
		for(String catalog:getSemanticLabels()){
			String key = cid+"="+catalog;
			count+=freq.getCount(key);
		}
		return count;
	}
	
	public double CatalogRiskLevel(String catalog){
		return MapsreaderTool.getSemanticLabelMapperConfigRiskLevel(catalog);
	}	
	
	public static void main(String[] args) {
		MapsReaderV1 m = new MapsReaderV1();
		m.reader();
		String cid = "0000";
		String catalog = "others";
		System.out.println(m.queryrisk(cid, catalog));

	}

}
