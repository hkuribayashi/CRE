package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.ga.GA;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;
import br.unifesspa.cre.view.Topology;

/**
 * 
 * @author hugo
 *
 */
public class Engine {
	
	private Double alpha;
	
	private Double beta;
	
	private Double[] biasOffset;
	
	private CREEnv env;
	
	public Engine(Double alpha, Double beta, CREEnv env) {
		this.alpha = alpha;
		this.beta = beta;
		this.env = env;
		
		double biasStep = this.env.getBiasStep();
		int totalBias = this.env.getTotalBias();
		
		Double initialBias = 10.0;
		this.biasOffset = new Double[totalBias];
		for (int i=0; i<this.biasOffset.length; i++)
			biasOffset[i] = initialBias + (biasStep * i);
	}

	public void run() {
		List<Result> results1 = this.getPhase1();
		this.getPhase2(results1);
	}
	
	public void paitTopology(Scenario s) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 1000, 1000);
		window.getContentPane().add(new Topology(s));
		window.setVisible(true);
	}

	private List<Result> getPhase1() {
		Double simulations = this.env.getNumberOfSimulations();
		
		String path = this.env.getWorkingDirectory();
		path += "results.alpha-"+alpha+".beta-"+beta+".data";
		DAO<List<Result>> dao = new DAO<List<Result>>();
		Boolean flag = dao.verifyPath(path);
		
		Scenario s = null;
		List<Result> results = new ArrayList<Result>();
		
		System.out.println("Simulation Parameters: alpha-"+this.alpha+" beta-"+beta);
		System.out.println(env);
		System.out.println();
		System.out.println("Phase 1: Static Bias: Iterating over static bias values.");
		System.out.println("Bias Offset: "+this.biasOffset[0]+" to "+this.biasOffset[biasOffset.length-1]);
		System.out.println();
		System.out.println("Results:");
		
		if (!flag) {
			
			long startTime = System.nanoTime();
			Double[] sRate = new Double[simulations.intValue()];
			Double[] mRate = new Double[simulations.intValue()];

			for (int i=0; i<this.biasOffset.length; i++) {
				
				for (int j=0; j<simulations; j++) {
					s = new Scenario(env);
					s.initBias(biasOffset[i]);
					s.evaluation();
					
					sRate[j] = s.getSumRate();
					mRate[j] = s.getMedianRate();
				}
				
				Result r = new Result();
				r.setBias(biasOffset[i]);
				r.setAlpha(this.alpha);
				r.setBeta(this.beta);
				
				List<Double> sr = Util.getConfidenceInterval(sRate, 0.90);
				double sumR = sr.stream().mapToDouble(Double::doubleValue).sum();
				
				List<Double> mr = Util.getConfidenceInterval(mRate, 0.90);
				double medianR = mr.stream().mapToDouble(Double::doubleValue).sum();
				
				r.setSumRate( sumR/sr.size() );
				r.setMedianRate( medianR/mr.size() );
						
				results.add(r);
		
				System.out.println(r);				
			}
			
			long endTime = System.nanoTime();
			long elapsedTime = endTime - startTime;
			System.out.println("Elapsed Time: "+TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)+" ms");
			
			dao.save(results, path);
			
			System.out.println("Results saved in "+path);
			System.out.println();
			Collections.sort(results);
			System.out.println("Best Static Bias: "+results.get(0));
			System.out.println();
		}else {
			results = dao.restore(path);
			System.out.println("Previous Phase 1 results Detected in "+path+ " file.");
			System.out.println("Previous Phase 1 results successfully loaded.");
			System.out.println("Best Static Bias: "+results.get(0));
			System.out.println();
		}
		return results;
	}
	
	private void getPhase2(List<Result> results) {
		System.out.println("Phase 2: Genectic Algorithm Execution - Searching for near-optimal bias values");
		System.out.println();
		
		HashMap<String, Double> map = Util.getChromossomeRange(results);
		
		env.set(Param.initialGeneRange, map.get("minBias"));
		env.set(Param.finalGeneRange, map.get("maxBias"));
		
		Scenario s = new Scenario(env);

		GA ga = new GA(this.alpha, this.beta, s);
		ga.evolve();
		
		System.out.println();
		System.out.println("Best Individual: ");
		System.out.println("Evaluation: "+ga.getBestSolution());
		System.out.println(ga.getBestIndividual().getResult());
		System.out.println("Chromossome Configuration: ");
		
		Double[] chromossome = ga.getBestIndividual().getChromossome();
		for (int i=0; i<chromossome.length; i++)
			System.out.print(chromossome[i]+" ");
		System.out.println();
	}

	public Double getAlpha() {
		return alpha;
	}

	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public Double getBeta() {
		return beta;
	}

	public void setBeta(Double beta) {
		this.beta = beta;
	}

	public Double[] getBiasOffset() {
		return biasOffset;
	}

	public void setBiasOffset(Double[] biasOffset) {
		this.biasOffset = biasOffset;
	}

	public CREEnv getEnv() {
		return env;
	}

	public void setEnv(CREEnv env) {
		this.env = env;
	}
}