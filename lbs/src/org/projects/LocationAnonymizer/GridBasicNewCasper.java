package org.projects.LocationAnonymizer;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.parameters.I_constant;
import org.projects.privacymodel.PrivacyProfile;
import org.tools.forMaths.MathTool4Integer;
import org.tools.forString.StringTool;

public final class GridBasicNewCasper implements ILocationAnonymizer{
	
	final QuadTreeCounter qtcounter;
	
	public GridBasicNewCasper(QuadTreeCounter qtcounter) {
		super();
		this.qtcounter = qtcounter;
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
	
	public ArrayList<String>  BasicLA_v1(PrivacyProfile privacyprofile,String cid){
		if(StringUtils.isEmpty(cid)||cid.length()<I_constant.cellid_length_max_for_recursion){
			if(b_output_detail){
				System.out.println("WARN: Cloaking Fails!");
			}
			return null;
		}		
		
		final int k = privacyprofile.k;
		final int Amin = privacyprofile.A;
		final Cell cell = findCell(cid);
		//String[] area = null;
		ArrayList<String> area =  new ArrayList<String>();
		final boolean b = (cell.N>=k) && (cell.Area>= Amin);
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
				
				boolean b2 = ( (NH>=k) && (NV>=k) && (NH<=NV) ) || (NV<k) ;
				if (b2){
					//Area(cid) ¡È Area(cidH);
					area.add(cid);
					area.add(cellH.cid);
				}else{
					//Area(cid) ¡È Area(cidV );
					area.add(cid);
					area.add(cellV.cid);
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
