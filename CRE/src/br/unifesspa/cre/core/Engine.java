	package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.ga.GA;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.pso.CoPSO;
import br.unifesspa.cre.pso.DecreaseIWPSO;
import br.unifesspa.cre.pso.IncreaseIWPSO;
import br.unifesspa.cre.pso.PSO;
import br.unifesspa.cre.pso.StaticIWPSO;
import br.unifesspa.cre.pso.StochasticIWPSO;
import br.unifesspa.cre.util.Util;


/**
 * 
 * @author hugo
 *
 */
public class Engine{

	protected double alpha;

	protected double beta;

	private Double[] biasOffset;

	protected CREEnv env;

	protected Scenario scenario;

	public Engine(double alpha, double beta, CREEnv env) {
		this.alpha = alpha;
		this.beta = beta;
		this.env = env;
		this.scenario = new Scenario(env);
	}

	public Engine(double alpha, double beta, Scenario scenario) {
		this.alpha = alpha;
		this.beta = beta;
		this.scenario = scenario;
		this.env = this.scenario.getEnv();
	}

	public Result execNoBias() {

		this.biasOffset = new Double[20];
		for (int i=0; i<this.biasOffset.length; i++)
			this.biasOffset[i] = 0.0;

		this.scenario.setBias(this.biasOffset);
		this.scenario.evaluation();
		
		Result r = new Result();
		
		r.setBias(0.0);
		r.setAlpha(this.alpha);
		r.setBeta(this.beta);
		r.setSumRate(this.scenario.getSumRate());
		r.setMedianRate(this.scenario.getMedianRate());
		r.setRequiredRate(this.scenario.getRequiredRate());
		r.setUesServed(this.scenario.getUesServed());
		r.setServingBSs( this.scenario.getServingBSs() );
		r.setEvaluation( this.alpha * r.getUesServed() + this.beta * r.getServingBSs() );
		r.setScenario(this.scenario);
		
		return r;
	}

	public Result execUnifiedBias(Double bias) {

		this.biasOffset = new Double[20];
		for (int i=0; i<this.biasOffset.length; i++)
			this.biasOffset[i] = bias;

		this.scenario.setBias(this.biasOffset);
		this.scenario.evaluation();
		
		Result r = new Result();
		
		r.setBias(bias);
		r.setAlpha(this.alpha);
		r.setBeta(this.beta);
		r.setSumRate(this.scenario.getSumRate());
		r.setMedianRate(this.scenario.getMedianRate());
		r.setRequiredRate(this.scenario.getRequiredRate());
		r.setUesServed(this.scenario.getUesServed());
		r.setServingBSs( this.scenario.getServingBSs() );
		r.setEvaluation( this.alpha * r.getUesServed() + this.beta * r.getServingBSs() );
		r.setScenario(this.scenario);
		
		return r;
	}	
	
	public Result execUnifiedBiasEvolved(Double bias) {

		this.biasOffset = new Double[20];
		for (int i=0; i<this.biasOffset.length; i++)
			this.biasOffset[i] = bias;

		this.scenario.setBias(this.biasOffset);
		this.scenario.evaluationEvolved();
		
		Result r = new Result();
		
		r.setBias(bias);
		r.setAlpha(this.alpha);
		r.setBeta(this.beta);
		r.setSumRate(this.scenario.getSumRate());
		r.setMedianRate(this.scenario.getMedianRate());
		r.setRequiredRate(this.scenario.getRequiredRate());
		r.setUesServed(this.scenario.getUesServed());
		r.setServingBSs( this.scenario.getServingBSs() );
		r.setEvaluation( this.alpha * r.getUesServed() + this.beta * r.getServingBSs() );
		r.setScenario(this.scenario);
		
		return r;
	}

	public Result getGA() {
		GA ga = new GA(this.alpha, this.beta, this.scenario);
		ga.evolve();
		return ga.getBestIndividual().getResult();
	}
	
	public Result getPSO(Integer swarmSize) {
		double target = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		System.out.println("Classical PSO Target Solution: "+ target);
		System.out.println();
		
		PSO pso = new PSO(this.alpha, this.beta, this.scenario, this.scenario.getEnv().getPsoSteps(), swarmSize, target);
		
		return pso.search(); 
	}
	
	public Result getStaticIWPSO(Integer swarmSize) {
		List<Result> results = new ArrayList<Result>();
		double target = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		System.out.println("Static Inertia Weight - PSO Target Solution: "+ target);
		System.out.println();
		
		Integer simulations = this.scenario.getEnv().getSimulations();
		
		for(int i=0; i<simulations; i++) {
			StaticIWPSO pso = new StaticIWPSO(this.alpha, this.beta, this.scenario, this.scenario.getEnv().getPsoSteps(), swarmSize, target);
			results.add(pso.search());
		}
		
		return Util.getMean(results);
	}
	
	public Result getIncreaseIWPSO(Integer swarmSize) {
		List<Result> results = new ArrayList<Result>();
		double target = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		System.out.println("Increase Inertia Weight - PSO Target Solution: "+ target);
		System.out.println();
		
		Integer simulations = this.scenario.getEnv().getSimulations();
		
		for(int i=0; i<simulations; i++) {
			IncreaseIWPSO pso = new IncreaseIWPSO(this.alpha, this.beta, this.scenario, this.scenario.getEnv().getPsoSteps(), swarmSize, target);
			results.add(pso.search());
		}
		
		return Util.getMean(results);
	}
	
	public Result getDecreaseIWPSO(Integer swarmSize) {
		double target = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		System.out.println("Decrease Inertia Weight - PSO Target Solution: "+ target);
		System.out.println();
		
		DecreaseIWPSO pso = new DecreaseIWPSO(this.alpha, this.beta, this.scenario, this.scenario.getEnv().getPsoSteps(), swarmSize, target);
		
		return pso.search(); 
	}
	
	public Result getStochasticPSO(Integer swarmSize) {
		double target = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		System.out.println("Stochastic Inertia Weight - PSO Target Solution: "+ target);
		System.out.println();
		
		StochasticIWPSO pso = new StochasticIWPSO(this.alpha, this.beta, this.scenario, this.scenario.getEnv().getPsoSteps(), swarmSize, target);
		
		return pso.search(); 
	}
	
	public Result getCoPSO(Integer swarmSize) {
		double target = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		System.out.println("Constriction Inertia Weight - PSO Target Solution: "+ target);
		System.out.println();
		
		CoPSO pso = new CoPSO(this.alpha, this.beta, this.scenario, this.scenario.getEnv().getPsoSteps(), swarmSize, target);
		
		return pso.search(); 
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

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
}