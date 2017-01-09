package com.pir.crypto.encrypt;

import java.math.BigInteger;

public interface Encryption {
	public Object[]   keygen();
	public BigInteger encrypt(Object key, BigInteger P);
	public BigInteger encryptDomain(Object key);
	public BigInteger encryptRange(Object key);
	public BigInteger decrypt(Object key, BigInteger C);
	public BigInteger recrypt(Object key, BigInteger C);
}