package org.examples.v1.json;

import java.util.ArrayList;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone.BufferManager;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone.BufferWorker;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone.CellData;

import com.alibaba.fastjson.JSON;

public class Fastjson {

	public static void main(String[] args) {
		ArrayList<String> a = new ArrayList<String>();
		for(int i=0;i<10;i++){
			a.add("abc"+i);
		}
		System.out.println(JSON.toJSONString(a));
		
		CellData c = new CellData();
		c.setCellID("cellID");
		c.getPOIs().put("aaaa", new POIentry());
		System.out.println(JSON.toJSONString(c));
		
		final BufferManager task = BufferWorker.getBuffer();
		task.write(c.getCellID(), c);

	}

}
