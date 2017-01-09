package org.examples.v2.jodd;

import jodd.http.*;

public final class Httpworker {

	public static void main(String[] args) {
		    HttpBrowser browser = new HttpBrowser();

		    HttpRequest request = HttpRequest.get("www.baidu.com");
		    browser.sendRequest(request);

		    // request is sent and response is received

		    // process the page:
		    String page = browser.getPage();

		    System.out.println(page);
		    
		    // create new request
		    //HttpRequest newRequest = HttpRequest.post(formAction);

		    //browser.sendRequest(newRequest);
	}

}
