package com.pir.crypto.pir.test;

import java.math.BigInteger;
import java.util.*;
//import math.algebra.*;
import com.pir.math.*;
import com.pir.crypto.encrypt.*;
import com.pir.crypto.pir.*;

public class TestPIRClient {
	public static void main(String args[]) throws Exception {
		Random rnd = new Random();
		
		/*
		BigInteger p = new BigInteger("833955320468959800533775396417474954583");
		BigInteger q = p.subtract(BigInteger.ONE).shiftRight(1);
		System.out.println(p);
		
		BigInteger factor[] = {p};
		int multiple[] = {1};
		
		CyclicIntegerStarSubGroup group = CyclicIntegerStarSubGroup.getGroup(factor, multiple, q);
		GroupElement g = group.fromBigInteger(BigInteger.ONE.negate());
		GroupMapping map = new CyclicMapping(group, g, BigInteger.valueOf(2).pow(1));
		*/
		
		//HomomorphicEncryption c = new ElGamal(group, map, rnd);
		HomomorphicEncryption c = new GoldwasserMicali(1024, rnd);
		//HomomorphicEncryption c = new Paillier(1024, rnd);
		
		PIR pir = new KOPIR(c);
		PIRServer server = (PIRServer)java.rmi.Naming.lookup("//localhost/PIRServer");
		PIRClient client = new PIRClient(pir, server);
		
		double start = System.currentTimeMillis();
		Object response = client.get(Integer.parseInt(args[0]));
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(response);
		System.out.println(server.get(Integer.parseInt(args[0])));
	}
}
