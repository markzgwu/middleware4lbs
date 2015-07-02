package com.pir.crypto.pir.test;

import java.math.BigInteger;
import java.util.*;
//import math.algebra.*;
import com.pir.math.*;
import com.pir.crypto.encrypt.*;
import com.pir.crypto.pir.*;
import com.pir.crypto.*;

public class TestKeyPIR {
	public static void main(String args[]) throws java.rmi.RemoteException {
		Random rnd = new Random();
		
		int width = 4;
		int size = 25;
		int testsize = 25;
		
		/*BigInteger p = new BigInteger("833955320468959800533775396417474954583");
		BigInteger q = p.subtract(BigInteger.ONE).shiftRight(1);
		
		BigInteger factor[] = {p};
		int multiple[] = {1};
		
		CyclicIntegerStarSubGroup group = CyclicIntegerStarSubGroup.getGroup(factor, multiple, q);
		GroupElement g = group.fromBigInteger(BigInteger.ONE.negate());
		GroupMapping map = new CyclicMapping(group, g, BigInteger.valueOf(2).pow(1));
		*/
		
		//HomomorphicEncryption c = new ElGamal(group, map, rnd);
		//HomomorphicEncryption c = new GoldwasserMicali(1024, rnd);
		HomomorphicEncryption c = new Paillier(1024, rnd);
		
		Map db = new HashMap(size);
		for (int i = 0; i < size; i++) {
			db.put(new BigInteger(32, rnd), new BigInteger(width, rnd));
		}
		
		//PIR pir = new NaivePIR();
		PIR pir = new KOPIR(c);
		//pir = new ParallelPIR(pir);
		KeyPIRServer server = new KeyPIRServerImpl(pir, db, width, new NaiveHashFunctionGenerator());
		KeyPIRClient client = new KeyPIRClient(pir, server);
		
		double start = System.currentTimeMillis();
		int count = 0;
		for (int z = 0; z < testsize; z++) {
			int choice = rnd.nextInt(2);
			BigInteger key;
			if (choice == 0) {
				key = new BigInteger(32, rnd);
				System.out.print(key + " ");
			}
			else {
				key = (BigInteger)(new ArrayList(db.keySet())).get(rnd.nextInt(db.keySet().size()));
				System.out.print(key + "* ");
			}
			
			BigInteger response = (BigInteger)client.get(key);
			BigInteger real = (BigInteger)server.get(key);
			
			System.out.println(response + " " + real);
			if (!((response == null && real == null) || (response != null && response.equals(real)))) {
				count++;
			}
		}
		
		System.out.println(count + " errors out of " + testsize + ".");
		System.out.println("Total elapsed time: " + ((System.currentTimeMillis() - start)));
		
		java.rmi.server.UnicastRemoteObject.unexportObject(server, true);
	}
}
