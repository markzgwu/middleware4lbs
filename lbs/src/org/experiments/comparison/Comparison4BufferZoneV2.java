package org.experiments.comparison;

import java.io.File;

import org.experiments.comparison.warper.*;
import org.projects.caches.CacheEhcache;
import org.projects.caches.ICache;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.privacymodel.PrivacyProfile;
import org.projects.providers.*;
import org.projects.schemes.*;
import org.tools.forLog.ILog;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class Comparison4BufferZoneV2 implements ILog{
	
	public static void main(String[] args) {
		final IDBProvider dbsp1 = new ProviderSimple();
		//final IDBProvider dbsp = new ProviderBaiduMaps();
		//final WorkerMysql mysql = new WorkerMysql();
		//mysql.init();
		//final IDBProvider dbsp = new ProviderMYSQL(mysql);

		String filepath = "d:\\";
		//final String filepath = "..";
		filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
		final LoadingMO data = new LoadingMO(filepath);
		final int level = 5;
		final String tag = "20";
		final Rectangle rect = data.rectangelTrim(tag);
		final RectEnv rectenv = new RectEnv(level,rect);
		logger.info(rectenv.summary());
		String prefix = "slim_";
		
		final ICache bufferzone1 = new CacheEhcache("BufferFIFO");
		bufferzone1.clear();		
		
		final ICache bufferzone2 = new CacheEhcache("BufferLRU");
		bufferzone2.clear();		
		
		final ICache bufferzone3 = new CacheEhcache("BufferLFU");
		bufferzone3.clear();				
		
		Dataoutput dataoutput1 = new Dataoutput();
		dataoutput1.init();
		Dataoutput dataoutput2 = new Dataoutput();
		dataoutput2.init();
		Dataoutput dataoutput3 = new Dataoutput();
		dataoutput3.init();
		
		for(int i=2;i<21;i++){
			final PrivacyProfile privacyprofile = new PrivacyProfile(i,2,0);
			final AbsScheme s = new MyCloaking(privacyprofile);
			//final AbsScheme s = new BufferedNewcasper(privacyprofile);
			
			{
			final AbsSchemeWarper schemewarper = new SchemeWarper4Continous(s);
			schemewarper.mount(rectenv, data, dbsp1, bufferzone1);
			schemewarper.mount(dataoutput1);
			schemewarper.prefix = prefix;
			schemewarper.execute();
			}
			
			{
			final AbsSchemeWarper schemewarper = new SchemeWarper4Continous(s);
			schemewarper.mount(rectenv, data, dbsp1, bufferzone2);
			schemewarper.mount(dataoutput2);
			schemewarper.prefix = prefix;
			schemewarper.execute();
			}
			
			{
			final AbsSchemeWarper schemewarper = new SchemeWarper4Continous(s);
			schemewarper.mount(rectenv, data, dbsp1, bufferzone3);
			schemewarper.mount(dataoutput3);
			schemewarper.prefix = prefix;
			schemewarper.execute();
			}			
		}
	
		
		dataoutput1.output("", "BufferFIFO");
		dataoutput2.output("", "BufferLRU");
		dataoutput3.output("", "BufferLFU");
		//mysql.close();
	}
	
}
