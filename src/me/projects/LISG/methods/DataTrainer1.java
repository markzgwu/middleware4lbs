package me.projects.LISG.methods;

import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.Frequency;

public final class DataTrainer1 extends AbsDataTrainer{

	int rowDimension;
	int columnDimension;
	
	public DataTrainer1(){
		init();
		initMatrixSample();
		initFreq();
	}
	
	void init(){
		final String[] strSemanticLabels={"Hospital","Offices","Parks"};
		SemanticLabels.addAll(Arrays.asList(strSemanticLabels));
		final String[] strLocationTags={"1","2","3","4"};		
		LocationTags.addAll(Arrays.asList(strLocationTags));
		rowDimension=LocationTags.size();
		columnDimension=SemanticLabels.size();
	}
	
	Frequency freq;
	void initFreq(){
		freq = new Frequency();
		freq.incrementValue("1", 3);
		freq.incrementValue("2", 3);
		freq.incrementValue("3", 2);
		freq.incrementValue("4", 2);
	}
	
	RealMatrix matrix1,matrix2;

	void initMatrixSample(){
		
		matrix1 = new Array2DRowRealMatrix(rowDimension,columnDimension);
		matrix2 = new Array2DRowRealMatrix(1,columnDimension);
		
		double[] col0 = {1,0,0,0};
		matrix1.setColumn(0,col0);
		double[] col1 = {0,0.5,0.5,0};
		matrix1.setColumn(1,col1);
		double[] col2 = {0,0,0,1};
		matrix1.setColumn(2,col2);
		
		double[] M2_row = {0.5,0.5,0};
		matrix2.setRow(0, M2_row);
		
	}
	
	//P(O loc = L 1 |S = x)
	final double BN_Pr_Oloc_S(String LocationTag,String SemanticLabel){
		int i = LocationTags.indexOf(LocationTag);
		int j = SemanticLabels.indexOf(SemanticLabel);
		double BN_Pr_Oloc_S = matrix1.getEntry(i, j);
		return BN_Pr_Oloc_S;
	}
	//P(S = x|A = 1)
	final double BN_Pr_S_A(String SemanticLabel){
		int j = SemanticLabels.indexOf(SemanticLabel);
		double BN_Pr_S_A = matrix2.getEntry(0, j);
		return BN_Pr_S_A;
	}
	
	public final double PriorBelief_Pr_A1(){
		return 0.4;
	}

	public final double PriorBelief_Pr_Oloc(String LocationTag){
		return freq.getPct(LocationTag);
	}
	
	public final double PriorBelief_Pr_Oloc_A1(String LocationTag){
		int i = LocationTags.indexOf(LocationTag);
		//System.out.println(LocationTag+" at "+i);
		double Pr_Oloc_A = 0;
		double[] row1 = matrix1.getRow(i);
		double[] row2 = matrix2.getRow(0);
		for(int k=0;k<row1.length;k++){
			double Pr_Oloc_A_per_S = row1[k]*row2[k];
			Pr_Oloc_A+=Pr_Oloc_A_per_S;
		}
		return Pr_Oloc_A;
	}
	protected final double PosteriorBelief_Pr_A1_Oloc(String locTag){
		double Pr_Oloc_A1 = PriorBelief_Pr_Oloc_A1(locTag);
		double Pr_A1 = PriorBelief_Pr_A1();
		double Pr_Oloc = PriorBelief_Pr_Oloc(locTag);
		double Pr_A_Oloc = (Pr_Oloc_A1 * Pr_A1) / Pr_Oloc;
		System.out.println("Pr_A_Oloc="+Pr_Oloc_A1+"*"+Pr_A1+"/"+Pr_Oloc);
		System.out.println("PosteriorBelief:P(A=1|Oloc="+locTag+")="+Pr_A_Oloc+"\n");
		return Pr_A_Oloc;
	}

	@Override
	protected
	double PriorBelief_Pr_Oloc_A(String LocationTag, boolean A) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected
	double PriorBelief_Pr_A(boolean A) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected
	double PriorBelief_Pr_Oloc_A(String[] LocationTags, boolean A) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected
	double PosteriorBelief_Pr_A1_Oloc(String[] LocationTags) {
		// TODO Auto-generated method stub
		return 0;
	}
}
