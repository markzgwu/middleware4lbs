package org.projects.privacymodel;

public class PrivacyProfile {
	public final int k;
	public final int A;
	public final double risk;
	public final double prediction;
	
	public PrivacyProfile(int anonymity_k, int diversity_l) {
		super();
		this.k = anonymity_k;
		this.A = diversity_l;
		this.risk = 0;
		this.prediction = 0;
	}	
	
	public PrivacyProfile(int anonymity_k, int diversity_l, double risk) {
		super();
		this.k = anonymity_k;
		this.A = diversity_l;
		this.risk = risk;
		this.prediction = 0;
	}	
	
	public PrivacyProfile(int anonymity_k, int diversity_l, double risk,double prediction) {
		super();
		this.k = anonymity_k;
		this.A = diversity_l;
		this.risk = risk;
		this.prediction = prediction;
	}
	
}
