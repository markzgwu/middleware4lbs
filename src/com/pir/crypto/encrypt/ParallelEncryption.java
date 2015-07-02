package com.pir.crypto.encrypt;

import java.math.BigInteger;
import com.pir.math.BigIntegerUtils;

public class ParallelEncryption implements Encryption, java.io.Serializable {
	private static class Key implements java.io.Serializable {
		public Object key;
		public int reps;
		public Key(Object key, int reps) {this.key = key; this.reps = reps;}
	}
	
	private Encryption c;
	private BigInteger domain;
	
	public ParallelEncryption(Encryption c, BigInteger domain) {this.c = c; this.domain = domain;}
	
	public String toString() {
		return "Parallel encryption with domain ~ 2^" + domain.bitLength() + " of (" + c + ")";
	}
	
	public Object[] keygen() {
		Object[] pair = c.keygen();
		BigInteger dom = c.encryptDomain(pair[0]);
		BigInteger rdom = domain;
		int reps = BigIntegerUtils.log(rdom, dom);
		
		pair[0] = new Key(pair[0], reps);
		pair[1] = new Key(pair[1], reps);
		
		return pair;
	}
	
	public BigInteger encrypt(Object ky, BigInteger P) {
		if (!(ky instanceof Key))
			return null;
		Key key = (Key)ky;
		
		int reps = key.reps;
		BigInteger dom = c.encryptDomain(key.key);
		BigInteger range = c.encryptRange(key.key);
		int shift = range.subtract(BigInteger.ONE).bitLength();
		BigInteger result = BigInteger.ZERO;
		while (reps > 0) {
			BigInteger single = c.encrypt(key.key, P.mod(dom));
			if (single == null)
				return null;
			result = result.shiftLeft(shift).add(single);
			P = P.divide(dom);
			reps--;
		}
		
		return result;
	}
	
	public BigInteger encryptDomain(Object key) {
		return domain;
	}
	
	public BigInteger encryptRange(Object ky) {
		if (!(ky instanceof Key))
			return null;
		Key key = (Key)ky;
		return c.encryptRange(key.key).multiply(BigInteger.valueOf(key.reps));
	}
	
	public BigInteger decrypt(Object ky, BigInteger C) {
		if (!(ky instanceof Key))
			return null;
		Key key = (Key)ky;
		
		int reps = key.reps;
		BigInteger dom = c.encryptDomain(key.key);
		BigInteger range = c.encryptRange(key.key);
		int shift = range.subtract(BigInteger.ONE).bitLength();
		BigInteger mask = BigInteger.valueOf(2).pow(shift).subtract(BigInteger.ONE);
		BigInteger result = BigInteger.ZERO;
		while (reps > 0) {
			BigInteger single = c.decrypt(key.key, C.and(mask));
			if (single == null)
				return null;
			result = result.multiply(dom).add(single);
			C = C.shiftRight(shift);
			reps--;
		}
		
		return result;
	}
	
	public BigInteger recrypt(Object key, BigInteger C) {
		return encrypt(key, decrypt(key, C));
	}
}