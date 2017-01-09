package com.pir.crypto.encrypt;

import java.math.BigInteger;

public interface BlindableEncryption extends Encryption {
	public BigInteger randomize(Object key, BigInteger C);
}