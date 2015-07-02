package com.pir.crypto.pir;

import java.math.BigInteger;
import java.util.List;

public class NaivePIR implements PIR, java.io.Serializable {
	public Object generateParams() {return new Object();}
	public PIR.Query generateQuery(Object params, int index, int size, int maxWidth) {
		return new PIR.Query(-1, new Object());
	}
	public Object generateResponse(List db, int width, PIR.Query query) {
		return db;
	}
	public BigInteger processResponse(Object params, Object response, int index, int size, int maxWidth) {
		return (BigInteger)((List)response).get(index);
	}
}
