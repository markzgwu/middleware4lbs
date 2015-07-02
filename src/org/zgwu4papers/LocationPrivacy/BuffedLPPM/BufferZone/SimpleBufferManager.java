package org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone;

import java.util.HashMap;

public final class SimpleBufferManager {

	HashMap<String,String> bufferPOI = new HashMap<String,String>();
	final int buffersize = 100;
	
	boolean isFull(){
		return bufferPOI.size() > (buffersize-1);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
