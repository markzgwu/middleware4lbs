package measure.AuthKBA.experiment;

public final class KBAnaivebayes{
	final static public boolean log = false;
	
	double likenessrate=0;
	
	StringBuffer message = new StringBuffer();
	
	int[][] arg_ratings={{0,2,1,0},{1,1,2,0},{0,1,0,2},{1,1,1,1},{2,1,2,0},{1,0,1,0},{1,2,1,0},{1,1,1,0},{0,1,0,0},{0,0,2,0}};
	int[][] arg_claimedusers={{0,2,1,0},{1,1,2,0},{0,1,0,2},{1,1,1,1},{2,1,2,0},{1,0,1,0},{1,2,1,0},{1,1,1,0},{0,1,0,0},{0,0,2,0}};
	int[] arg_claimeduseraverage={0,0,0,0};//Row 0 in ratings  is for the claimed user.

	//int[][] arg_ratings={{1,0,0},{0,1},{-1,1},{0,0}};
	//int[][] arg_claimedusers={{1,0},{0,1},{1,1},{0,0}};
	//int[] arg_claimeduser={-1,1};//Row 0 in ratings  is for the claimed user.	
	
	double success_request = 1;
	double total_request= 20;
	double arg_pr_prior_true=success_request/total_request;
	
	double correctness_rate_memoryability=0.9;

	public KBAnaivebayes(int[][] arg_ratings1,int[] arg_claimeduseraverage1){
		arg_ratings=arg_ratings1;
		arg_claimedusers = arg_ratings1;
		arg_claimeduseraverage = arg_claimeduseraverage1;
		//worker2();
	}
	
	public double getlikenessrate(){
		return likenessrate;
	}
	
	public KBAnaivebayes(){
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int climant = 1;
		//DoExtractDataSlim task = new DoExtractDataSlim(1);
		//task.OperateData();
		new KBAnaivebayes().worker2();
		//new KBAnaivebayes().worker1();
	}

	public String show(int[] array){
		String s="";
		for(int i:array){
			s+=i+",";
		}
		return s;
	}
	
	public void worker1(){
		//arg_claimeduseraverage = getaverage(arg_ratings);
		pr_posterior_true(arg_claimeduseraverage,arg_ratings,arg_pr_prior_true,0);	
	}
	
	public void worker(){
		int[][] arg_claimeduser_list = arg_claimedusers;
		double accepted = 0;
		for(int i=0;i<arg_claimeduser_list.length;i++){
			int[] arg_claimeduser = arg_claimeduser_list[i];
			show(arg_claimeduser);
			pr_posterior_true(arg_claimeduser,arg_ratings,arg_pr_prior_true,i);	
			boolean b = likenessrate>1;
			if(b){
				accepted++;
			}
		}
		
		System.out.println("Accept Rate:"+(accepted/arg_claimedusers.length));
	}
	
	public void worker2(){
		int[][] arg_claimeduser_list = arg_claimedusers;
		
		double falserejection = 0;
		double totalFAR=0;
		
		for(int i=0;i<arg_claimeduser_list.length;i++){
			//int[] arg_claimeduser = arg_claimeduser_list[i];
			//show(arg_claimeduser);
			double falseaccepted = 0;
			for(int t=0;t<arg_claimeduser_list.length;t++){
				
				int[] arg_claimeduser = arg_claimeduser_list[t];
				show(arg_claimeduser);				
				
				pr_posterior_true(arg_claimeduser,arg_ratings,arg_pr_prior_true,i);
				
				if((i==t)&&(likenessrate<1)){
					falserejection++;
				}
				
				if ((i!=t)&&(likenessrate>1) && (i!=t)){
					falseaccepted++;
				}
			}
			double i_FAR = (falseaccepted/arg_claimedusers.length);
			if(log){System.out.println("User:"+i+";FAR:"+i_FAR);}
			totalFAR+=i_FAR;
		}
		System.out.println("FAR:"+(totalFAR/arg_claimedusers.length));
		System.out.println("FRR:"+(falserejection/arg_claimedusers.length));
		
	}	
	
