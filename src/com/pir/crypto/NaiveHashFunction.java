package com.pir.crypto;

import java.util.List;
import java.util.Iterator;
import java.math.BigInteger;

public class NaiveHashFunction implements HashFunction {
	private List list;
	
	public NaiveHashFunction(List list) {
		this.list = list;
	}
	
	public BigInteger hash(BigInteger value) {
		int i = 0;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			BigInteger next = (BigInteger)it.next();
			if (value.equals(next))
				return BigInteger.valueOf(i);
			i++;
		}
		
		return BigInteger.valueOf(i);
	}
}
