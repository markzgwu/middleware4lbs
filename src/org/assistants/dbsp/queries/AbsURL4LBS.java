package org.assistants.dbsp.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbsURL4LBS {
	
	final String location;
	
	AbsURL4LBS(String loc){
		location = loc;
	}
	
	public abstract String operation();
	
    String readContentFromGet(String get_url) throws IOException{

    	//System.out.println(get_url);
    	
        URL getUrl = new URL(get_url);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
        final StringBuffer stringbuf = new StringBuffer();
        String lines;
        while ((lines = reader.readLine()) != null){
        	//lines = new String(lines.getBytes(), "utf-8");
        	stringbuf.append(lines+"\n");
        	//System.out.println(lines);
        }
        reader.close();
        connection.disconnect();
        reader = null;
        connection = null;
        getUrl = null;
        
        return stringbuf.toString();
    }	
}
