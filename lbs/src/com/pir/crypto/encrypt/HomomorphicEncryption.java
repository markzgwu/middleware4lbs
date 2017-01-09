package com.pir.crypto.encrypt;

import java.math.BigInteger;

public interface HomomorphicEncryption extends Encryption {
	public BigInteger add(Object key, BigInteger C1, BigInteger C2);
	public BigInteger multiply(Object key, BigInteger m, BigInteger C);
}