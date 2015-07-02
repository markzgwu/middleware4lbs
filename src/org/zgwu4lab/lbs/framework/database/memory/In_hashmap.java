package org.zgwu4lab.lbs.framework.database.memory;

import java.util.HashMap;

import org.parameters.I_constant;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;

public class In_hashmap {
	public static void main(String[] args) {
		System.out.println("loading "+I_constant.default_POIDB_filepath);
		final HashMap<String,POIentry> POIs = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
		System.out.println("The POI dataset's size: "+POIs.size());
	}
}
