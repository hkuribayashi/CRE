package br.unifesspa.cre.model;

import java.io.Serializable;

public class Result extends Entity implements Serializable, Comparable<Result>{

	private static final long serialVersionUID = -8876078984426952757L;

	private Double bias;

	private Double sumRate;
	
	private Double medianRate;
	
	private Double alpha;
	
	private Double beta;
	
	private Double evaluation;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Result [bias=" + bias + ", sumRate=" + sumRate + ", medianRate=" + medianRate + ", alpha=" + alpha
				+ ", beta=" + beta + ", evaluation=" + evaluation + "]";
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