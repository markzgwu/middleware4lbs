package com.pir.crypto.encrypt.test;

import java.math.BigInteger;
import java.util.*;
//import math.algebra.*;
import com.pir.math.*;
import com.pir.crypto.encrypt.*;

public class TestEncrypt {
	private static double testDuration = 5000;
	
	public static void testPK(Encryption c, Random rnd, boolean test) {
		double start = 0;
		double time = 0;
		int i = 0;
		
		System.out.println("Testing PK:\n" + c);
		
		start = System.currentTimeMillis();
		for (i = 0; ; i++) {
			if (System.currentTimeMillis() - start > testDuration)
				break;
			c.keygen();
		}
		System.out.println("K = " + ((System.currentTimeMillis() - start) / i));
		
		Object[] pair = c.keygen();
		int bits = c.encryptDomain(pair[0]).bitLength() - 1;
		
		System.out.println("Encrypt domain: " + bits + " bits");
			
		start = System.currentTimeMillis();
		time = 0;
		for (i = 0; ; i++) {
			if (System.currentTimeMillis() - start > testDuration)
				break;
			double pause = System.currentTimeMillis();
			BigInteger P = new BigInteger(bits, rnd);
			time = time + (System.currentTimeMillis() - pause);
			c.encrypt(pair[0], new BigInteger(bits, rnd));
		}
		time = (System.currentTimeMillis() - start - time) / i;
		System.out.println("E = " + time + ", " + time / bits + " per bit");
		
		boolean valid = true;
		start = System.currentTimeMillis();
		time = 0;
		for (i = 0; ; i++) {
			if (System.currentTimeMillis() - start > testDuration)
				break;
			double pause = System.currentTimeMillis();
			//pair = c.keygen();
			BigInteger P = new BigInteger(bits, rnd);
			BigInteger C = c.encrypt(pair[0], P);
			time = time + (System.currentTimeMillis() - pause);
			BigInteger P2 = c.decrypt(pair[1], C);
			double pause2 = System.currentTimeMillis();
			if (test && !P2.equals(P))
				valid = false;
			time = time + (System.currentTimeMillis() - pause2);
		}
		time = (System.currentTimeMillis() - start - time) / i;
		System.out.println("D = " + time + ", " + time / bits + " per bit");
		
		if (test) System.out.println("Encrypt/decrypt work: " + valid);
		System.out.println();
	}
	
	public static void testAHPK(HomomorphicEncryption c, Random rnd, boolean test) {
		double start = 0;
		double time = 0;
		int i = 0;
		
		System.out.println("Testing AHPK:\n" + c);
		
		Object[] pair = c.keygen();
		int bits = c.encryptDomain(pair[0]).bitLength() - 1;
		
		boolean valid = true;
		start = System.currentTimeMillis();
		time = 0;
		for (i = 0; ; i++) {
			if (System.currentTimeMillis() - start > testDuration)
				break;
			double pause = System.currentTimeMillis();
			BigInteger P1 = new BigInteger(bits, rnd);
			BigInteger C1 = c.encrypt(pair[0], P1);
			BigInteger P2 = new BigInteger(bits, rnd);
			BigInteger C2 = c.encrypt(pair[0], P2);
			time = time + (System.currentTimeMillis() - pause);
			BigInteger C3 = c.add(pair[0], C1, C2);
			double pause2 = System.currentTimeMillis();
			if (test) {
				BigInteger P3 = c.decrypt(pair[1], C3);
				if (!P3.equals(P1.add(P2).mod(c.encryptDomain(pair[0]))))
					valid = false;
			}
			time = time + (System.currentTimeMillis() - pause2);
		}
		time = (System.currentTimeMillis() - start - time) / i;
		System.out.println("A = " + time + ", " + time / bits + " per bit");
		if (test) System.out.println("Add work: " + valid);
		
		boolean valid2 = true;
		start = System.currentTimeMillis();
		time = 0;
		for (i = 0; ; i++) {
			if (System.currentTimeMillis() - start > testDuration)
				break;
			double pause = System.currentTimeMillis();
			BigInteger P1 = new BigInteger(bits, rnd);
			BigInteger C1 = c.encrypt(pair[0], P1);
			BigInteger P2 = new BigInteger(bits, rnd);
			time = time + (System.currentTimeMillis() - pause);
			BigInteger C2 = c.multiply(pair[0], P2, C1);
			double pause2 = System.currentTimeMillis();
			if (test) {
				BigInteger P3 = c.decrypt(pair[1], C2);
				if (!P3.equals(P1.multiply(P2).mod(c.encryptDomain(pair[0]))))
					valid2 = false;
			}
			time = time + (System.currentTimeMillis() - pause2);
		}
		time = (System.currentTimeMillis() - start - time) / i;
		System.out.println("M = " + time + ", " + time / bits + " per bit");
		if (test) System.out.println("Multiply work: " + valid2);
		System.out.println();
	}
	
