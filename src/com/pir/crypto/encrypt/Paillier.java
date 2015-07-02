package com.pir.crypto.encrypt;

import java.math.BigInteger;
import java.util.Random;
import com.pir.math.BigIntegerUtils;

public class Paillier implements HomomorphicEncryption, BlindableEncryption, java.io.Serializable {
	private static class PublicKey implements java.io.Serializable {
		public BigInteger n;
		public transient BigInteger n2;
		
		private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
			in.defaultReadObject();
			n2 = n.multiply(n);
		}
		
		public PublicKey(BigInteger n) {this.n=n; n2 = n.multiply(n);}
	}
	
	private static class PrivateKey extends PublicKey {
		public BigInteger l;
		public PrivateKey(BigInteger n, BigInteger l) {super(n); this.l=l;}
	}
		
	private static BigInteger L(BigInteger x, BigInteger n) {
		return x.subtract(BigInteger.ONE).divide(n);
	}
	
	private int k;
	private transient Random rnd;
	
	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
		rnd = new Random();
	}
	
	public Paillier(int k, Random rnd) {this.k = k; this.rnd = rnd;}
	public Paillier(int k) {this(k, new Random());}
	
	public String toString() {return "Paillier, k=" + k;}
	
	public Object[] keygen() {
		BigInteger p = null, q = null, n = null;
		do {
			p = BigInteger.probablePrime(k/2+1, rnd);
			q = BigInteger.probablePrime(k-(k/2), rnd);
			n = p.multiply(q);
		} while (n.bitLength() != k+1);
		
		BigInteger pm1 = p.subtract(BigInteger.ONE);
		BigInteger qm1 = q.subtract(BigInteger.ONE);
		BigInteger phi = pm1.multiply(qm1);
		BigInteger l = phi.divide(pm1.gcd(qm1));
		
		Object[] result = {new PublicKey(n), new PrivateKey(n, l)};
		
		return result;
	}
	
	public BigInteger encrypt(Object ky, BigInteger P) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		
		BigInteger r = BigIntegerUtils.randomInterval(key.n, rnd);
		
		BigInteger c = P.multiply(key.n).add(BigInteger.ONE).multiply(r.modPow(key.n, key.n2)).mod(key.n2);
		return c;
	}
	
	public BigInteger encryptDomain(Object ky) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		return key.n;
	}
	
	public BigInteger encryptRange(Object ky) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		return key.n2;
	}
	
	public BigInteger decrypt(Object ky, BigInteger C) {
		if (!(ky instanceof PrivateKey))
			return null;
		PrivateKey key = (PrivateKey)ky;
		BigInteger m = L(C.modPow(key.l, key.n2), key.n).multiply(L(key.l.multiply(key.n).add(BigInteger.ONE), key.n).modInverse(key.n)).mod(key.n);
		return m;
	}
	
	public BigInteger add(Object ky, BigInteger C1, BigInteger C2) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		return C1.multiply(C2).mod(key.n2);	
	}
	
	public BigInteger multiply(Object ky, BigInteger m, BigInteger C1) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		return C1.modPow(m, key.n2);
	}
	
	public BigInteger recrypt(Object ky, BigInteger C) {
		return randomize(ky, C);	
	}
	
	public BigInteger randomize(Object ky, BigInteger C) {
		if (!(ky instanceof PublicKey))
			return null;
		PublicKey key = (PublicKey)ky;
		
		BigInteger r = BigIntegerUtils.randomInterval(key.n, rnd);
		
		return C.multiply(r.modPow(key.n, key.n2)).mod(key.n2);
	}
}
