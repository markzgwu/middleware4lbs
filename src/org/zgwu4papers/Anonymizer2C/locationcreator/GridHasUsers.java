package org.zgwu4papers.Anonymizer2C.locationcreator;

import java.util.ArrayList;

public final class GridHasUsers {
	public ArrayList<Integer> Users = new ArrayList<Integer>();
	public String showArraylist(){
		String r = "[";
		for(Integer o:Users){
			r += (o+",");
		}
		r += "]";
		return r;
	}
		
}
