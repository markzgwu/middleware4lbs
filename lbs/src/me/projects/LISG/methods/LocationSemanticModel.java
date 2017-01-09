package me.projects.LISG.methods;

import java.util.ArrayList;

public final class LocationSemanticModel {
	
	public AbsDataTrainer dataTrainer;

	public LocationSemanticModel(AbsDataTrainer dataTrainer){
		this.dataTrainer = dataTrainer;
	}
	
	public void mount(AbsDataTrainer dataTrainer){
		this.dataTrainer = dataTrainer;
	}
	
	public final double PosteriorBelief_Pr_A_Oloc(String locTag){
		return dataTrainer.PosteriorBelief_Pr_A1_Oloc(locTag);
	}
	
	public final double PosteriorBelief_Pr_A_Oloc(String[] LocTags){
		return dataTrainer.PosteriorBelief_Pr_A1_Oloc(LocTags);
	}
	
	public final double PosteriorBelief_Pr_A_Oloc(ArrayList<String> LocTags){
		//System.out.println(LocTags);
		String[] loctags = new String[LocTags.size()];
		loctags = LocTags.toArray(loctags);
		double result = PosteriorBelief_Pr_A_Oloc(loctags);
		//System.out.println(result);
		return result;
	}

}