	public int[] getaverage(int[][] arg_ratings){
		int m=arg_ratings.length;
		int n=arg_ratings[0].length;
		int[] arg_rating_avage_col=new int[n];

		for(int j=0;j<n;j++){
			int arg_rating_col_sum = 0;
			int count=0;
			for(int i=0;i<m;i++){
				if(arg_ratings[i][j]>0){
					count++;
					arg_rating_col_sum+=arg_ratings[i][j];
				};
			}
			arg_rating_avage_col[j]=arg_rating_col_sum/count;
		}
		return arg_rating_avage_col;
	}	
		
	public double pr_posterior_true(int[] evidence,int[][] sampleratings,double pr_prior_true,final int evidence_for_clamint){
		StringBuffer logging = new StringBuffer();
		logging.append("("+show(evidence)+")   ");
		
		double pr_prior_flase=1-pr_prior_true;
		//logging.append(pr_prior_true+";"+pr_prior_flase+";");
		
		double pr_prior_evdience_true = pr_prior_true(evidence,sampleratings,evidence_for_clamint);
		double pr_prior_evdience_flase = pr_prior_false(evidence,sampleratings);
		
		//logging.append(pr_prior_evdience_true+";"+pr_prior_evdience_flase+";");
		
		double pr_joint_true=pr_prior_evdience_true*pr_prior_true;
		double pr_joint_false=pr_prior_evdience_flase*pr_prior_flase;
		
		//logging.append(pr_joint_true+";"+pr_joint_false+";");
		
		double pr_evdience= pr_joint_true+pr_joint_false;
		double pr_posterior=pr_joint_true/pr_evdience;
		//logging.append(pr_evdience+";"+pr_posterior+";");
		
		//System.out.println(pr_prior_evdience_true);
		//System.out.println(pr_prior_evdience_flase);
		double pr1 = pr_posterior;
		double pr2 = 1-pr_posterior;
		//double pr2 = pr_joint_false/pr_evdience;
		logging.append(pr1+";"+pr2);
		
		double pr1_pr2=pr1/pr2;
		//double pr1_pr2=pr_joint_true/pr_joint_false;
		
		likenessrate = pr1_pr2;
		
		logging.append(";"+pr1_pr2);
		//double ln_pr1_pr2=Math.log(pr1_pr2);
		//System.out.println("AUTH:log(pr1/pr2)="+ln_pr1_pr2);
		logging.append("\n");
		if(log){System.out.print(logging);}
		return pr_posterior;
	}
	
	public double pr_prior_true(int[] evidence,int[][] sampleratings,final int evidence_for_clamint){
		double pr_prior = 1;
		for(int j=0;j<evidence.length;j++ ){
			int comparednumber = evidence[j];
			double pr_prior1 = 0;
			if(comparednumber == sampleratings[evidence_for_clamint][j]){
				pr_prior1 = correctness_rate_memoryability;
			}else{
				pr_prior1 = 1-correctness_rate_memoryability;
			}
			//System.out.println(pr1);
			pr_prior = pr_prior*pr_prior1;
		}
		return pr_prior;
	}	
	
	public double pr_prior_false(int[] evidence,int[][] sampleratings){
		double pr_prior = 1;
		for(int j=0;j<evidence.length;j++ ){
			int comparednumber = evidence[j];
			double pr_prior1 = pr_prior_comparednumber(j,comparednumber,sampleratings);
			//System.out.println(pr1);
			pr_prior = pr_prior*pr_prior1;
		}
		return pr_prior;
	}
	
	public double pr_prior_comparednumber(int j,int comparednumber,int[][] sampleratings){
		int total_num_users = sampleratings.length;//若不包括第一个元素时，需要减去首元素。
		double pr_prior=1;
		double count=count_comparednumber(j,comparednumber,sampleratings);
		pr_prior=count/total_num_users;
		//System.out.println(count+"/"+total_num_users);
		return pr_prior;
	}
	
	public int count_comparednumber(int j,int comparednumber,int[][] sampleratings){
		int total_num_users = sampleratings.length;
		int count=0;
		for(int i=0;i<total_num_users;i++){
			if(sampleratings[i][j]==comparednumber){
				count++;
			}
		}
		return count;
	}	

}
