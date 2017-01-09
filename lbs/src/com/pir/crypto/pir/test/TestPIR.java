package com.pir.crypto.pir.test;

import java.math.BigInteger;
import java.util.*;
//import math.algebra.*;
import com.pir.math.*;
import com.pir.crypto.encrypt.*;
import com.pir.crypto.pir.*;

public class TestPIR {
	public static void main(String args[]) throws java.rmi.RemoteException {
		Random rnd = new Random();
		
		//int width = 1;
		//int size = 25;
		//int testsize = 4;
		
		int width = 8;
		int size = 1024;
		int testsize = 4;
		
		/*
		BigInteger p = new BigInteger("833955320468959800533775396417474954583");
		BigInteger q = p.subtract(BigInteger.ONE).shiftRight(1);
		
		BigInteger factor[] = {p};
		int multiple[] = {1};
		
		CyclicIntegerStarSubGroup group = CyclicIntegerStarSubGroup.getGroup(factor, multiple, q);
		GroupElement g = group.fromBigInteger(BigInteger.ONE.negate());
		GroupMapping map = new CyclicMapping(group, g, BigInteger.valueOf(2).pow(1));
		*/
		
		//HomomorphicEncryption c = new ElGamal(group, map, rnd);
		HomomorphicEncryption c = new GoldwasserMicali(1024, rnd);
		//HomomorphicEncryption c = new Paillier(1024, rnd);
		
		List<BigInteger> db = new ArrayList<BigInteger>(size);
		for (int i = 0; i < size; i++) {
			db.add(new BigInteger(width, rnd));
		}
		
		//PIR pir = new NaivePIR();
		PIR pir = new KOPIR(c);
		pir = new ParallelPIR(pir);
		PIRServer server = new PIRServerImpl(pir, db, width);
		PIRClient client = new PIRClient(pir, server);
		
		double start = System.currentTimeMillis();
		int count = 0;
		for (int z = 0; z < testsize; z++) {
			int i = rnd.nextInt(size);
			BigInteger response = (BigInteger)client.get(i);
			BigInteger real = (BigInteger)server.get(i);
			System.out.print(response+";");
			//System.out.print(".");
			if (!response.equals(real)) {
				count++;
			}
		}
		System.out.println();
		
		System.out.println(count + " errors out of " + testsize + ".");
		System.out.println("Total elapsed time: " + ((System.currentTimeMillis() - start)));
		
		java.rmi.server.UnicastRemoteObject.unexportObject(server, true);
	}
}
