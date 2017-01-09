package org.projects.datamodel;

import org.tools.forMaths.MathTool4Integer;

public final class Level4qt {
	public final int level;	
	public final int powerLevelOf2;
	public final int powerLevelOf4;	
	
	public Level4qt(int level){
		this.level = level;
		powerLevelOf2 = pow(2,level);
		powerLevelOf4 = pow(4,level);;
	}
	
	private int pow(int b,int power){
		return MathTool4Integer.pow(b,power);
	}
}
