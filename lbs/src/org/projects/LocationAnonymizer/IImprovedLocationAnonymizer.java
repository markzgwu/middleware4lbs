package org.projects.LocationAnonymizer;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRSequence;

import org.parameters.I_constant;
import org.projects.privacymodel.PrivacyProfile;

public interface IImprovedLocationAnonymizer {
	//ArrayList<String> anonymization(int k,int Amin,String cid);
	
	ArrayList<String> anonymization(final PrivacyProfile privacyprofile, 
			final String cid, 
			final CRSequence CRSequence);
	
	boolean b_output_detail = I_constant.output_detail.equals("true");
}
