package me.projects.QP.methods.prediction;

import java.util.ArrayList;

public final class SessionUser {
	//ArrayList<MovingObject> ListMO = new ArrayList<MovingObject>();
	public final String ObjectID;
	public final ArrayList<String> TImeStampSequence = new ArrayList<String>();
	public final ArrayList<String> CellIDSequence = new ArrayList<String>();
	public final ArrayList<String[]> CRSequence = new ArrayList<String[]>();
	public SessionUser(final String ObjectID){
		this.ObjectID = ObjectID;
	}
	public void add(CRHistory oneSession){
		TImeStampSequence.add(oneSession.MO.getTimestamp());
		CellIDSequence.add(oneSession.cell);
		CRSequence.add(oneSession.CR);
	}
}
