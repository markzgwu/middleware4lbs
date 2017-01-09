package com.pir.crypto;

import java.math.BigInteger;

public interface HashFunction extends java.io.Serializable {
	public BigInteger hash(BigInteger i);
}
