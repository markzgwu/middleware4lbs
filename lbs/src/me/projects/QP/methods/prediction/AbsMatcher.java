package me.projects.QP.methods.prediction;

import java.util.ArrayList;

public abstract class AbsMatcher {
	public int countHistoryAndCellid(final String[] History,final String cellid1,final ArrayList<String[]> allpaths){
		int n = 0;
		for(String[] onepath:allpaths){
			n+=isHistoryAndCellid(History,cellid1, onepath);
		}
		return n;
	}
	
	public int countHistory(final String[] History,final ArrayList<String[]> allpaths){
		int n = 0;
		for(String[] onepath:allpaths){
			n+=isHistory(History, onepath);
		}
		return n;
	}	
	
	public int countPast(final String cellid1,final ArrayList<String[]> allpaths){
		int n = 0;
		for(String[] onepath:allpaths){
			n+=isPast(cellid1, onepath);
		}		
		return n;
	}
	
	//cellid1 �Ƿ��� �켣�о�����λ��
	abstract public int isPast(String cellid1, final String[] onepath);

	abstract public int isHistoryAndCellid(final String[] History,final String Xf,final String[] onepath);
	
	//history�Ƿ��ǹ켣��һ����
	abstract public int isHistory(final String[] History,final String[] onepath);

	abstract public boolean isRegExMatch(String[] strings, String[] onepath);
}
