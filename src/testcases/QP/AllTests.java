package testcases.QP;

import java.util.HashMap;

import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;
import me.projects.QP.methods.prediction.CRPrediction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {
	@Test
	public void test1(){
		final Parameters para = new Parameters();
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final CRPrediction CRChecker = new CRPrediction(datasampler);	
		
		HashMap<String,String> alltails=CRChecker.alltails;
        HashMap<String,String[]> allpaths=CRChecker.allpaths;
		
		assertEquals(allpaths.size(),alltails.size());
		for(String objectid:allpaths.keySet()){
			assertEquals(allpaths.get(objectid),alltails.get(objectid));
		}
	}
}
