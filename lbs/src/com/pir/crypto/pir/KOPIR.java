package com.pir.crypto.pir;

import java.math.BigInteger;
import java.util.*;
import com.pir.math.BigIntegerUtils;
import com.pir.crypto.encrypt.HomomorphicEncryption;

public class KOPIR implements PIR, java.io.Serializable {
	private static class Params implements java.io.Serializable {
		public Object[] keypair;
		public Params(Object[] keypair) {this.keypair = keypair;}
	}
	
	private HomomorphicEncryption c;
	
	public KOPIR() {this.c = null;}
	public KOPIR(HomomorphicEncryption c) {this.c = c;}
	
	public Object generateParams() {
		if (c == null)
			return null;
		return new Params(c.keygen());
	}
	
	public Query generateQuery(Object params, int index, int size, int maxWidth) {
		if (c == null)
			return null;
		
		if (!(params instanceof Params))
			return null;
		
		Params p = (Params)params;
		int queryDim = 2;
		int querySize = (int)Math.round(Math.ceil(Math.pow(size, 1.0 / queryDim)));
		List query = new ArrayList(queryDim - 1);
		for (int i = 0; i < queryDim - 1; i++) {
			int nonzero = index % querySize;
			List encValues = new ArrayList(querySize);
			for (int j = 0; j < querySize; j++) {
				encValues.add(c.encrypt(p.keypair[0], j == nonzero ? BigInteger.ONE : BigInteger.ZERO));
			}
			query.add(encValues);
			index = index / querySize;
		}
		
		int realWidth = c.encryptDomain(p.keypair[0]).bitLength() - 1;
		if (realWidth < maxWidth)
			maxWidth = realWidth;
		
		Object fquery[] = {c, p.keypair[0], query};
		return new PIR.Query(maxWidth, fquery);
	}
	
	public Object generateResponse(List db, int width, PIR.Query fquery) {
		int maxWidth = fquery.maxWidth;
		Object query[] = (Object[])fquery.query;
		HomomorphicEncryption c = (HomomorphicEncryption)query[0];
		Object key = query[1];
		List encValuesList = (List)query[2];
		
		if (width > maxWidth)
			return null;
		
		return totalFold(c, key, encValuesList, db);
	}
	
	public BigInteger processResponse(Object params, Object response, int index, int size, int maxWidth) {
		if (c == null)
			return null;
		
		if (response == null)
			return null;
		
		if (!(params instanceof Params))
			return null;
		
		Params p = (Params)params;
		
		int queryDim = 2;
		List result = totalUnfold(c, p.keypair[1], (List)response, queryDim - 1);
		
		int querySize = (int)Math.round(Math.ceil(Math.pow(size, 1.0 / queryDim)));
		for (int i = 0; i < queryDim - 1; i++) {
			index = index / querySize;
		}
		
		return (BigInteger)result.get(index);
	}
	
	private static int expansionFactor(HomomorphicEncryption c, Object key) {
		BigInteger d = c.encryptDomain(key);
		BigInteger r = c.encryptRange(key);
		return BigIntegerUtils.log(r, d);
	}
	
	private static List[] fold(HomomorphicEncryption c, Object key, List encValues, List db, int e) {
		int dbSize = db.size();
		int encSize = encValues.size();
		List encDB[] = new List[e];
		for (int i = 0; i < e; i++) {
			encDB[i] = new ArrayList((dbSize + encSize - 1) / encSize);
		}
		
		BigInteger d = c.encryptDomain(key);
		for (int i = 0; i < dbSize; i += encSize) {
			BigInteger value = c.multiply(key, (BigInteger)db.get(i), (BigInteger)encValues.get(0));
			for (int j = 1; j < encSize; j++) {
				if (i + j >= dbSize)
					break;
				value = c.add(key, value, c.multiply(key, (BigInteger)db.get(i+j), (BigInteger)encValues.get(j)));
			}
			for (int z = 0; z < e; z++) {
				encDB[z].add(value.mod(d));
				value = value.divide(d);
			}
		}
		
		return encDB;
	}
	
	private static List totalFold(HomomorphicEncryption c, Object key, List encValuesList, List db, int depth, int e) {
		if (depth >= encValuesList.size())
			return db;
		
		List encValues = (List)encValuesList.get(depth);
		List encDB[] = fold(c, key, encValues, db, e);
		
		List result = totalFold(c, key, encValuesList, encDB[0], depth+1, e);
		for (int i = 1; i < e; i++) {
			result.addAll(totalFold(c, key, encValuesList, encDB[i], depth+1, e));
		}
		
		return result;
	}
	
	private static List totalFold(HomomorphicEncryption c, Object key, List encValuesList, List db) {
		return totalFold(c, key, encValuesList, db, 0, expansionFactor(c, key));
	}
	
	private static List unfold(HomomorphicEncryption c, Object key, List folded, int fold, int e) {
		if (fold <= 0)
			return folded;
		
		List result = new ArrayList(folded.size() / e);
		
		BigInteger d = c.encryptDomain(key);
		int foldSize = folded.size() / BigIntegerUtils.pow(e, fold-1);
		for (int i = 0; i < folded.size(); i += foldSize) {
			for (int j = 0; j < foldSize / e; j++) {
				BigInteger C = BigInteger.ZERO;
				for (int k = e-1; k >= 0; k--) {
					C = C.multiply(d).add((BigInteger)folded.get(i+j+k*(foldSize/e)));
				}
				result.add(c.decrypt(key, C));
			}
		}
		
		return result;
	}
	
	private static List totalUnfold(HomomorphicEncryption c, Object key, List folded, int folds) {
		int e = expansionFactor(c, key);
		List result = folded;
		for (int i = folds; i > 0; i--) {
			result = unfold(c, key, result, i, e);
		}
		
		return result;
	}
}
