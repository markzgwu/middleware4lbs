package com.pir.crypto.pir;

import java.math.BigInteger;
import java.util.List;

public interface PIR {
	public class Query implements java.io.Serializable {
		public int maxWidth;
		public Object query;
		public Query(int maxWidth, Object query) {this.maxWidth = maxWidth; this.query = query;}
	}
	
	public Object     generateParams();
	public Query      generateQuery(Object params, int index, int size, int maxWidth);
	public Object     generateResponse(List db, int width, Query query);
	public BigInteger processResponse(Object params, Object response, int index, int size, int maxWidth);
}
