package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Particule implements Comparable<Particule>{
	
	private Double currentPosition;
	
	private Double bestPosition;

	private Double[] currentConfiguration;
	
	private Double[] bestConfiguration;
	
	private Double[] inertiaWeight;

	private Scenario scenario;
	
	private Double alpha;
	
	private Double beta;
	
	private Result result;

	public Particule(Double alpha, Double beta, Scenario scenario) {
		this.scenario = scenario;
		this.currentPosition = 0.0;
		this.bestPosition = 0.0;
		this.alpha = alpha;
		this.beta = beta;
		this.bestConfiguration = null;
		this.result = new Result();
		this.result.setAlpha(this.alpha);
		this.result.setBeta(this.beta);

		Double qtdMacro = this.scenario.getEnv().getArea() * this.scenario.getEnv().getLambdaMacro();
		int configurationSize = this.scenario.getAllBS().size()-(qtdMacro.intValue());

		double lowerBound = this.scenario.getEnv().getInitialGeneRange();
		double upperBound = this.scenario.getEnv().getFinalGeneRange();

		this.currentConfiguration = new Double[configurationSize];
		this.inertiaWeight = new Double[configurationSize];
		
		double bW = 0.9;
		double aW = (0.6 - bW)/50.0;		

		for (int i=0; i<configurationSize; i++) {
			this.currentConfiguration[i] = Util.getUniformRealDistribution(lowerBound, upperBound);
			inertiaWeight[i] = (aW*i)+bW;
		}
	}
	
	public void evaluate(Double targetSolution) {
		this.scenario.setBias(this.currentConfiguration);
		this.scenario.evaluation();
		
		this.currentPosition = (this.scenario.getUesServed() * this.alpha) + (this.scenario.getServingBSs() * this.beta);
		
		if (this.currentPosition >= this.bestPosition) {
			this.bestPosition = this.currentPosition;
			this.bestConfiguration = this.currentConfiguration;
			this.result.setEvaluation(this.bestPosition);
			this.result.setMedianRate(this.scenario.getMedianRate());
			this.result.setSumRate(this.scenario.getSumRate());
			this.result.setUesServed(this.scenario.getUesServed());
			this.result.setServingBSs(this.scenario.getServingBSs());
			this.result.setRequiredRate(this.scenario.getRequiredRate());
			this.result.setSolution(this.bestConfiguration);
		}
	}
	
	public void updateVelocity(Double[] gBestConfiguration) {
		for (int i=0; i<this.currentConfiguration.length; i++) {
			double phi1 = Math.random() * 0.3;
			double phi2 = Math.random() * 1.3;
			Double newVelocity = (bestConfiguration[i] - this.currentConfiguration[i]) * phi1 +
							(gBestConfiguration[i] - this.currentConfiguration[i]) * phi2;
			this.currentConfiguration[i] = (this.inertiaWeight[i]) * this.currentConfiguration[i] + newVelocity;
		}
	}

	@Override
	public int compareTo(Particule o) {
		if (this.bestPosition == o.bestPosition)
			return 0;
		else if (this.bestPosition > o.bestPosition)
			return 1;
		else return -1;
	}

	public Double getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Double currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Double getBestPosition() {
		return bestPosition;
	}

	public void setBestPosition(Double bestPosition) {
		this.bestPosition = bestPosition;
	}

	public Double[] getCurrentConfiguration() {
		return currentConfiguration;
	}

	public void setCurrentConfiguration(Double[] currentConfiguration) {
		this.currentConfiguration = currentConfiguration;
	}

	public Double[] getBestConfiguration() {
		return bestConfiguration;
	}

	public void setBestConfiguration(Double[] bestConfiguration) {
		this.bestConfiguration = bestConfiguration;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}