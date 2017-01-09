package me.projects.LISG.methods;

import java.util.ArrayList;

import org.parameters.I_constant;

public interface MapsReaderInterface {
	final boolean b_output_detail = I_constant.output_detail.equals("true");
	ArrayList<String> getSemanticLabels();
	double queryrisk(String cid,String catalog);
	double CatalogRiskLevel(String catalog);
	void worker();
}
