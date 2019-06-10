package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Particule implements Comparable<Particule>{
	
	private Double currentPosition;
	
	private Double bestPosition;

	private Double[] currentConfiguration;
	
	private Double[] bestConfiguration;

	private Scenario scenario;
	
	private Result result;

	public Particule(Scenario scenario) {
		this.scenario = scenario;
		this.currentPosition = Double.MAX_VALUE;
		this.bestPosition = Double.MAX_VALUE;
		this.bestConfiguration = null;
		this.result = new Result();

		Double qtdMacro = this.scenario.getEnv().getArea() * this.scenario.getEnv().getLambdaMacro();
		int configurationSize = this.scenario.getAllBS().size()-(qtdMacro.intValue());
		double lowerBound, upperBound;

		lowerBound = 10.0;
		upperBound = 40.0;

		this.currentConfiguration = new Double[configurationSize];

		for (int i=0; i<configurationSize; i++)
			this.currentConfiguration[i] = Util.getUniformRealDistribution(lowerBound, upperBound);
	}
	
	public void evaluate(Double targetSolution) {
		this.scenario.setBias(this.currentConfiguration);
		this.scenario.evaluation();
		
		this.currentPosition = (this.scenario.getUesServed() + this.scenario.getSumRate());
		this.currentPosition = Math.sqrt(Math.pow(this.currentPosition - targetSolution, 2.0));
		
		if (this.currentPosition < this.bestPosition) {
			this.bestPosition = this.currentPosition;
			this.bestConfiguration = this.currentConfiguration;
			this.result.setEvaluation(this.bestPosition);
			this.result.setMedianRate(this.scenario.getMedianRate());
			this.result.setSumRate(this.scenario.getSumRate());
			this.result.setUesServed(this.scenario.getUesServed());
			this.result.setServingBSs(this.scenario.getServingBSs());
		}
	}
	
	public void updateVelocity(Double[] gBestConfiguration) {
		
		for (int i=0; i<this.currentConfiguration.length; i++) {
			double phi1 = Math.random() * 0.4;
			double phi2 = Math.random() * 0.4;
			Double newVelocity = (bestConfiguration[i] - this.currentConfiguration[i]) * phi1 +
							(gBestConfiguration[i] - this.currentConfiguration[i]) * phi2;
			this.currentConfiguration[i] = this.currentConfiguration[i] + newVelocity;
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}