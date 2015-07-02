package org.projects.LocationAnonymizer;

import java.util.ArrayList;

import org.parameters.I_constant;
import org.projects.privacymodel.PrivacyProfile;

public interface ILocationAnonymizer {
	ArrayList<String> anonymization(final PrivacyProfile privacyprofile, 
			final String cid);
	
	boolean b_output_detail = I_constant.output_detail.equals("true");
}
