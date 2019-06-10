package br.unifesspa.cre.model;

import java.io.Serializable;

import br.unifesspa.cre.hetnet.Scenario;

public class Result implements Serializable, Comparable<Result>{

	private static final long serialVersionUID = -8876078984426952757L;

	private Double bias;
	
	private Double sumRBs;

	private Double sumRate;
	
	private Double medianRate;
	
	private Double requiredRate;
	
	private Double uesServed;
	
	private Double servingBSs;
	
	private Double evaluation;
	
	private Scenario scenario;

	public Result() {
		super();
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

	public Double getSumRBs() {
		return sumRBs;
	}

	public void setSumRBs(Double sumRBs) {
		this.sumRBs = sumRBs;
	}

	@Override
	public String toString() {
		return "Result [bias=" + bias + ", sumRBs=" + sumRBs + ", sumRate=" + sumRate + ", medianRate=" + medianRate
				+ ", requiredRate=" + requiredRate + ", uesServed=" + uesServed + ", servingBSs=" + servingBSs
				+ ", evaluation=" + evaluation + "]";
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