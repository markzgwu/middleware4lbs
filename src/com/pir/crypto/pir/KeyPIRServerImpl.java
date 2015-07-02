package com.pir.crypto.pir;

import com.pir.crypto.HashFunction;
import com.pir.crypto.HashFunctionGenerator;

import java.math.BigInteger;
import java.util.*;

public class KeyPIRServerImpl extends java.rmi.server.UnicastRemoteObject implements KeyPIRServer {
	private class KeyPIRList extends AbstractList {
		private Map map;
		private HashFunctionGenerator hg;
		private HashFunction h;
		private List list;
		
		public KeyPIRList(Map map, HashFunctionGenerator hg) {
			this.map = map;
			this.hg = hg;
		}
		
		public void update() {
			// FIXME: Shouldn't do this unless map has changed
			h = hg.hashFunction(new ArrayList(map.keySet()));
			
			list = new ArrayList(map.keySet().size());
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				BigInteger key = (BigInteger)it.next();
				int index = h.hash(key).intValue();
				list.add(index, map.get(key));
			}
		}
		
		public HashFunction hash() {
			update();
			return h;
		}
		
		public Object get(int index) {
			update();
			return list.get(index);
		}
		
		public int size() {
			update();
			return list.size();
		}
	}
	
	private PIRServer server;
	private Map map;
	private List db;
	
	public KeyPIRServerImpl(PIR pir, Map map, int width, HashFunctionGenerator hg) throws java.rmi.RemoteException {
		this.map = map;
		db = new KeyPIRList(map, hg);
		server = new PIRServerImpl(pir, db, width);
		java.rmi.server.UnicastRemoteObject.unexportObject(server, true);
	}
	
	public HashFunction hash() {
		return ((KeyPIRList)db).hash();
	}
	
	public BigInteger get(BigInteger key) {
		return (BigInteger)map.get(key);
	}
	
	public Object generateResponse(PIR.Query query) throws java.rmi.RemoteException {return server.generateResponse(query);}
	public Object get(int index) throws java.rmi.RemoteException {return server.get(index);}
	public int size() throws java.rmi.RemoteException {return server.size();}
	public int width() throws java.rmi.RemoteException {return server.width();}
	public PIR pir() throws java.rmi.RemoteException {return server.pir();}
}
