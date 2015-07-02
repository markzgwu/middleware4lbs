package org.projects.providers;

public final class ProviderSimple extends AbsProvider implements IDBProvider{

	@Override
	public String retrieval(final String cellid) {
		// TODO Auto-generated method stub
		return "Data("+cellid+")";
	}

}
