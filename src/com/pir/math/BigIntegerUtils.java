package com.pir.math;

import java.math.BigInteger;

public class BigIntegerUtils {
	public static BigInteger randomInterval(BigInteger max, java.util.Random rnd) {
		int bits = max.bitLength() + 1;
		BigInteger k = null;
		do {
			k = new BigInteger(bits, rnd);
		} while (k.compareTo(max) >= 0);
		
		return k;
	}
	
	public static boolean isValidFactorization(BigInteger factor[], int multiple[]) {
		if (factor.length == 0 || factor.length != multiple.length)
			return false;
		
		for (int i = 0; i < factor.length; i++) {
			if (!factor[i].isProbablePrime(100) || multiple[i] <= 0)
				return false;
			
			for (int j = 0; j < i; j++) {
				if (factor[i].equals(factor[j]))
					return false;
			}
		}
		
		return true;
	}
	
	public static int jacobi(BigInteger a, BigInteger n) {
		int cs = 1;
		
		while (true) {
			if (a.equals(BigInteger.ZERO))
				return cs*0;
			else if (a.equals(BigInteger.ONE))
				return cs*1;
			
			int e = a.getLowestSetBit();
			BigInteger a2 = a.shiftRight(e);
			
			int s = 1;
			if (e % 2 != 0) {
				BigInteger r = n.and(BigInteger.valueOf(7));
				if (r.equals(BigInteger.valueOf(3)) || r.equals(BigInteger.valueOf(5)))
					s = -1;
			}
			
			if ( n.and(BigInteger.valueOf(3)).equals(BigInteger.valueOf(3)) &&
				a2.and(BigInteger.valueOf(3)).equals(BigInteger.valueOf(3))) {
				s = -s;
			}
			
			if (a2.equals(BigInteger.valueOf(1)))
				return cs*s;
			
			BigInteger n2 = n.mod(a2);
			
			a = n2;
			n = a2;
			cs = cs * s;
		}
	}
	
	public static int log(BigInteger r, BigInteger d) {
		int e = 0;
		while (r.compareTo(BigInteger.ZERO) > 0) {
			r = r.divide(d);
			e++;
		}
		
		return e;
	}
	
	public static int pow(int b, int e) {
		int r = 1;
		while (e > 0) {
			if ((e & 1) == 1)
				r = r * b;
			b = b * b;
			e = e >> 1;
		}
		
		return r;
	}
}