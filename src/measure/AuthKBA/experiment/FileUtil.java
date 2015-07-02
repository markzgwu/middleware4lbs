package measure.AuthKBA.experiment;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtil {
	String path1 = "D:\\_reserach\\datasets\\movielens\\ml-1m\\users.dat";
	int limit = 5;
	public void ReadFile(String path) throws Exception{

		  FileReader reader = new FileReader(path);
		  BufferedReader br = new BufferedReader(reader);
		  String row = null;

		  int count = 0;
		  while((row = br.readLine()) != null) {
			  count ++;
			  //PrepareData(row,limit);
			  //row.split(regex);
		  }
		  
		  System.out.println("the number of records:"+count);
		  br.close();
		  reader.close();
	}
}
