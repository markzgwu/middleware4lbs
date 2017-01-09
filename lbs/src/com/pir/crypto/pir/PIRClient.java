package com.pir.crypto.pir;

import java.math.BigInteger;

public class PIRClient extends java.util.AbstractList {
	protected PIR pir;
	protected PIRServer server;
	protected Object params;
	
	public PIRClient(PIR pir, PIRServer server) {this.pir = pir; this.server = server; this.params = pir.generateParams();}
	
	public Object get(int index) {
		try {
			int size = server.size();
			if (index < 0 || index >= size)
				return null;
			
			int maxWidth = server.width();
			PIR.Query query = pir.generateQuery(params, index, size, maxWidth);
			Object response = server.generateResponse(query);
			Object result = pir.processResponse(params, response, index, size, maxWidth);
			return result;
		}
		catch (java.rmi.RemoteException e) {
			return null;
		}
	}
	
	public int size() {
		try {
			return server.size();
		}
		catch (java.rmi.RemoteException e) {
			return 0;
		}
	}
	
	public int width() {
		try {
			return server.size();
		}
		catch (java.rmi.RemoteException e) {
			return 0;
		}
	}
	public PIR pir() {return pir;}
	
	public PIRServer server() {return server;}
}