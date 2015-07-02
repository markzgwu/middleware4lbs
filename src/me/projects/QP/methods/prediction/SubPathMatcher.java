package me.projects.QP.methods.prediction;

import java.util.ArrayList;
import java.util.HashMap;

import me.projects.toolkit.ToolkitString;

public final class SubPathMatcher{
	int check=0;
	public void check(final String[] subpath,final String[] onepath){
		if(subpath.length>onepath.length-1){
			check++;
			String msg = "subpath:("+subpath.length+")"+ToolkitString.output(subpath);
			msg += "onepath:("+onepath.length+")"+ToolkitString.output(onepath);
			System.out.println("WARN:"+msg);
		}
	}
	
	public int countSubpathAndTail(final String[] subpath,final String onetail,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			final String[] onepath = allpaths.get(ObjectID);
			//check(subpath, onepath);
			if(isSubpathAndTail(subpath,onetail,onepath)){
				n++;
			}
		}
		return n;
	}
	
	public int countCloakingRegionListAndTail(final ArrayList<String[]> cloakingregionlist,final String onetail,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			final String[] onepath = allpaths.get(ObjectID);
			//check(subpath, onepath);
			if(isCloakingRegionListAndTail(cloakingregionlist,onetail,onepath)){
				n++;
			}
		}
		return n;
	}
	
	public int countTail(final String onetail,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			final String[] onepath = allpaths.get(ObjectID);
			if(isTail(onetail,onepath)){
				n++;
			}
		}
		return n;
	}
	
	public int countCloakingRegionList(final ArrayList<String[]> cloakingregionlist,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			final String[] onepath = allpaths.get(ObjectID);
			if(isCloakingRegionList(cloakingregionlist, onepath)){
				n++;
			}
		}
		return n;
	}	
	
	public int countSubpath(final String[] subpath,final HashMap<String,String[]> allpaths){
		int n = 0;
		for(String ObjectID:allpaths.keySet()){
			String[] onepath = allpaths.get(ObjectID);
			if(isSubpath(subpath, onepath)){
				n++;
			}
		}
		return n;
	}	
	
	public boolean isTail(final String onetail,final String[] onepath){
		return onetail.equals(getTail(onepath));
	}
	
	public boolean isMatch(final String[] subpath,final String[] onepath){
		final String strOnepath=ToolkitString.ShowStringArray(onepath);
		final String strSubpath=ToolkitString.ShowStringArray(subpath);
		//System.out.println(strOnepath+"\n"+strSubpath);
		//System.out.println("strOnepath="+strOnepath);
		return strOnepath.contains(strSubpath);
	}
	
	public boolean isSubpathAndTail(final String[] subpath,final String onetail,final String[] onepath){
		return isTail(onetail,onepath) && isSubpath(subpath,onepath);
	}
	
	public boolean isCloakingRegionListAndTail(final ArrayList<String[]> cloakingregionlist,final String onetail,final String[] onepath){
		return isTail(onetail,onepath) && isCloakingRegionList(cloakingregionlist,onepath);
	}	
	
	//history是否是轨迹的一部分
	public boolean isSubpath(final String[] subpath,final String[] onepath){
		return isMatch(subpath,splithistory(onepath));
	}
	
	//该隐藏区是否是该轨迹的一个隐藏区
	public boolean isCloakingRegionList(final ArrayList<String[]> cloakingregionlist, final String[] onepath){
		boolean b = true;
		int start_i = 0;
		for(int i=0;i<onepath.length;i++){
			final String[] cloakingregion = cloakingregionlist.get(0);
			final String cell_index = onepath[i];
			if(isCellInCloakingRegion(cloakingregion,cell_index)){
				start_i = i;
			}
		}
		//System.out.println("Check 1:"+b);
		final int CR_length = cloakingregionlist.size();
		if(CR_length>(onepath.length-start_i)){
			return false;
		}
		
		//System.out.println("Check 2:"+b);
		//int match_count=1;
		for(int j=0;j<(CR_length-start_i);j++){
			if((1+j)>=CR_length){
				//b = false;
				break;
			}
			final String[] cloakingregion = cloakingregionlist.get(1+j);
			final String cell = onepath[start_i+j];
			boolean b1 = isCellInCloakingRegion(cloakingregion,cell);
			//System.out.println("Check 3:"+";"+b1+";j="+j+";start_i="+start_i+":CR_length="+CR_length+";onepath["+(j+start_i)+"]"+cell+"<->"+JSON.toJSONString(cloakingregion));
			if(b1){
				//match_count++;
				//if(match_count==CR_length){
				//	b=true;
				//	break;
				//}
			}else{
				//System.out.println("Check 3:"+";"+b1+";j="+j+";start_i="+start_i+":CR_length="+CR_length+";onepath["+(j+start_i)+"]"+cell+"<->"+JSON.toJSONString(cloakingregion));
				b=false;
				break;
			}
		}
		//System.out.println("Check 4:"+b);
		return b;
	}
	
	public boolean isCellInCloakingRegion(final String[] cloakingregion,final String cell){
		boolean b = false;
		for(String cellInCR:cloakingregion){
			//System.out.println("length:"+cellInCR.length()+";"+cell.length());
			if(cellInCR.length()==cell.length()){
				//System.out.println("length:"+cellInCR.length()+";"+cell.length());
				if(cell.equals(cellInCR)){
					//System.out.println(cell.equals(cellInCR)+"length:"+cellInCR.length()+";"+cell.length());
					b = true;
					break;
				}
			}else{
				final String precell = cell.substring(0,cellInCR.length());
				//System.out.println("precell="+precell);
				if(precell.length()!=cellInCR.length()){
					System.out.println("ERROR!");
				}
				if(precell.equals(cellInCR)){
					b = true;
					break;
				}
			}

		}
		
		//System.out.println(b+":cell="+cell+" matchs "+JSON.toJSONString(cloakingregion));
		return b;
	}
	
	public String[] splithistory(String[] onepath){
		final int splitlength = onepath.length-1;
		final String[] historypath = new String[splitlength];
		for(int i=0;i<splitlength;i++){
			historypath[i] = onepath[i];
		}
		return historypath;
	}
	
	public String getTail(String[] onepath){
		return onepath[onepath.length-1];
	}
	
	public static void main(String[] args) {
		String[] onepath={"C0000","C0001","C0002"};
		String Xf = "C0002";
		SubPathMatcher a = new SubPathMatcher();
		//String[] History = {"C0002"};
		System.out.println(a.isSubpath(new String[]{"C0000"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0001"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0002"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0001","C0002"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0000","C0001"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0000","C0001","C0002"},onepath));
		System.out.println(a.isSubpath(new String[]{"C0000","C0002"},onepath));
		System.out.println();
		System.out.println(a.isSubpathAndTail(new String[]{"C0000","C0001"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0000"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0001"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0002"},Xf,onepath));
		System.out.println(a.isSubpathAndTail(new String[]{"C0003"},Xf,onepath));
		System.out.println();

	}
}
