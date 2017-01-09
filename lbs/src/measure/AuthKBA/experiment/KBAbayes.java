package measure.AuthKBA.experiment;

public final class KBAbayes{
	int threshole = 1;
	//final int[][] arg_ratings={{0,-1,1,0},{1,1,-1,0},{0,1,0,-1},{1,1,1,1},{-1,1,-1,0},{1,0,1,0}};
	//final int[] arg_claimeduser0={0,-1,1,0};//Row 0 in ratings  is for the claimed user.

	int[][] arg_ratings={{1,0},{0,1},{-1,1}};
	int[] arg_claimeduser0={1,0};//Row 0 in ratings  is for the claimed user.	
	
	double success_request = 1;
	double total_request= 10;
	double arg_pr_prior_true=success_request/total_request;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new KBAbayes().worker();
	}

	public void show(int[] array){
		String s="";
		for(int i:array){
			s+=i+";";
		}
		System.out.println(s);
	}
	
	public void worker1(){
		pr_posterior_true(arg_claimeduser0,arg_ratings,arg_pr_prior_true);	
	}
	
	public void worker(){
		int[][] arg_claimeduser_list = arg_ratings;
		for(int i=0;i<arg_claimeduser_list.length;i++){
			int[] arg_claimeduser0 = arg_claimeduser_list[i];
			show(arg_claimeduser0);
			pr_posterior_true(arg_claimeduser0,arg_ratings,arg_pr_prior_true);	
		}
	}
		
	public boolean pr_posterior_true(int[] evidence,int[][] sampleratings,double pr_prior_true){
		return pr_posterior(evidence,true,arg_ratings,arg_pr_prior_true);
	}
	
	public boolean pr_posterior(int[] evidence,boolean b,int[][] sampleratings,double pr_prior_true){
		double pr_prior_flase=1-pr_prior_true;
		
		double pr_prior_evdience_true = pr_prior(evidence,true,sampleratings);
		double pr_prior_evdience_flase = pr_prior(evidence,false,sampleratings);
		
		double pr_joint_true=pr_prior_evdience_true*pr_prior_true;
		double pr_joint_false=pr_prior_evdience_flase*pr_prior_flase;
		
		double pr_evdience= pr_joint_true+pr_joint_false;
		
		double pr_posterior;
		if(b){
			pr_posterior=pr_joint_true/pr_evdience;
		}else{
			pr_posterior=pr_joint_false/pr_evdience;
		}
		
		//System.out.println(pr_prior_evdience_true);
		//System.out.println(pr_prior_evdience_flase);
		double pr1 = pr_posterior;
		System.out.println("AUTH:pr("+b+"|e)="+pr1);
		double pr2 = 1-pr_posterior;
		System.out.println("AUTH:pr("+!b+"|e)="+pr2);
		double pr1_pr2=pr1/pr2;
		//System.out.println("AUTH:pr1/pr2="+pr1_pr2);
		double ln_pr1_pr2=Math.log(pr1_pr2);
		System.out.println("AUTH:pr1/pr2="+ln_pr1_pr2);
		
		
		return (pr1_pr2>threshole);
	}
	
	public double pr_prior(int[] evidence,boolean b,int[][] sampleratings){
		double pr_prior = 1;
		for(int j=0;j<evidence.length;j++ ){
			int comparednumber = evidence[j];
			double pr_prior1 = pr_prior_comparednumber(j,comparednumber,b,sampleratings);
			//System.out.println(pr1);
			pr_prior = pr_prior*pr_prior1;
		}
		return pr_prior;
	}
	
	public double pr_prior_comparednumber(int j,int comparednumber,boolean b,int[][] sampleratings){
		int startindex = 1;
		if(b){
			startindex=0;
		}
		return pr_prior_comparednumber_startindex(j,comparednumber,startindex,sampleratings);
	}
	
	public double pr_prior_comparednumber_startindex(int j,int comparednumber,int startindex,int[][] sampleratings){
		int total_num_users = sampleratings.length-startindex;//����������һ��Ԫ��ʱ����Ҫ��ȥ��Ԫ�ء�
		double pr_prior=1;
		double count=count_comparednumber_startindex(j,comparednumber,startindex,sampleratings);
		pr_prior=count/total_num_users;
		System.out.println(count+"/"+total_num_users);
		return pr_prior;
	}
	
	public int count_comparednumber_startindex(int j,int comparednumber,int startindex,int[][] sampleratings){
		int total_num_users = sampleratings.length;
		int count=0;
		for(int i=startindex;i<total_num_users;i++){
			if(sampleratings[i][j]==comparednumber){
				count++;
			}
		}
		return count;
	}	

}
