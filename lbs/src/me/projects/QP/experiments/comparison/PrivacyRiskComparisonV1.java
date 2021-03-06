package me.projects.QP.experiments.comparison;

import me.projects.LISG.experiments.comparison.warper.*;
import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;

import org.projects.caches.ICache;
import org.projects.privacymodel.PrivacyProfile;
import org.projects.providers.IDBProvider;
import org.projects.providers.ProviderSimple;
import org.projects.schemes.*;
import org.tools.forLog.ILog;

public final class PrivacyRiskComparisonV1 implements ILog{
	
	public static void main(String[] args) {
		final Parameters para = new Parameters();
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		
		final IDBProvider dbsp1 = new ProviderSimple();
		final ICache bufferzone = null;
		final PrivacyProfile PrivacyProfile = new PrivacyProfile(10,2,0.02);
		
		String output="";
		
		String prefix = "slim_";
		//System.gc();
		{
		final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyMetricV1(new PrivacyRiskBaseline());
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.prefix = prefix;
		schemewarper.load();
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		}	
		
		{
		final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyMetricV1(new PrivacyRiskBaseline());
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.prefix = prefix;
		schemewarper.load();
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		}
		//System.gc();
		{
		final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyMetricV1(new PrivacyRiskNewcasper(PrivacyProfile));
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.load();
		schemewarper.prefix = prefix;
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		}
		//System.gc();
		{
		final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyMetricV1(new PrivacyRiskNewcasperSCA(PrivacyProfile));
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.load();
		schemewarper.prefix = prefix;
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		}
		
		System.out.println(output);
		//mysql.close();
	}
	
}
