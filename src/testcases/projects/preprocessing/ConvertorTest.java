package testcases.projects.preprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.projects.preprocessing.Convertor;

import com.alibaba.fastjson.JSON;

public class ConvertorTest {

	@Test
	public void testExtendStringInt() {
		//System.out.println(JSON.toJSONString(Convertor.extendByOneChar("")));
		{
		ArrayList<String> result = Convertor.extend("0000",5);
		System.out.println(JSON.toJSONString(result));
		assertEquals(4,result.size());
		}
		
		{
		ArrayList<String> result = Convertor.extend("000",5);
		System.out.println(JSON.toJSONString(result));
		assertEquals((int)Math.pow(4, 2),result.size());
		}
		
		{
		ArrayList<String> result = Convertor.extend("00",5);
		System.out.println(JSON.toJSONString(result));
		assertEquals((int)Math.pow(4, 3),result.size());
		}
		
		{
		ArrayList<String> result = Convertor.extend("0",5);
		System.out.println(JSON.toJSONString(result));
		assertEquals((int)Math.pow(4, 4),result.size());
		}
		
		{
		ArrayList<String> result = Convertor.extend("",5);
		System.out.println(JSON.toJSONString(result));
		assertEquals((int)Math.pow(4, 5),result.size());
		}
		
		{
		ArrayList<String> nodeids = new ArrayList<String>();
		nodeids.add("0000");
		nodeids.add("0001");
		ArrayList<String> result = Convertor.extend(nodeids,5);
		System.out.println(JSON.toJSONString(result));
		assertEquals(2*(int)Math.pow(4, 1),result.size());
		}		

		{
		ArrayList<String> nodeids = new ArrayList<String>();
		nodeids.add("00000");
		nodeids.add("00010");
		ArrayList<String> result = Convertor.extend(nodeids,5);
		System.out.println(JSON.toJSONString(result));
		assertEquals(2*(int)Math.pow(4, 0),result.size());
		}		
		
	}

}
