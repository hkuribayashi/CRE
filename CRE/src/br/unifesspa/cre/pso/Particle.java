package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.util.Util;

public class Particle implements Comparable<Particle>, Cloneable{

	private Double[] position;

	private Double[] bestPosition;

	private Double bestEvaluation;

	private Double evaluation;

	private Double[] velocity;

	private Scenario scenario;

	private Double alpha;

	private Double beta;

	public Particle(Double alpha, Double beta, Scenario scenario, Integer particleSize) {
		this.alpha = alpha;
		this.beta = beta;
		this.scenario = scenario.clone();
		this.position = new Double[particleSize];

		for (int i=0; i<this.position.length; i++) {
			this.position[i] = Util.getUniformRealDistribution(-10.0, 80.0);
		}

		this.bestPosition = this.position.clone();
		this.velocity = new Double[particleSize];
		Util.init(this.velocity);
		
		this.evaluation();
		this.bestEvaluation = 0.0;
	}

	public synchronized void evaluation() {
		this.scenario.setBias(this.position);
		this.scenario.evaluation();
		this.evaluation = (this.alpha * this.scenario.getUesServed()) + (this.beta * this.scenario.getServingBSs());
	}

	public void updateBestPosition() {
		if (this.evaluation > this.bestEvaluation) {
			this.bestPosition = this.position;
			this.bestEvaluation = this.evaluation;
		}	
	}

	@Override
	public int compareTo(Particle o) {
		if (this.evaluation == o.evaluation)
			return 0;
		else if (this.evaluation > o.evaluation)
			return 1;
		else return -1;
	}

	public Object clone(){ 
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {			
			e.printStackTrace();
			return null;
		} 
	}

	public Double[] getPosition() {
		return position;
	}

	public void setPosition(Double[] position) {
		this.position = position;
	}

	public Double[] getBestPosition() {
		return bestPosition;
	}

	public void setBestPosition(Double[] bestPosition) {
		this.bestPosition = bestPosition;
	}

	public Double getBestEvaluation() {
		return bestEvaluation;
	}

	public void setBestEvaluation(Double bestEvaluation) {
		this.bestEvaluation = bestEvaluation;
	}

	public Double getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Double evaluation) {
		this.evaluation = evaluation;
	}

	public Double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(Double[] velocity) {
		this.velocity = velocity;
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
}