package com.pir.crypto.pir;

import java.math.BigInteger;
import java.util.List;
import com.pir.util.BigIntegerSlice;

public class ParallelPIR implements PIR, java.io.Serializable {
	private static class Response implements java.io.Serializable {
		public Object value;
		public Response(Object value) {this.value = value;}
	}
	
	private PIR pir;
	
	public ParallelPIR(PIR pir) {this.pir = pir;}
	
	public Object generateParams() {
		return pir.generateParams();
	}
	
	public Query generateQuery(Object params, int index, int size, int maxWidth) {
		return new PIR.Query(maxWidth, pir.generateQuery(params, index, size, maxWidth));
	}
	
	public Object generateResponse(List db, int width, Query fquery) {
		Query query = (Query)fquery.query;
		int maxWidth = query.maxWidth;
		
		if (maxWidth == -1) {
			Response response[] = {new Response(pir.generateResponse(db, width, query))};
			Object fresponse[] = {new Integer(width), response};
			return fresponse;
		}
		else {
			int slices = (width + maxWidth - 1) / maxWidth;
			Response response[] = new Response[slices];
			for (int i = 0; i < slices; i++) {
				response[i] = new Response(pir.generateResponse(new BigIntegerSlice(db, i, maxWidth), maxWidth, query));
				if (response[i].value == null)
					return null;
			}
			Object fresponse[] = {new Integer(maxWidth), response};
			return fresponse;
		}
	}
	
	public BigInteger processResponse(Object params, Object ffresponse, int index, int size, int maxWidth) {
		if (ffresponse == null)
			return null;
		
		Object fresponse[] = (Object[])ffresponse;
		int width = ((Integer)fresponse[0]).intValue();
		Object response[] = (Object[])fresponse[1];
		
		BigInteger result[] = new BigInteger[response.length];
		for (int i = 0; i < response.length; i++) {
			result[i] = pir.processResponse(params, ((Response)response[i]).value, index, size, maxWidth);
			if (result[i] == null)
				return null;
		}

		BigInteger value = BigInteger.ZERO;
		for (int i = response.length - 1; i >= 0; i--) {
			value = value.shiftLeft(width).add(result[i]);	
		}
		return value;
	}
}
