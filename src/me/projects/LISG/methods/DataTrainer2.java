package me.projects.LISG.methods;

import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.Frequency;

public final class DataTrainer2 extends AbsDataTrainer{

	int level;
	int rowDimension;
	int columnDimension;
	
	public DataTrainer2(int level){
		this.level = level;
		initFreq();
		initModel();
		initMatrixSample();
	}
	

	
	void initModel(){
		final String[] strSemanticLabels={"Hospital","Offices","Parks"};
		SemanticLabels.addAll(Arrays.asList(strSemanticLabels));
		//final String[] strLocationTags={"1","2","3","4"};
		//LocationTags.addAll(Arrays.asList(strLocationTags));
		LocationTags.addAll(extend(level));
		
		rowDimension=LocationTags.size();
		columnDimension=SemanticLabels.size();
		System.out.println(rowDimension+"*"+columnDimension);
	}
	
	Frequency freq;
	void initFreq(){
		final DataSampler ds = ParameterTable.INSTANCE.getDatasampler();
		freq = ds.getFreq();
		System.out.println(freq);

	}
	
	RealMatrix matrix1,matrix2;
	void initMatrixSample(){
		matrix1 = new Array2DRowRealMatrix(rowDimension,columnDimension);
		matrix2 = new Array2DRowRealMatrix(1,columnDimension);
		
		for(int i=0;i<rowDimension;i++){
			double[] rowM1 = bulidRow4M1_fixed(i);
			matrix1.setRow(i,rowM1);
		}
		
		for(int j=0;j<columnDimension;j++){
			int sum = 0;
			for(int i=0;i<rowDimension;i++){
				double e = matrix1.getEntry(i, j);
				sum+=e;
			}
			for(int i=0;i<rowDimension;i++){
				double e = matrix1.getEntry(i, j);
				e = e / sum;
				if(e>0){
					matrix1.setEntry(i, j, e);
				}
			}

		}		
		
		double[] M2_row = {0.1,0.8,0.1};
		matrix2.setRow(0, M2_row);
		
		System.out.println(matrix1);
		System.out.println(matrix2);
		
	}
	
	double[] bulidRow4M1_fixed(int i){
		double[] rowM1 = new double[]{0,0,0};
		int mynumber = 0;
		if(i<1){
			mynumber = 1;
		}else if(i == this.rowDimension-1){
			mynumber = 3;
		}else{
			mynumber = 2;
		}
		switch (mynumber){
		case 1:
			rowM1 = new double[]{1,0,0};
			break;
		case 2:
			rowM1 = new double[]{0,1,0};
			break;
		case 3:
			rowM1 = new double[]{0,0,1};
			break;
		}
		return rowM1;
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
	
	public double Constant_Pr_A = 0.01;
	protected final double PriorBelief_Pr_A(boolean A){
		double Pr_A = 0;
		if(A){
			Pr_A = Constant_Pr_A;
		}else{
			Pr_A = 1-Constant_Pr_A;
		}
		return Pr_A;
	}
	
	final double PriorBelief_Pr_Oloc(String LocationTag){
		double Pr_Oloc_A1 = (PriorBelief_Pr_Oloc_A(LocationTag,true)*PriorBelief_Pr_A(true));
		double Pr_Oloc_A0 = (PriorBelief_Pr_Oloc_A(LocationTag,false)*PriorBelief_Pr_A(false));
		double PriorBelief_Pr_Oloc = Pr_Oloc_A1+Pr_Oloc_A0;
		return PriorBelief_Pr_Oloc;
	}
	
	protected final double PriorBelief_Pr_Oloc_A(String LocationTag,boolean A){
		double PriorBelief_Pr_Oloc = 0;
		if(A){
			PriorBelief_Pr_Oloc = PriorBelief_Pr_Oloc_A1(LocationTag);
		}else{
			PriorBelief_Pr_Oloc = PriorBelief_Pr_Oloc_A0(LocationTag);
		}
		return PriorBelief_Pr_Oloc;
	}	
	
	final double PriorBelief_Pr_Oloc_A1(String LocationTag){
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
	
	final double PriorBelief_Pr_Oloc_A0(String LocationTag){
		return freq.getPct(LocationTag);
	}
	
	protected final double PosteriorBelief_Pr_A1_Oloc(String locTag){
		double Pr_Oloc_A1 = PriorBelief_Pr_Oloc_A(locTag,true);
		double Pr_A1 = PriorBelief_Pr_A(true);
		double Pr_Oloc = PriorBelief_Pr_Oloc(locTag);
		double Pr_A_Oloc = (Pr_Oloc_A1 * Pr_A1) / Pr_Oloc;
		//System.out.println("Pr_A_Oloc="+Pr_Oloc_A1+"*"+Pr_A1+"/"+Pr_Oloc);
		//System.out.println("PosteriorBelief:P(A=1|Oloc="+locTag+")="+Pr_A_Oloc+"\n");
		return Pr_A_Oloc;
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
