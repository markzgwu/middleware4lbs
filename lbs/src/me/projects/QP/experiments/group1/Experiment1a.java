package me.projects.QP.experiments.group1;

import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;
import me.projects.QP.methods.prediction.PathTrainer;

import org.tools.forLog.ILog;

public final class Experiment1a implements ILog{

	public static void main(String[] args) {
		String alloutput = "";

		for(int i=0;i<3;i++){
			final int SubPathLength=2+i; 
			for(int a = 0;a<3;a++)
			{
			int level = 3+a;
			final Parameters para = new Parameters(level,SubPathLength);
			ParameterTable.INSTANCE.init(para);
			final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
			final PathTrainer task = new PathTrainer(datasampler);
			task.init();
			String result = task.worker();
			System.out.println(result);
			alloutput+="SubPathLength"+SubPathLength+"_"+task.getOutput()+"\n";
			}
		
		}
		
		System.out.println(alloutput);
	}

}
