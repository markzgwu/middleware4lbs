package measure.AuthKBA.test;

public final class tools {

	/** 
	* �ַ���Base64����󷵻� 
	* @param str 
	* @return 
	*/ 
	public static String strToBase64(String str){ 
	byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(str.getBytes()); 
	return new String(bytes); 
	} 

	/** 
	* Base64������ַ���ת��Ϊ�ַ������� 
	* @param str 
	* @return 
	*/ 
	public static String base64ToString(String str){ 
	byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes()); 
	return new String(bytes); 
	}	

}
