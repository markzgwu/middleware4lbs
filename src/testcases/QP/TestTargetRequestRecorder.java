package testcases.QP;

import static org.junit.Assert.*;

import java.util.ArrayList;

import me.projects.QP.methods.prediction.CRHistory;
import me.projects.QP.methods.prediction.TargetReguestRecorder;
import me.projects.toolkit.ToolkitRecorder;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
public class TestTargetRequestRecorder {
	@Test
	public void test1(){
		System.out.println("TEST1");
		ArrayList<CRHistory> CRL = new ArrayList<CRHistory>();
		CRL.add(new CRHistory("1", null, new String[]{"1"}, null));
		CRL.add(new CRHistory("2", null, new String[]{"2"}, null));
		CRL.add(new CRHistory("3", null, new String[]{"3"}, null));
		CRL.add(new CRHistory("a", null, new String[]{"a1"}, null));
		CRL.add(new CRHistory("b", null, new String[]{"b2"}, null));
		CRL.add(new CRHistory("c", null, new String[]{"c3"}, null));
		System.out.println(JSON.toJSONString(ToolkitRecorder.getRecent(CRL, 2)));
	}
	
	@Test
	public void test2(){
		System.out.println("TEST2");
		ArrayList<CRHistory> CRL = new ArrayList<CRHistory>();
		CRL.add(new CRHistory("1", null, new String[]{"1"}, null));
		CRL.add(new CRHistory("2", null, new String[]{"2"}, null));
		System.out.println(JSON.toJSONString(ToolkitRecorder.getRecent(CRL, 2)));
	}
	
	@Test
	public void test3(){
		System.out.println("TEST3");
		ArrayList<CRHistory> CRL = new ArrayList<CRHistory>();
		CRL.add(new CRHistory("1", null, new String[]{"1"}, null));
		System.out.println(JSON.toJSONString(ToolkitRecorder.getRecent(CRL, 2)));
		System.out.println(JSON.toJSONString(ToolkitRecorder.getRecent(CRL, 1)));
	}	
}
