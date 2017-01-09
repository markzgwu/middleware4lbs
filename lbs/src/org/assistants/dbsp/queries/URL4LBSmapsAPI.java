package org.assistants.dbsp.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.forFile.FileTool;
import org.zgwu4lab.lbs.datamodel.geodata.Granularity;
import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class URL4LBSmapsAPI{
	final static Logger logger = LoggerFactory.getLogger(URL4LBSmapsAPI.class);
    
	final String myak = "C43da03d1baa7160e20bdee8bf9d403d";
	public String SP_URL = "http://api.map.baidu.com/place/v2/search?";
	String bounds = "bounds=39.892835,116.622647,39.912835,116.642647";
	public String region = "region=北京";
	public String query_keywords = "q=大学";
	public String page_size = "page_size=20";
	public String getURL(){
		String URL = SP_URL+"&output=json&ak="+myak;
		URL+="&"+bounds;
		URL+="&"+region;
		URL+="&"+query_keywords;
		URL+="&"+page_size;
		URL+="&page_num=";
		return URL;
	}
	
	public String operation(int page_num){
		String output = null;
		try {
			output = readContentFromGet(getURL()+page_num);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
    String readContentFromGet(String get_url) throws IOException{
    	logger.debug(get_url);
        URL getUrl = new URL(get_url);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
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
    
    private int ObtainTotal(JSONObject jsonobject){
		String total_str = jsonobject.getString("total");
		if(total_str==null){
			total_str="0";
		}
		int total = Integer.parseInt(total_str);
		logger.debug("TOTAL:"+total);
		return total;
    }
    
    private int ObtainResults(JSONObject jsonobject){
    	int ObtainResults = 0;
		JSONArray jsonarray = jsonobject.getJSONArray("results");
		//logger.debug(jsonarray.toJSONString());

			ObtainResults = jsonarray.size();
			for(int j = 0; j<ObtainResults ; j++  ){
				JSONObject tempJSONObj = jsonarray.getJSONObject(j);
				String uid = tempJSONObj.getString("uid");
				if(uid == null){
					logger.warn("NULL UID:"+tempJSONObj.toJSONString());
				}else{
					String content = tempJSONObj.toJSONString();
					try {
						FileTool.appendFile("D:\\POIdatabase.json",content+"\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.debug(content);
				}
			}			
		return ObtainResults;
    }
    
    public int worker(URL4LBSmapsAPI client){
		//String message = "runing...";
		int results_size_all = 0;
		int ObtainResults = 0;
		int page_num = 0;
		String jsoncontent = client.operation(page_num);
		JSONObject jsonobject = JSON.parseObject(jsoncontent);
		int total = ObtainTotal(jsonobject);
		boolean b = (total>760);
		if(b){
			System.out.println("Too many POIs! NUM of Results on SP (Total):"+total);
		}else{
			do{
				jsoncontent = client.operation(page_num);
				jsonobject = JSON.parseObject(jsoncontent);
				ObtainResults = ObtainResults(jsonobject);
				logger.debug("total="+total+";b="+b+";ObtainResults:"+ObtainResults);
				results_size_all+=ObtainResults;
				page_num++;
				
			}while(ObtainResults>1);
		}
		
		logger.debug("Worker Obtain The number of Results:"+results_size_all);
		jsonobject.clear();
		jsonobject=null;
		
		return results_size_all;
    }
    
	public static void main(String[] args) {
		LocationCoordinate left_bottom = new LocationCoordinate(0,0);
		left_bottom.lng_x=116.042647;
		left_bottom.lat_y=39.652835;
		LocationCoordinate right_top = new LocationCoordinate(0,0);
		right_top.lng_x=116.911919;
		right_top.lat_y=40.425292;
		
		Granularity g = new Granularity();
		logger.info(g.AREA()+" km2");
		RectBounds rect = new RectBounds(left_bottom,right_top,g);
		
		logger.info("left_bottom="+left_bottom.toLocation_yx()+";right_top="+right_top.toLocation_yx()+";"+rect.AREA()+" km2");
		ArrayList<String> rect_list = MapsTool.SplitRectBounds(rect,g);
		logger.info("NUM of GRIDs:"+rect_list.size());		
		
		//String[] keywords = {"学校","饭店","银行"};
		String[] keywords = {"银行"};
		URL4LBSmapsAPI client = new URL4LBSmapsAPI();
		int grid_num = 0;
		for(String rect_str:rect_list){
			client.bounds="bounds="+rect_str;
			int NumResults_Per_Request = 0;
			for(String keyword:keywords){
				client.query_keywords="q="+keyword;
				int NumResults_Per_Keyword = client.worker(client);
				NumResults_Per_Request+=NumResults_Per_Keyword;
			}
			System.out.println("RECT:"+grid_num+";"+rect_str+";"+client.query_keywords+";NUM of Results:"+NumResults_Per_Request);
			grid_num++;
			//if(grid_num>100)break;
		}


	}	
}
