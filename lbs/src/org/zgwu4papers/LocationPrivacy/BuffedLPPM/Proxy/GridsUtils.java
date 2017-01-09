package org.zgwu4papers.LocationPrivacy.BuffedLPPM.Proxy;

import java.util.ArrayList;

import org.parameters.I_constant;
import org.zgwu4lab.lbs.index.grid.GridEncoder;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class GridsUtils {

	static public ArrayList<String> computeGrids(RequestUser req){

		ArrayList<String> grids = null;
		double r;
		for(int j=0;j<5;j++){
			r = j*I_constant.index_grid_granularity;
			grids = GridEncoder.grids_range(req.location.toJsonLocation(),r);
		}
		//System.out.println("# of Grids"+grids.size());
		return grids;
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
