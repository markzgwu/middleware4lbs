package org.assistants.dbsp.queries;

public final class Mykey_baidu {
	static final String[] keys = {
			"F12321e49f1082ef261ffd3f0f1a16eb",
			"C43da03d1baa7160e20bdee8bf9d403d"
			};
	static int a = 0;
	public static String getAkey(){
		int n = a;
		a=(a+1)%keys.length;
		return keys[n];
	}
	
	public static void main(String[] args){
		for(int i=0;i<100;i++){
			System.out.println(getAkey());
		}
	}
}
