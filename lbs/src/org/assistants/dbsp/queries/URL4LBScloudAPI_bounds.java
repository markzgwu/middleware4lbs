package org.assistants.dbsp.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.tools.forFile.FileTool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class URL4LBScloudAPI_bounds{
	final String myak = "C43da03d1baa7160e20bdee8bf9d403d";
	public String SP_URL = "http://api.map.baidu.com/geosearch/v2/local?";
	//String region_bounds = "bounds=116.302149,40.005302,116.324859,39.989825";
	//String region_bounds = "bounds=37.766230,116.432130,39.543210,117.467730";
	public String region_bounds = "region=上海市";
	public String query_keywords = "q=饭店";
	public String page_size = "page_size=50";
	public String geotable_id = "geotable_id=30828";
	public String getURL(){
		String URL = SP_URL+"&output=json&ak="+myak;
		URL+="&"+region_bounds;
		URL+="&"+geotable_id;
		URL+="&"+query_keywords;
		URL+="&"+page_size;
		URL+="&page_index=";
		return URL;
	}
	
	public String operation(int page_index){

		String output = null;
		try {
			output = readContentFromGet(getURL()+page_index);
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
    
	public static void main(String[] args) {
		URL4LBScloudAPI_bounds client = new URL4LBScloudAPI_bounds();
		int results_size_per_page = 20;
		int results_size_all = 0;
		int page_num = 0;
		while(results_size_per_page>1){
			String jsoncontent = client.operation(page_num);
			//String jsoncontent = Client4WEB.operation(page_num);
			page_num++;
			JSONObject jsonobject = JSON.parseObject(jsoncontent);

			String total = jsonobject.getString("total");
			System.out.println("TOTAL:"+total);
			
			JSONArray jsonarray = jsonobject.getJSONArray("results");
			
			//String jsonString = jsonarray.toJSONString();
			results_size_per_page = jsonarray.size();
			
			for(int j = 0; j<results_size_per_page ; j++  ){
				JSONObject tempJSONObj = jsonarray.getJSONObject(j);
				String uid = tempJSONObj.getString("uid");
				//String location = tempJSONObj.getString("location");
				//String address = tempJSONObj.getString("address");
				if(uid != null){
					results_size_all++;
					//System.out.println(uid+location+address);
					//System.out.println(uid+location+address);
					String content = tempJSONObj.toJSONString();
					try {
						FileTool.appendFile("D:\\POIdatabase.json",content+"\n");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(content);
				}
			}
			
			//results_size_all+=results_size_per_page;
			//System.out.println(jsonarray.size());
			//System.out.println(jsonString);				
		}
		System.out.println("RESULT SIZE:"+results_size_all);
	}	
}
