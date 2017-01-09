package me.projects.QP.test;

import java.util.ArrayList;
import java.util.List;

import org.parameters.I_constant;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscreteFactory;
import be.ac.ulg.montefiore.run.jahmm.draw.GenericHmmDrawerDot;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.toolbox.KullbackLeiblerDistanceCalculator;
import be.ac.ulg.montefiore.run.jahmm.toolbox.MarkovGenerator;

public final class Example2 {
	public enum Packet {
		Hospatial, Park, Mall; 
		
		public ObservationDiscrete<Packet> observation() {
			return new ObservationDiscrete<Packet>(this);
		}
	};
	public static void main(String[] args)
			throws java.io.IOException{
		
		/* Build a HMM and generate observation sequences using this HMM */
		
		Hmm<ObservationDiscrete<Packet>> hmm = buildHmm();
		
		List<List<ObservationDiscrete<Packet>>> sequences;
		sequences = generateSequences(hmm);
		
		Hmm<ObservationDiscrete<Packet>> learntHmm = buildInitHmm();
		
		/* Baum-Welch learning */
		
		BaumWelchLearner bwl = new BaumWelchLearner();
		
		// This object measures the distance between two HMMs
		KullbackLeiblerDistanceCalculator klc = 
			new KullbackLeiblerDistanceCalculator();
		
		// Incrementally improve the solution
		for (int i = 0; i < 10; i++) {
			System.out.println("Distance at iteration " + i + ": " +
					klc.distance(learntHmm, hmm));
			learntHmm = bwl.iterate(learntHmm, sequences);
		}
		
		System.out.println("Resulting HMM:\n" + learntHmm);
		
		/* Computing the probability of a sequence */
		
		ObservationDiscrete<Packet> packet0 = Packet.Hospatial.observation();
		ObservationDiscrete<Packet> packet1 = Packet.Park.observation();
		ObservationDiscrete<Packet> packet2 = Packet.Mall.observation();
		
		List<ObservationDiscrete<Packet>> testSequence = 
			new ArrayList<ObservationDiscrete<Packet>>(); 
		testSequence.add(packet0);
		testSequence.add(packet1);
		testSequence.add(packet2);
		
		System.out.println("Sequence probability: " +
				learntHmm.probability(testSequence));	
		
		// TODO Auto-generated method stub
		(new GenericHmmDrawerDot()).write(learntHmm, I_constant.output_graphviz+"example1_learntHmm.gv");
	}

	/* The HMM this example is based on */
	
	static Hmm<ObservationDiscrete<Packet>> buildHmm()
	{	
		Hmm<ObservationDiscrete<Packet>> hmm = 
			new Hmm<ObservationDiscrete<Packet>>(3,
					new OpdfDiscreteFactory<Packet>(Packet.class));
		
		hmm.setPi(0, 0.90);
		hmm.setPi(1, 0.05);
		hmm.setPi(2, 0.05);
		
		hmm.setOpdf(0, new OpdfDiscrete<Packet>(Packet.class, 
				new double[] { 0.90, 0.05 ,0.05}));
		hmm.setOpdf(1, new OpdfDiscrete<Packet>(Packet.class,
				new double[] { 0.20, 0.70 ,0.10}));
		
		
		hmm.setAij(0, 0, 0.95);
		hmm.setAij(0, 1, 0.05);
		hmm.setAij(0, 2, 0.05);
		
		hmm.setAij(1, 0, 0.10);
		hmm.setAij(1, 1, 0.20);
		hmm.setAij(1, 2, 0.70);
		
		hmm.setAij(2, 0, 0.10);
		hmm.setAij(2, 1, 0.10);
		hmm.setAij(2, 2, 0.80);		
		
		return hmm;
	}
	
	
	/* Initial guess for the Baum-Welch algorithm */
	
	static Hmm<ObservationDiscrete<Packet>> buildInitHmm()
	{	
		Hmm<ObservationDiscrete<Packet>> hmm = 
			new Hmm<ObservationDiscrete<Packet>>(3,
					new OpdfDiscreteFactory<Packet>(Packet.class));
		
		hmm.setPi(0, 0.33);
		hmm.setPi(1, 0.33);
		hmm.setPi(2, 0.34);
		
		hmm.setOpdf(0, new OpdfDiscrete<Packet>(Packet.class,
				new double[] { 0.7, 0.2 ,0.1}));
		hmm.setOpdf(1, new OpdfDiscrete<Packet>(Packet.class,
				new double[] { 0.1, 0.8 ,0.1}));
		
		
		hmm.setAij(0, 0, 0.8);
		hmm.setAij(0, 1, 0.1);
		hmm.setAij(0, 2, 0.1);
		
		hmm.setAij(1, 0, 0.2);
		hmm.setAij(1, 1, 0.6);
		hmm.setAij(1, 2, 0.2);
		
		hmm.setAij(2, 0, 0.2);
		hmm.setAij(2, 1, 0.6);
		hmm.setAij(2, 2, 0.2);
		
		return hmm;
	}	
	
	/* Generate several observation sequences using a HMM */
	
	static <O extends Observation> List<List<O>> 
	generateSequences(Hmm<O> hmm)
	{
		MarkovGenerator<O> mg = new MarkovGenerator<O>(hmm);
		
		List<List<O>> sequences = new ArrayList<List<O>>();
		for (int i = 0; i < 200; i++)
			sequences.add(mg.observationSequence(100));

		return sequences;
	}	
	
}
