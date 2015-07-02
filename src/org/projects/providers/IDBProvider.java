package org.projects.providers;

public interface IDBProvider {
	abstract public String retrieval(String cellkey);
	abstract public String retrieval(String[] celllist);
}
