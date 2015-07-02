package org.projects.LocationAnonymizer;

import java.util.ArrayList;
import java.util.HashSet;

import me.projects.LISG.methods.LocationSemanticModel;

import org.apache.commons.lang3.StringUtils;
import org.projects.preprocessing.Convertor;
import org.projects.privacymodel.PrivacyProfile;
import org.tools.forMaths.MathTool4Integer;
import org.tools.forString.StringTool;

public final class GridSemanticsProtection implements ILocationAnonymizer{
	
	final QuadTreeCounter qtcounter;
	final LocationSemanticModel varLSM;
	public GridSemanticsProtection(QuadTreeCounter qtcounter,LocationSemanticModel varLSM) {
		super();
		this.qtcounter = qtcounter;
		this.varLSM = varLSM;
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
	
	HashSet<String>  SimpleLA(int k,int Amin,String cid){
		Cell myCell = findCell(cid);
		HashSet<String> area = null;
		boolean b = (myCell.N>=k) && (myCell.Area>= Amin);
		if(b){
			area = new HashSet<String>();
			area.add(cid);
		}else{
			area = SimpleLA(k,Amin,Parent(cid));
		}
		
		return area;
	}
	//PrivacyProfile privacyprofile = new PrivacyProfile(k,A,risk);
	public ArrayList<String>  BasicLA_v1(PrivacyProfile privacyprofile,String cid){
		if(StringUtils.isEmpty(cid)){
			if(b_output_detail){
				System.out.println("WARN: Cloaking Fails!");
			}
			return null;
		}
		
		ArrayList<String> area =  new ArrayList<String>();
		int k = privacyprofile.k;
		int Amin = privacyprofile.A;
		double riskmax = privacyprofile.risk; 
		int level = qtcounter.rectenv.getLevel().level;
		Cell cell = findCell(cid);
		//String[] area = null;
		
		//System.out.println(cid);
		double risk1 = 0;
		if(cid.length()<level){
			risk1 = varLSM.PosteriorBelief_Pr_A_Oloc(Convertor.extend(cid,level));
		}else{
			risk1 = varLSM.PosteriorBelief_Pr_A_Oloc(cid);
		}
		
		//risk1 = risk1 / cell.N;
		
		boolean b = (cell.N>=k) && (cell.Area>= Amin) && (risk1<riskmax);
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
				double risk =0;
				boolean b2 = ( (NH>=k) && (NV>=k) && (NH<=NV) ) || (NV<k) ;
				if (b2){
					//Area(cid) ¡È Area(cidH);
					area.add(cid);
					area.add(cellH.cid);
					risk = varLSM.PosteriorBelief_Pr_A_Oloc(Convertor.extend(area,qtcounter.rectenv.getLevel().level));
					//risk = risk / NH;
				}else{
					//Area(cid) ¡È Area(cidV );
					area.add(cid);
					area.add(cellV.cid);
					risk = varLSM.PosteriorBelief_Pr_A_Oloc(Convertor.extend(area,qtcounter.rectenv.getLevel().level));
					//risk = risk / NV;
				}

				boolean b3 = (risk>=riskmax);
				if(b_output_detail){
					System.out.println(b3+";risk="+risk+";Region Content="+area);
				}
				//System.out.println("Region Content="+area);
				if(b3){
					area = BasicLA_v1(privacyprofile,Parent(cid));
				}
				
			}else{
				area = BasicLA_v1(privacyprofile,Parent(cid));
			}
		}
		return area;
	}	
	
	HashSet<String> Area(String cid){
		HashSet<String> area = new HashSet<String>();
		area.add(cid);
		return area;
	}
	
	String Parent(String cid){
		String parent = cid.substring(0, cid.length()-1);
		//System.out.println(parent);
		return parent;
	}

	@Override
	public ArrayList<String> anonymization(PrivacyProfile privacyprofile, String cid) {
		// TODO Auto-generated method stub
		return BasicLA_v1(privacyprofile,cid);
	}	
}
