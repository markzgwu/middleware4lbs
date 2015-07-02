package me.projects.QP.experiments.group1;

import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;
import me.projects.QP.methods.prediction.PathTrainer;
import me.projects.toolkit.ToolkitMetric;

import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;

public final class Experiment1d implements ILog{

	public static void main(String[] args) {
		String alloutput = "";
		String allmetric = "";
		for(int i=0;i<4;i++){
			String outputmetric1="";
			String outputmetric2="";
			final int SubPathLength=2+i; 
			for(int a = 0;a < 10 ;a++)
			{
				int level = 3+a;
				final Parameters para = new Parameters(level,SubPathLength);
				ParameterTable.INSTANCE.init(para);
				final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
				final PathTrainer task = new PathTrainer(datasampler);
				task.init();
				String result = task.worker();
				outputmetric1+=ToolkitMetric.getAverage(task.outputPosteriorBeliefPr)+",";
				outputmetric2+=ToolkitMetric.getEntropy(task.outputPosteriorBeliefPr)+",";
				System.out.println(result);
				alloutput+="SubPathLength"+SubPathLength+"_"+task.getOutput()+"\n";
			}
			outputmetric1 = Format4Matlab.format("SubPathLength"+SubPathLength+"_"+"Average",outputmetric1);
			outputmetric2 = Format4Matlab.format("SubPathLength"+SubPathLength+"_"+"Entropy",outputmetric2);
			
			allmetric+=outputmetric1+"\n";
			allmetric+=outputmetric2+"\n";
		}
		
		System.out.println(alloutput);
		System.out.println(allmetric);
	}

}
