package com.pir.crypto.pir;

import com.pir.crypto.HashFunction;
import java.rmi.RemoteException;
import java.math.BigInteger;

public interface KeyPIRServer extends PIRServer {
	public HashFunction hash() throws RemoteException;
	public BigInteger get(BigInteger key) throws RemoteException;
}
