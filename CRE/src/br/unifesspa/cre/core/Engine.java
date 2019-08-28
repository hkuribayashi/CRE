package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.ga.GA;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.pso.PSO;


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

	private Scenario scenario;

	public Engine(Double alpha, Double beta, CREEnv env) {
		this.alpha = alpha;
		this.beta = beta;
		this.env = env;
		this.scenario = new Scenario(env);
	}

	public Engine(Double alpha, Double beta, Scenario scenario) {
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
	
	public Result getPSO() {
		double target = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		System.out.println("PSO Target Solution: "+ target);
		System.out.println();
		
		PSO pso = new PSO(this.alpha, this.beta, this.scenario, this.scenario.getEnv().getPsoSteps(), 20, target);
		
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
}