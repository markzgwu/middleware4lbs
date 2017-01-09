package org.constants;

public final class Constants {
	final static Constants inst = new Constants();
	public final static String Classpath = getInst().getClassPath();
 	public static Constants getInst(){
		return inst;
	}
	
	public final String getClassPath(){
		final String classpath = this.getClass()
				.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.getPath();
		return classpath;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
