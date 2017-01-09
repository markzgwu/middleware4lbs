package me.projects.LISG.methods;

import java.util.ArrayList;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.Frequency;
import org.parameters.I_constant;
import org.projects.preprocessing.Convertor;

public final class DataTrainer5 extends AbsDataTrainer{

	final DataSampler datasampler;
	int rowDimension;
	int columnDimension;
	
	public DataTrainer5(final DataSampler datasampler){
		this.datasampler = datasampler;
		initFreq();
		initModel();
		initMatrixSample();
	}
	
	ArrayList<String> extend(){
		int level = datasampler.level;
		ArrayList<String> result = Convertor.extend("", level);
		return result;
	}
	
	final MapsReaderInterface mapsreader = new MapsReaderV2();
	void initModel(){
		//final String[] strSemanticLabels={"Hospital","Offices","Parks"};
		//SemanticLabels.addAll(Arrays.asList(strSemanticLabels));
		SemanticLabels.addAll(mapsreader.getSemanticLabels());
		//final String[] strLocationTags={"1","2","3","4"};
		//LocationTags.addAll(Arrays.asList(strLocationTags));
		LocationTags.addAll(extend());
		
		rowDimension=LocationTags.size();
		columnDimension=SemanticLabels.size();
		System.out.println(rowDimension+"*"+columnDimension);
	}
	
	Frequency freq;
	void initFreq(){
		freq = datasampler.getFreq();
		//System.out.println(freq);

	}
	
	RealMatrix matrix1,matrix2;
	void initMatrixSample(){
		matrix1 = new Array2DRowRealMatrix(rowDimension,columnDimension);
		matrix2 = new Array2DRowRealMatrix(1,columnDimension);
		
		for(int i=0;i<rowDimension;i++){
			for(int j=0;j<columnDimension;j++){
				String cid = this.LocationTags.get(i);
				String catalog = this.SemanticLabels.get(j);
				double r = this.mapsreader.queryrisk(cid, catalog);
				matrix1.setEntry(i, j, r);
			}
		}
		
		for(int j=0;j<columnDimension;j++){
			String catalog = this.SemanticLabels.get(j);
			double r = this.mapsreader.CatalogRiskLevel(catalog);
			matrix2.setEntry(0, j, r);
		}
		if(I_constant.output_detail.equals("true")){
			System.out.println(matrix1);
			System.out.println(matrix2);
		}

		
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
		double a11 = PriorBelief_Pr_Oloc_A(LocationTag,true);
		double a12 = PriorBelief_Pr_A(true);
		double Pr_Oloc_A1 = a11*a12;
		double a01 = PriorBelief_Pr_Oloc_A(LocationTag,false);
		double a02 = PriorBelief_Pr_A(false);
		double Pr_Oloc_A0 = a01*a02;
		double PriorBelief_Pr_Oloc = Pr_Oloc_A1+Pr_Oloc_A0;
		/*
		if(PriorBelief_Pr_Oloc==0){
			PriorBelief_Pr_Oloc = 1;
		}
		*/
		//System.out.println(Constant_Pr_A);
		//System.out.println(Pr_Oloc_A1+"="+a11+"*"+a12);
		//System.out.println(Pr_Oloc_A0+"="+a01+"*"+a02);
		//System.out.println(PriorBelief_Pr_Oloc+"="+Pr_Oloc_A1+"+"+Pr_Oloc_A0);
		return PriorBelief_Pr_Oloc;
	}
	
	final double PriorBelief_Pr_Oloc(String[] LocationTags){
		double PriorBelief_Pr_Oloc = 0;
		for(String LocationTag:LocationTags){
			PriorBelief_Pr_Oloc+=PriorBelief_Pr_Oloc(LocationTag);
		}
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
	
	protected final double PriorBelief_Pr_Oloc_A(String[] LocationTags,boolean A){
		double PriorBelief_Pr_Oloc = 0;
		for(String LocationTag:LocationTags){
			PriorBelief_Pr_Oloc+=PriorBelief_Pr_Oloc_A(LocationTag,A);
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
	
	protected final double PosteriorBelief_Pr_A1_Oloc(String[] locTags){
		double Pr_Oloc_A1 = PriorBelief_Pr_Oloc_A(locTags,true);
		double Pr_A1 = PriorBelief_Pr_A(true);
		double Pr_Oloc = PriorBelief_Pr_Oloc(locTags);
		double Pr_A_Oloc = (Pr_Oloc_A1 * Pr_A1) / Pr_Oloc;
		//System.out.println("Pr_A_Oloc="+Pr_Oloc_A1+"*"+Pr_A1+"/"+Pr_Oloc);
		//System.out.println("PosteriorBelief:P(A=1|Oloc="+locTags+")="+Pr_A_Oloc+"\n");
		return Pr_A_Oloc;
	}

}
