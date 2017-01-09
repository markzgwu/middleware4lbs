package org.projects.LocationAnonymizer;

import java.util.HashSet;

public final class BasicLA {

	Cell findCell(String cid){
		Cell c = new Cell();
		c.cid = cid;
		c.N = 10;
		c.Area = 1;
		return c;
	}
	
	String findV(String cid){
		String cidv = cid;
		return cidv;
	}
	
	String findH(String cid){
		String cidh = cid;
		return cidh;
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
	
	HashSet<String>  BasicLA_v1(int k,int Amin,String cid){
		Cell cell = findCell(cid);
		//String[] area = null;
		HashSet<String> area =  new HashSet<String>();;
		boolean b = (cell.N>=k) && (cell.Area>= Amin);
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
				area = BasicLA_v1(k,Amin,Parent(cid));
			}
		}
		return area;
	}	
	
	String[]  BasicLA(int k,int Amin,String cid){
		Cell cell = findCell(cid);
		String[] area = null;
		//HashSet<String> area =  new HashSet<String>();;
		boolean b = (cell.N>=k) && (cell.Area>= Amin);
		if(b){
			area = new String[]{cid};
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
					area = new String[]{cid,cellH.cid};
				}else{
					//Area(cid) ¡È Area(cidV );
					area = new String[]{cid,cellV.cid};
				}
				
			}else{
				area = BasicLA(k,Amin,Parent(cid));
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
		return cid;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
