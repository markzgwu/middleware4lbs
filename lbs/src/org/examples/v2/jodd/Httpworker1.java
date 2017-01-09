package org.examples.v2.jodd;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class Httpworker1 {
	public static void worker(){
		HttpRequest httpRequest = HttpRequest.get("http://www.baidu.com");
	    HttpResponse response = httpRequest.send();

	    System.out.println(response);
	    
	}
	
	public static void main(String[] args) {
		worker();
	}	
}
