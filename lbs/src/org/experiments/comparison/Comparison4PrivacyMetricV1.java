package org.experiments.comparison;

import java.io.File;

import org.experiments.comparison.warper.*;
import org.projects.caches.CacheEhcache;
import org.projects.caches.ICache;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.privacymodel.PrivacyProfile;
import org.projects.providers.*;
import org.projects.schemes.Baseline;
import org.projects.schemes.BufferedNewcasper;
import org.projects.schemes.BufferingV2;
import org.projects.schemes.MyCloaking;
import org.projects.schemes.Newcasper;
import org.tools.forLog.ILog;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class Comparison4PrivacyMetricV1 implements ILog{
	
	public static void main(String[] args) {
		final IDBProvider dbsp1 = new ProviderSimple();
		//final IDBProvider dbsp = new ProviderBaiduMaps();
		//final WorkerMysql mysql = new WorkerMysql();
		//mysql.init();
		//final IDBProvider dbsp = new ProviderMYSQL(mysql);
		final ICache bufferzone = new CacheEhcache("Buffer1");
		bufferzone.clear();
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
		
		{
		final AbsSchemeWarper schemewarper = new SchemeWarper(new Baseline());
		schemewarper.mount(rectenv, data, dbsp1, bufferzone);
		schemewarper.prefix = prefix;
		schemewarper.execute();
		}
		
		{
		final AbsSchemeWarper schemewarper = new SchemeWarper(new Newcasper(new PrivacyProfile(10,2,0)));
		schemewarper.mount(rectenv, data, dbsp1, bufferzone);
		schemewarper.prefix = prefix;
		schemewarper.execute();
		}
		
		{
		final AbsSchemeWarper schemewarper = new SchemeWarper(new BufferingV2());
		schemewarper.mount(rectenv, data, dbsp1, bufferzone);
		schemewarper.prefix = prefix;
		schemewarper.execute();
		}
		
		{
		final AbsSchemeWarper schemewarper = new SchemeWarper(new BufferedNewcasper(new PrivacyProfile(10,2,0)));
		schemewarper.mount(rectenv, data, dbsp1, bufferzone);
		schemewarper.prefix = prefix;
		schemewarper.execute();
		}
		
		{
		final AbsSchemeWarper schemewarper = new SchemeWarper(new MyCloaking(new PrivacyProfile(10,2,0)));
		schemewarper.mount(rectenv, data, dbsp1, bufferzone);
		schemewarper.prefix = prefix;
		schemewarper.execute();
		}		
		
		
		//mysql.close();
	}
	
}
