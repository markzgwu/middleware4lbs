package org.assistants.api2;

import org.tools.forFile.FileTool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONProcessor {
	public static void main(String[] args) {
		Client4LBS client4lbs = new Client4LBS();
		int results_size_per_page = 20;
		int results_size_all = 0;
		int page_num = 0;
		while(results_size_per_page>1){
			String jsoncontent = client4lbs.operation(page_num);
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
