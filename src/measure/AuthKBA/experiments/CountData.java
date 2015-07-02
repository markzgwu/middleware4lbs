package measure.AuthKBA.experiments;

public abstract class CountData extends AbstractData {

	protected ParameterPackage MyParameterPackage;	
	
	public CountData(String[][] input_dataset, ParameterPackage mypp){
		knownledge_data = input_dataset;
		MyParameterPackage = mypp;
	}
	

}
