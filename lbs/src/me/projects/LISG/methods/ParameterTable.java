package me.projects.LISG.methods;

//Singleton
public enum ParameterTable {
	INSTANCE;
	ParameterTable(){

	}
	
	private Parameters parameters = null;

	public final Parameters getParameters() {
		return parameters;
	}

	public final void init(Parameters para){
		parameters = para;
		datasampler = new DataSampler(para);
	}
	
	private DataSampler datasampler = null;
	public final DataSampler getDatasampler() {
		return datasampler;
	}
	
}


