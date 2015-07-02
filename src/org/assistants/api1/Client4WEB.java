package org.assistants.api1;
import java.io.*;
import java.net.*;
 
public class Client4WEB {
	
	final static String myak = "03a1dce9afa0148fa1a82b3b05e89c8b";
	static final String GET_URL1 = "http://api.map.baidu.com/place/v2/search?&output=json&ak="+myak;
	//static final String GET_URL = GET_URL1 + "&q=海淀区学校&region=北京&page_size=20&page_num=";
	static final String GET_URL = GET_URL1 + "&q=银行&bounds=37.76623,116.43213,39.54321,117.46773&page_size=20&page_num=";
	
	public static void main(String[] args){
		String output = operation(0);
        System.out.println(output.length()+" bytes");
        System.out.println("="+"Contents of get request: start"+"=");
        System.out.println(output);
        System.out.println("="+"Contents of get request: end"+"=");		
	}
	
	public static String operation(int page_num){
		String output = null;
		try {
			output = readContentFromGet(GET_URL+page_num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

    public static String readContentFromGet(String get_url) throws IOException{

        String getURL = get_url;
        URL getUrl = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
        StringBuffer stringbuf = new StringBuffer();
        String lines;
        while ((lines = reader.readLine()) != null){
        	//lines = new String(lines.getBytes(), "utf-8");
        	stringbuf.append(lines+"\n");
        	//System.out.println(lines);
        }
        reader.close();
        connection.disconnect();
        
        return stringbuf.toString();
    }

}
