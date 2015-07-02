package me.projects.LISG.experiments.comparison;

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

public final class ComparisonForPrivacyProfile implements ILog{
	
	public static void main(String[] args) {
		
		final IDBProvider dbsp1 = new ProviderSimple();
		final ICache bufferzone = null;
		
		String output="";
		
		String prefix = "PrivacyProfile_";
		{
		final double Constant_Pr_A = 0.05;
		final Parameters para = new Parameters(Constant_Pr_A);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();		
		final PrivacyProfile PrivacyProfile = new PrivacyProfile(10,2,0.1);
		final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyProfile(new PrivacyRiskNewcasperSCA(PrivacyProfile));
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.load();
		schemewarper.prefix = prefix;
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		}
		//System.gc();
		
		{
		final double Constant_Pr_A = 0.1;
		final Parameters para = new Parameters(Constant_Pr_A);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final PrivacyProfile PrivacyProfile = new PrivacyProfile(10,2,0.1);
		final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyProfile(new PrivacyRiskNewcasperSCA(PrivacyProfile));
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.load();
		schemewarper.prefix = prefix;
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		}		
		
		{
		final double Constant_Pr_A = 0.5;
		final Parameters para = new Parameters(Constant_Pr_A);
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final PrivacyProfile PrivacyProfile = new PrivacyProfile(10,2,0.05);
		final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyProfile(new PrivacyRiskNewcasperSCA(PrivacyProfile));
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
