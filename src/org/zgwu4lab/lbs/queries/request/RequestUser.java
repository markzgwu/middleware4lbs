package org.zgwu4lab.lbs.queries.request;

import com.alibaba.fastjson.JSON;

public final class RequestUser extends AbsRequestUser{
	public QueryArgument argument;
	public final static RequestUser fromJson(String json){
		RequestUser u = JSON.parseObject(json, RequestUser.class);
		return u;
	}
}
