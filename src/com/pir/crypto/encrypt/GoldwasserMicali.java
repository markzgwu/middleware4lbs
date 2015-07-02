package com.pir.crypto.encrypt;

import java.math.BigInteger;
import java.util.Random;
import com.pir.math.BigIntegerUtils;

public class GoldwasserMicali implements HomomorphicEncryption, BlindableEncryption, java.io.Serializable {
	private static class PublicKey implements java.io.Serializable {
		public BigInteger n, y;
		public PublicKey(BigInteger n, BigInteger y) {this.n=n; this.y=y;}
	}
	
	private static class PrivateKey extends PublicKey {
		public BigInteger p;
		public PrivateKey(BigInteger n, BigInteger y, BigInteger p) {super(n,y); this.p=p;}
	}
	
	private int k;
	private transient Random rnd;
	
	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
		rnd = new Random();
	}
	
	public GoldwasserMicali(int k, Random rnd) {this.k = k; this.rnd = rnd;}
	public GoldwasserMicali(int k) {this(k, new Random());}
	
	public String toString() {return "Goldwasser-Micali, k=" + k;}
	
	public Object[] keygen() {
		BigInteger p = null, q = null, n = null;
		do {
			p = BigInteger.probablePrime(k/2, rnd);
			q = BigInteger.probablePrime(k-(k/2), rnd);
			n = p.multiply(q);
		} while (n.bitLength() != k);
		
		BigInteger a = null;
		do {
			a = BigIntegerUtils.randomInterval(p, rnd);
		}
		while (BigIntegerUtils.jacobi(a, p) != -1);
		
		BigInteger b = null;
		do {
			b = BigIntegerUtils.randomInterval(q, rnd);
		}
		while (BigIntegerUtils.jacobi(b, q) != -1);
		
		BigInteger y1 = a.multiply(q).multiply(q.modInverse(p)).mod(n);
		BigInteger y2 = b.multiply(p).multiply(p.modInverse(q)).mod(n);
		BigInteger y = y1.add(y2).mod(n);
		
		Object[] result = {new PublicKey(n, y), new PrivateKey(n, y, p)};
		
		return result;
	}
	
	public BigInteger encrypt(Object ky, BigInteger P) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		
		BigInteger x = BigIntegerUtils.randomInterval(key.n, rnd);
		x = x.multiply(x).mod(key.n);
		
		if (P.equals(BigInteger.ZERO))
			return x;
		else
			return key.y.multiply(x).mod(key.n);
	}
	
	public BigInteger encryptDomain(Object ky) {
		return BigInteger.valueOf(2);
	}
	
	public BigInteger encryptRange(Object ky) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		return key.n;
	}
	
	public BigInteger decrypt(Object ky, BigInteger C) {
		if (!(ky instanceof PrivateKey))
			return null;
		PrivateKey key = (PrivateKey)ky;
		
		int e = BigIntegerUtils.jacobi(C, key.p);
		if (e == 1)
			return BigInteger.valueOf(0);
		else
			return BigInteger.valueOf(1);
	}
	
	public BigInteger add(Object ky, BigInteger C1, BigInteger C2) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		return C1.multiply(C2).mod(key.n);
	}
	
	public BigInteger multiply(Object ky, BigInteger m, BigInteger C1) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		if (m.equals(BigInteger.ZERO))
			return BigInteger.ONE;
		else
			return C1;
	}
	
	public BigInteger recrypt(Object ky, BigInteger C) {
		return randomize(ky, C);	
	}
	
	public BigInteger randomize(Object ky, BigInteger C) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		
		BigInteger x = BigIntegerUtils.randomInterval(key.n, rnd);
		x = x.multiply(x).mod(key.n);
		return C.multiply(x).mod(key.n);
	}
}