	public static void main(String args[]) {
		Random rnd = new Random();
		
		BigInteger q = null, p = null, gen = null;
		/*int k = 384;
		int k2 = k - 256;
		int k3 = 4;
		//p = new BigInteger("284583440037415938284244781228535659235317364592590996006155378836595380368323743827549808016035152702037532974655637913761421686727206954669956692987277295258433830834699826832165806899407366507369080152698768825109590718181092195787132188378117727301163112581214803898264156997270804268729989252420024139777");
		//gen = new BigInteger("192892192912112785602767178791624505843951909003898524749615827141673948896811526647380915388044028268410957282985677775566550157563747735860642718388188757366255443700845263441562097213655013917495287710800015126585199763524452050105887792970919719676400594874778341352385874775619350696835940626127032630143");
		if (p == null) {
			while (true) {
				q = BigInteger.probablePrime(k-k2+1, rnd);
				System.out.print(".");
				p = q.shiftLeft(k2).add(BigInteger.ONE);
				if (p.isProbablePrime(100))
					break;
			}
			System.out.println();
			
			System.out.println(p);
			System.out.println();
		}
		q = p.subtract(BigInteger.ONE).shiftRight(p.subtract(BigInteger.ONE).getLowestSetBit());
		BigInteger phi = p.subtract(BigInteger.ONE).shiftRight(1);
		if (gen == null) {
			BigInteger i = BigInteger.valueOf(2);
			while (true) {
				gen = i.modPow(q, p);
				if (gen.modPow(phi.divide(q), p).equals(BigInteger.ONE) &&
					!gen.modPow(phi.divide(q).subtract(BigInteger.ONE), p).equals(BigInteger.ONE))
					break;
				
				i = i.add(BigInteger.ONE);
			}
			System.out.println(q);
			System.out.println();
			System.out.println(gen);
			System.out.println();
			gen = gen.modPow(BigInteger.ONE.shiftLeft(k3), p);
			System.out.println(gen);
			System.out.println();
		}
		
		BigInteger factor[] = {p};
		int multiple[] = {1};
		
		CyclicIntegerStarSubGroup group = CyclicIntegerStarSubGroup.getGroup(factor, multiple, q);
		GroupElement g = group.fromBigInteger(gen);
		//GroupMapping map = new SafePrimeMapping(group);
		GroupMapping map = new CyclicMapping(group, g, BigInteger.ONE.shiftLeft(k3));
		*/
		
		/*BigInteger p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
		BigInteger r = new BigInteger("6277101735386680763835789423176059013767194773182842284081");
		BigInteger a = new BigInteger("-3");
		BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
		BigInteger x = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
		BigInteger y = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);
		*/
		
		/*BigInteger p = new BigInteger("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF", 16);
		BigInteger r = new BigInteger("FFFFFFFE0000000075A30D1B9038A115", 16);
		BigInteger a = new BigInteger("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFC", 16);
		BigInteger b = new BigInteger("E87579C11079F43DD824993C2CEE5ED3", 16);
		BigInteger x = new BigInteger("161FF7528B899B2D0C28607CA52C5B86", 16);
		BigInteger y = new BigInteger("CF5AC8395BAFEB13C02DA292DDED7A83", 16);
		*/
		
		/*
		BigInteger p = new BigInteger("DB7C2ABF62E35E668076BEAD208B", 16);
		BigInteger r = new BigInteger("DB7C2ABF62E35E7628DFAC6561C5", 16);
		BigInteger a = new BigInteger("DB7C2ABF62E35E668076BEAD2088", 16);
		BigInteger b = new BigInteger("659EF8BA043916EEDE8911702B22", 16);
		BigInteger x = new BigInteger("09487239995A5EE76B55F9C2F098", 16);
		BigInteger y = new BigInteger("A89CE5AF8724C0A23E0E0FF77500", 16);
		
		Field field = IntegerField.getField(p);
		FieldElement fa = field.fromBigInteger(a);
		FieldElement fb = field.fromBigInteger(b);
		FieldElement fx = field.fromBigInteger(x);
		FieldElement fy = field.fromBigInteger(y);
		EllipticCurveGroup group = EllipticCurveGroup.getGroup(fa, fb, fx, fy, r);
		GroupElement g = group.generator();
		
		GroupMapping map = new CyclicMapping(group, g, BigInteger.ONE.shiftLeft(20));
		*/
		
		//HomomorphicEncryption c = new Paillier(1024, rnd);
		//HomomorphicEncryption c = new ElGamal(group, map, rnd);
		HomomorphicEncryption c = new GoldwasserMicali(1024, rnd);
		//ParallelEncryption c2 = new ParallelEncryption(c, BigInteger.valueOf(2).pow(1024));
		testPK(c, rnd, true);
		testAHPK(c, rnd, true);
	}
}