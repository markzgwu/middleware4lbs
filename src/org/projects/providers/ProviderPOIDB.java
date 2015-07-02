package org.projects.providers;

public final class ProviderPOIDB extends AbsProvider implements IDBProvider{
	private int counter = 0;
	@Override
	public String retrieval(String cellid) {
		// TODO Auto-generated method stub
		counter++;
		return "Data("+cellid+")";
	}

	public final int getCounter() {
		return counter;
	}
 
}
