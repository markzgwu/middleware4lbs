package org.assistants.api2;
import java.io.*;
import java.net.*;

import org.assistants.dbsp.baidumaps.Task;
 
public class Client4LBS {
	
	public String operation(int page_num){
		Task cur_url = new Task();

		String output = null;
		try {
			output = readContentFromGet(cur_url.toURL()+page_num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
    String readContentFromGet(String get_url) throws IOException{

    	System.out.println(get_url);
    	
        URL getUrl = new URL(get_url);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//���ñ���,������������
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
