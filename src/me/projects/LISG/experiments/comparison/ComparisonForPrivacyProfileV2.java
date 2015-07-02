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
import org.tools.forMatlab.Format4Matlab;

public final class ComparisonForPrivacyProfileV2 implements ILog{
	
	public static void main(String[] args) {
		
		final IDBProvider dbsp1 = new ProviderSimple();
		final ICache bufferzone = null;
		
		String output="";
		String output1="";
		//double[] Pr_As = {0.01,0.05,0.1,0.2,0.5,0.9};
		double[] Pr_As = {0.05,0.1,0.2,0.5};
		String prefix = "PrivacyProfile_";
		int label = 0;
		for(double Constant_Pr_A:Pr_As){
			String msg = "";
			for(int i=1;i<20;i++){
				
				//final double Constant_Pr_A = 0.05;
				final double risk = 0.01*i;
				final double safty_t = 1-risk;
				
				System.out.println("Experiment Batch "+i+":Pr_A = "+Constant_Pr_A+"; risk = "+risk +"; safty="+safty_t);
				
				final Parameters para = new Parameters(Constant_Pr_A);
				ParameterTable.INSTANCE.init(para);
				final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();		
				final PrivacyProfile PrivacyProfile = new PrivacyProfile(10,2,risk);
				final AbsSchemeWarper4PrivacyMetric schemewarper = new SchemeWarper4PrivacyProfile(new PrivacyRiskNewcasperSCA(PrivacyProfile));
				schemewarper.mount(datasampler);
				schemewarper.mount(dbsp1,bufferzone);
				schemewarper.load();
				schemewarper.prefix = prefix;
				schemewarper.execute();
				//output+=schemewarper.output()+"\n";
				msg+=(schemewarper.StatPara.average_cloaking_success_ratio)+",";
			}
			output1+=Format4Matlab.output4matlab("P"+label++, msg)+"\n";
			
		}

		System.out.println(output);
		System.out.println(output1);
		//mysql.close();
	}
	
}
