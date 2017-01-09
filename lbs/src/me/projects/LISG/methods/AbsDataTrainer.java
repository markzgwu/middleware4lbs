package me.projects.LISG.methods;

import java.util.ArrayList;

import org.projects.preprocessing.Convertor;

public abstract class AbsDataTrainer {
	//abstract double PriorBelief_Pr_Oloc_A1(String LocationTag);
	//abstract double PriorBelief_Pr_A();
	//abstract double PriorBelief_Pr_Oloc(String LocationTag);
	
	protected abstract double PriorBelief_Pr_Oloc_A(String LocationTag,boolean A);
	protected abstract double PriorBelief_Pr_A(boolean A);
	
	protected abstract double PosteriorBelief_Pr_A1_Oloc(String LocationTag);
	
	protected abstract double PriorBelief_Pr_Oloc_A(String[] LocationTags,boolean A);
	protected abstract double PosteriorBelief_Pr_A1_Oloc(String[] LocationTags);
	
	public final ArrayList<String> SemanticLabels=new ArrayList<String>();
	public final ArrayList<String> LocationTags=new ArrayList<String>();
	
	final protected double Constant_Pr_A = ParameterTable.INSTANCE.getParameters().Constant_Pr_A;
	
	protected ArrayList<String> extend(int level){
		ArrayList<String> result = Convertor.extend("", level);
		return result;
	}
	//ArrayList<String> getLocationTags();
	//ArrayList<String> getSemanticLabels();
}
