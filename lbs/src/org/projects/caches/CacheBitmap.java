package org.projects.caches;

import java.util.BitSet;

import org.projects.datamodel.Grid;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.Convertor;

public final class CacheBitmap implements ICache{

	public final BitSet bufferIndex;
	public final RectEnv rectenv;
	
	public CacheBitmap(RectEnv rectenv){
		this.rectenv = rectenv;
		this.bufferIndex = new BitSet(rectenv.getLevel().powerLevelOf4);
	}
	
	public void writeBit(int i,int j){
		final int bitnum = i*(rectenv.getLevel().powerLevelOf2)+j;
		bufferIndex.set(bitnum);
	}
	
	public boolean readBit(int i,int j){
		final int bitnum = i*(rectenv.getLevel().powerLevelOf2)+j;
		return bufferIndex.get(bitnum);
	}

	Grid convert(String cellid){
		PointInPlane pPlane = rectenv.spatialSpliter.quadtreeDecoder(cellid);
		Grid grid = Convertor.convert(pPlane);
		return grid;
	}
	
	@Override
	public boolean check(String cellid) {
		Grid grid = convert(cellid);
		return (readBit(grid.i,grid.j)==true);
	}

	@Override
	public String load(String cellid) {
		Grid grid = convert(cellid);
		return String.valueOf(readBit(grid.i,grid.j));
	}

	@Override
	public boolean save(String cellid, String value) {
		Grid grid = convert(cellid);
		writeBit(grid.i,grid.j);
		return true;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	
}
