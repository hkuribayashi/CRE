package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

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