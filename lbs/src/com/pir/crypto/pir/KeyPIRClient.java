package com.pir.crypto.pir;

import com.pir.crypto.HashFunction;
import java.math.BigInteger;
import java.util.Random;

public class KeyPIRClient {
	protected PIRClient client;
	protected KeyPIRServer server;
	
	public KeyPIRClient(PIR pir, KeyPIRServer server) {
		this.server = server;
		client = new PIRClient(pir, server);
	}
	
	public Object get(Object k) {
		if (!(k instanceof BigInteger))
			return null;
		
		BigInteger key = (BigInteger)k;
		
		try {
			HashFunction h = server.hash();
			int index = h.hash(key).intValue();
			int size = server.size();
			if (index < 0 || index >= size) {
				client.get(0);
				return null;
			}
			
			Object result = client.get(index);
			if (result == null)
				return null;
			return result;
		}
		catch (java.rmi.RemoteException e) {
			return null;
		}
	}
}
