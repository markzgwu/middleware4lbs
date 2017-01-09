package com.pir.crypto.pir.test;

import java.math.BigInteger;
import java.util.*;
//import math.algebra.*;
import com.pir.math.*;
import com.pir.crypto.encrypt.*;
import com.pir.crypto.pir.*;
import java.rmi.*;

public class TestPIRServer {
	public static void main(String args[]) throws Exception {
		Random rnd = new Random();
		
		int size = Integer.parseInt(args[0]);
		int width = Integer.parseInt(args[1]);
		
		List db = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			db.add(new BigInteger(width, rnd));
		}
		
		PIR pir = new KOPIR();
		PIRServer server = new PIRServerImpl(pir, db, width);
		if (System.getSecurityManager() == null) {
		    System.setSecurityManager(new RMISecurityManager());
		}
		Naming.rebind("PIRServer", server);
	}
}
