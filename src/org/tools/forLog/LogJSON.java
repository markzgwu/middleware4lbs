package org.tools.forLog;

import com.alibaba.fastjson.JSON;

public final class LogJSON {
	public static String json(Object object){
		return (JSON.toJSONString(object));
	}
}
