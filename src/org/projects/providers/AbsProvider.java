package org.projects.providers;

public abstract class AbsProvider {
	abstract public String retrieval(String cellkey);
	public String retrieval(String[] celllist) {
		String r = "";
		for(String cell:celllist){
			r+=retrieval(cell)+"\n";
		}
		return r;
	}
}
