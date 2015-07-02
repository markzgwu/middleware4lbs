package com.pir.crypto;

import java.util.List;

public class NaiveHashFunctionGenerator implements HashFunctionGenerator {
	public HashFunction hashFunction(List list) {
		return new NaiveHashFunction(list);	
	}
}
