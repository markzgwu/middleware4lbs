package com.pir.crypto.pir;

import java.math.BigInteger;
import java.util.List;

public class PIRServerImpl extends java.rmi.server.UnicastRemoteObject implements PIRServer {
	protected PIR pir;
	protected List db;
	protected int width;
	
	public PIRServerImpl(PIR pir, List db, int width) throws java.rmi.RemoteException {
		super();
		this.pir = pir;
		this.db = db;
		this.width = width;
	}
	
	public Object generateResponse(PIR.Query query) {
		return pir.generateResponse(db, width, query);
	}
	
	public Object get(int index) {
		return db.get(index);
	}
	
	public int size() {return db.size();}
	public int width() {return width;}
	
	public PIR pir() {return pir;}
}
