package com.pir.crypto.pir;

import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PIRServer extends Remote {
	public Object generateResponse(PIR.Query query) throws RemoteException;
	
	public Object get(int index) throws RemoteException;
	public int    size() throws RemoteException;
	public int    width() throws RemoteException;
	public PIR    pir() throws RemoteException;
}
