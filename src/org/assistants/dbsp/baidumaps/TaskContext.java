package org.assistants.dbsp.baidumaps;

public final class TaskContext {
	//lbYX left_bottom lat_Y lnt_X
	//rtYX right_top lat_Y lnt_X
	public String bounds_lbYX_rtYX = "";
	public String query_keywords = "";
	public TaskContext(String bounds_lbYX_rtYX,String keyword){
		this.bounds_lbYX_rtYX = bounds_lbYX_rtYX;
		this.query_keywords = keyword;
	}
}
