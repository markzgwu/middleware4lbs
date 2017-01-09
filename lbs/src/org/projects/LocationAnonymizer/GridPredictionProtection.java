package org.projects.LocationAnonymizer;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRPrediction;
import me.projects.QP.methods.prediction.CRSequence;
import me.projects.toolkit.ToolkitString;

import org.apache.commons.lang3.StringUtils;
import org.parameters.I_constant;
import org.projects.preprocessing.Convertor;
import org.projects.privacymodel.PrivacyProfile;
import org.tools.forMaths.MathTool4Integer;
import org.tools.forString.StringTool;

public final class GridPredictionProtection implements IImprovedLocationAnonymizer{
	
	final QuadTreeCounter qtcounter;
	final CRPrediction varCRPrediction;
	public GridPredictionProtection(final QuadTreeCounter qtcounter,
			final CRPrediction varCRPrediction){
		super();
		this.qtcounter = qtcounter;
		this.varCRPrediction = varCRPrediction;
	}

	Cell findCell(String cid){
		Cell c = new Cell();
		c.cid = cid;
		c.N = qtcounter.get(cid);
		c.Area = MathTool4Integer.pow(4, qtcounter.rectenv.getLevel().level-cid.length());
		return c;
	}
	
	//v ´¹Ö± vertical
	String findV(String cid){
		return StringTool.replaceLastChat(cid,findCharVH(cid)[0]);
	}
	
	//h Ë®Æ½ horizontal
	String findH(String cid){
		return StringTool.replaceLastChat(cid,findCharVH(cid)[1]);
	}
	
	String[] findCharVH(String cid){
		String lastchar = String.valueOf(cid.charAt(cid.length()-1));
		String neighbors = neighbor(lastchar);
		return neighbors.split(",");
	}
	
	//result = v,h
	String neighbor(String key){
		String result="";
		switch(key){
			case "0":
				result = "1,3";
				break;
			case "1":
				result = "0,2";
				break;
			case "2":
				result = "3,1";
				break;
			case "3":
				result = "2,0";
				break;
		}
		return result;
	}
	
	String[] getCellSet(final String cid){
		final int level = qtcounter.rectenv.getLevel().level;
		//String[] cellset = null;
		if(cid.length()<level){
			final String[] cellset = ToolkitString.toStringArray(Convertor.extend(cid,level));
			return cellset;
		}else{
			final String[] cellset = new String[1];
			cellset[0] = cid;
			return cellset;
		}
	}
	
	String[] getCellSet(final ArrayList<String> area){
		final int level = qtcounter.rectenv.getLevel().level;
		return ToolkitString.toStringArray(Convertor.extend(area,level));
	}
	
	//PrivacyProfile privacyprofile = new PrivacyProfile(k,A,risk);
	public ArrayList<String>  Cloaking(
			final PrivacyProfile privacyprofile,
			final String cid,
			final CRSequence CRSequence){
		//final int level = qtcounter.rectenv.getLevel().level;
		if(StringUtils.isEmpty(cid)||cid.length()<I_constant.cellid_length_max_for_recursion){
			if(b_output_detail){
				System.out.println("WARN: Cloaking Fails!");
			}
			return null;
		}
		
		ArrayList<String> area =  new ArrayList<String>();
		final int k = privacyprofile.k;
		final int Amin = privacyprofile.A;
		final double predictionmin = privacyprofile.prediction;
		
		final Cell cell = findCell(cid);
		//String[] area = null;
		
		//System.out.println(cid);
		double prediction = 0;
		//final String[] cellset = getCellSet(cid);
		final String[] cellset = new String[1];
		cellset[0] = cid;
		final CRSequence newCRSequence = CRSequence.putnew(cellset);
		//System.out.println();
		
		prediction = varCRPrediction.check(newCRSequence).Entropy;
		
		//risk1 = risk1 / cell.N;
		
		boolean b = (cell.N>=k) && (cell.Area>= Amin) && (prediction>=predictionmin);
		if(b){
			//area = new String[]{"cid"};
			//area = new HashSet<String>();
			area.add(cid);
		}else{
			Cell cellV = findCell(findV(cid));
			Cell cellH = findCell(findH(cid));
			int NV = cell.N + cellV.N;
			int NH = cell.N + cellH.N;
			
			boolean b1 = ( (NV>=k) || (NH>=k) ) && ( (2*cell.Area)>=Amin );
			if (b1){
				double prediction2 =0;
				boolean b2 = ( (NH>=k) && (NV>=k) && (NH<=NV) ) || (NV<k) ;
				if (b2){
					//Area(cid) ¡È Area(cidH);
					area.add(cid);
					area.add(cellH.cid);
					//risk = risk / NH;
				}else{
					//Area(cid) ¡È Area(cidV );
					area.add(cid);
					area.add(cellV.cid);
					//risk = risk / NV;
				}
				prediction2 = varCRPrediction.check(CRSequence.putnew(area)).Entropy;
				boolean b3 = (prediction2<predictionmin);
				if(b_output_detail){
					System.out.println(b3+";QPSafety="+prediction+";Region Content="+area);
				}
				//System.out.println("Region Content="+area);
				if(b3){
					area = Cloaking(privacyprofile,Parent(cid),CRSequence);
				}
				
			}else{
				area = Cloaking(privacyprofile,Parent(cid),CRSequence);
			}
		}
		return area;
	}
	
	String Parent(String cid){
		String parent = cid.substring(0, cid.length()-1);
		//System.out.println(parent);
		return parent;
	}

	@Override
	public ArrayList<String> anonymization(PrivacyProfile privacyprofile,
			String cid, CRSequence CRSequence) {
		return Cloaking(privacyprofile,cid,CRSequence);
	}
	
}
