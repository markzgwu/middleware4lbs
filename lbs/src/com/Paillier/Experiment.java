package com.Paillier;

import java.math.BigInteger;

public final class Experiment {
	public static void main(String[] str) {
		String output = "";
		double averagetime = 0;
		for(int i=1;i<11;i++){
			double T = i*1000000;
			double time = paillieroutput(T);
			averagetime+=time;
			output+=time+",";	
		}
		System.out.println(averagetime/10);	
		System.out.println(output);	
	}
	
	static double output(double T){
		
		double Intersection_AB = 100;
		double Unionsection_AB = 200;
		
		//double T = 10000;
		double c =  T / Intersection_AB ;
		c = Math.floor(c);
		double tmp1 = c * Unionsection_AB;
		double J_AB = T / tmp1;
		
		return J_AB;
	}
	
	static double paillieroutput(double T){
		/* instantiating an object of Paillier cryptosystem*/
		long alicestart = System.currentTimeMillis();
		Paillier paillier = new Paillier();
		
		BigInteger ca_A = new BigInteger("100");
		BigInteger ca_B = new BigInteger("200");
		BigInteger Intersection_AB = new BigInteger("100");
		
		BigInteger em_ca_A = paillier.Encryption(ca_A);
		//BigInteger em_ca_B = paillier.Encryption(ca_B);
		long aliceend = System.currentTimeMillis();
		
		//bob start
		long start = System.currentTimeMillis();
		BigInteger em_ca_B_sub_ca_i_AB = paillier.Encryption(ca_B.subtract(Intersection_AB));
		
		//BigInteger product_em1em2 = em1.multiply(em2).mod(paillier.nsquare);
		BigInteger em_Unionsection_AB = em_ca_A.multiply(em_ca_B_sub_ca_i_AB).mod(paillier.nsquare);		

		//System.out.println(paillier.Decryption(em_Unionsection_AB).toString());
		
		//double ca_A = 100;
		//double ca_B = 200;
		//double Intersection_AB = 100;
		//double Unionsection_AB = ca_A+ca_B-Intersection_AB;
		
		//double T = 10000;
		double c =  T / Intersection_AB.doubleValue();
		c = Math.floor(c);
		BigInteger BigInt_c = BigInteger.valueOf((long)c);
		//double tmp1 = c * Unionsection_AB;
		//double J_AB = T / tmp1;
		
		BigInteger em_c_Unionsection_AB = em_Unionsection_AB.modPow(BigInt_c, paillier.nsquare);
		long end = System.currentTimeMillis();
		/*bob end*/
		
		long alicestart1 = System.currentTimeMillis();
		double c_Unionsection_AB = paillier.Decryption(em_c_Unionsection_AB).doubleValue();
		//System.out.println(paillier.Decryption(em_c_Unionsection_AB).toString());
		long aliceend1 = System.currentTimeMillis();
		
		double J_AB = T / c_Unionsection_AB;
		double bobtime = (end - start); 
		double alicetime = (aliceend - alicestart)+(aliceend1 - alicestart1); 
		//return J_AB;
		//return alicetime;
		return bobtime;
	}
}
