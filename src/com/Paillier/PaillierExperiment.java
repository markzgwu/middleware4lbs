package com.Paillier;

import java.math.BigInteger;

public final class PaillierExperiment {

	public static void main(String[] str) {
		/* instantiating an object of Paillier cryptosystem*/
		Paillier paillier = new Paillier();
		/* instantiating two plaintext msgs*/
		BigInteger m1 = new BigInteger("20");
		BigInteger m2 = new BigInteger("60");
		/* encryption*/
		BigInteger em1 = paillier.Encryption(m1);
		BigInteger em2 = paillier.Encryption(m2);
		/* printout encrypted text*/
		System.out.println(em1);
		System.out.println(em2);
		/* printout decrypted text */
		System.out.println(paillier.Decryption(em1).toString());
		System.out.println(paillier.Decryption(em2).toString());

		/* test homomorphic properties -> D(E(m1)*E(m2) mod n^2) = (m1 + m2) mod n */
		BigInteger product_em1em2 = em1.multiply(em2).mod(paillier.nsquare);
		BigInteger sum_m1m2 = m1.add(m2).mod(paillier.n);
		System.out.println("original sum: " + sum_m1m2.toString());
		System.out.println("decrypted sum: " + paillier.Decryption(product_em1em2).toString());

		/* test homomorphic properties -> D(E(m1)^m2 mod n^2) = (m1*m2) mod n */
		BigInteger expo_em1m2 = em1.modPow(m2, paillier.nsquare);
		BigInteger prod_m1m2 = m1.multiply(m2).mod(paillier.n);
		System.out.println("original product: " + prod_m1m2.toString());
		System.out.println("decrypted product: " + paillier.Decryption(expo_em1m2).toString());
	}

}
