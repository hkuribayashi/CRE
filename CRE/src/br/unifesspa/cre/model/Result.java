package br.unifesspa.cre.model;

import java.io.Serializable;

import br.unifesspa.cre.hetnet.Scenario;

public class Result extends Entity implements Serializable, Comparable<Result>{

	private static final long serialVersionUID = -8876078984426952757L;

	private Double bias;

	private Double sumRate;
	
	private Double medianRate;
	
	private Double alpha;
	
	private Double beta;
	
	private Double evaluation;
	
	private Double requiredRate;
	
	private Double uesServed;
	
	private Double servingBSs;
	
	private Scenario scenario;
	
	private Double[] solution;

	public Result() {
		super();
	}

	public Result(Integer id) {
		super(id);
	}

	public Result(Integer id, Double bias, Double sumRate, Double medianRate, Double alpha, Double beta) {
		super(id);
		this.bias = bias;
		this.sumRate = sumRate;
		this.medianRate = medianRate;
		this.alpha = alpha;
		this.beta = beta;
		this.requiredRate = 0.0;
		this.uesServed = 0.0;
		this.scenario = null;
	}

	public Double getSumRate() {
		return sumRate;
	}

	public void setSumRate(Double sumRate) {
		this.sumRate = sumRate;
	}

	public Double getMedianRate() {
		return medianRate;
	}

	public void setMedianRate(Double medianRate) {
		this.medianRate = medianRate;
	}

	public Double getBias() {
		return bias;
	}

	public void setBias(Double bias) {
		this.bias = bias;
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

	public Double getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Double evaluation) {
		this.evaluation = evaluation;
	}

	public Double getRequiredRate() {
		return requiredRate;
	}

	public void setRequiredRate(Double requiredRate) {
		this.requiredRate = requiredRate;
	}

	public Double getUesServed() {
		return uesServed;
	}

	public void setUesServed(Double uesServed) {
		this.uesServed = uesServed;
	}

	public Double getServingBSs() {
		return servingBSs;
	}

	public void setServingBSs(Double servingBSs) {
		this.servingBSs = servingBSs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Double[] getSolution() {
		return solution;
	}

	public void setSolution(Double[] solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return "\nResult [bias=" + bias + ", alpha=" + alpha + ", beta=" + beta + ", uesServed=" + uesServed
				+ ", servingBSs=" + servingBSs + ", evaluation=" + evaluation + ", sumRate=" + sumRate + ", medianRate="
				+ medianRate + ", requiredRate=" + requiredRate + "]";
	}

	@Override
	public int compareTo(Result o) {
		if (this.evaluation == o.evaluation) 
			return 0;
		else if (this.evaluation > o.evaluation)
				return 1;
		else return -1;
	}
}