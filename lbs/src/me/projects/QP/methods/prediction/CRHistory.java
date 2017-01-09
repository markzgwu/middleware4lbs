package me.projects.QP.methods.prediction;

import org.projects.preprocessing.MovingObject;

public final class CRHistory {
	final String TargetID;
	final MovingObject MO;
	final String cell;
	final String[] CR;
	public CRHistory(final String targetid, 
			final String cellid, 
			final String[] cloakingregion, 
			final MovingObject movingobject) {
		super();
		this.TargetID = targetid;
		this.cell = cellid;
		this.CR = cloakingregion;
		this.MO = movingobject;
	}
	public final String getTargetID() {
		return TargetID;
	}
	public final MovingObject getMO() {
		return MO;
	}
	public final String getCell() {
		return cell;
	}
	public final String[] getCR() {
		return CR;
	}
	
}
