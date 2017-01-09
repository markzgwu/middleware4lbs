package measure.AuthKBA.experiments;

public interface ParametersDb {
	//String DBname =  "movieslens1";
	boolean IID_assumption = true;//true IID;false rational.
	
	String DBname =  "movieslens_slim";
	// TODO Auto-generated method stub
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/"+DBname;
	String user = "root";
	String password = "12345678";
	
	double success_request = 1;
	double total_request= 2;
	//double parameter_pr_prior_true=success_request/total_request;
	
	double parameter_reliability_evdience=1;
	double parameter_reliability_evdience_low=0.9;
	
	
}
