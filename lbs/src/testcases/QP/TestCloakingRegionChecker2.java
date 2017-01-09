package testcases.QP;

import static org.junit.Assert.*;

import java.util.HashMap;

import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;
import me.projects.QP.methods.prediction.CRPrediction;

import org.junit.Test;

public class TestCloakingRegionChecker2 {

	public void test(){
		
	}
	
	@Test
	public void test0() {
		final Parameters para = new Parameters();
		ParameterTable.INSTANCE.init(para);
		final DataSampler datasampler = ParameterTable.INSTANCE.getDatasampler();
		final CRPrediction CRChecker = new CRPrediction(datasampler);
		int a = 2;//敌手获取的连续子路径长度
		HashMap<String,String> alltails=CRChecker.alltails;
        HashMap<String,String[]> allpaths=CRChecker.allpaths;
        assertEquals(allpaths.size(),alltails.size());
        
        String[] subpatherror = {"3103","3012"};
        String[] subpatherror1 = {"3012","3103"};
        String tailerror="3103";
        System.out.println(CRChecker.PosteriorBelief_Pr(tailerror, subpatherror));
        System.out.println(CRChecker.PosteriorBelief_Pr(tailerror, subpatherror1));
        
		
	}
}
