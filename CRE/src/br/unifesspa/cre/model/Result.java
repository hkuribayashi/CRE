package br.unifesspa.cre.model;

public class Result extends Entity implements Comparable<Result>{
	
	private Double bias;

	private Double sumRate;
	
	private Double medianRate;
	
	private Long executionTime;

	public Result() {
		super();
	}

	public Result(Integer id) {
		super(id);
	}

	public Result(Integer id, Double bias, Double sumRate, Double medianRate, Long executionTime) {
		super(id);
		this.bias = bias;
		this.sumRate = sumRate;
		this.medianRate = medianRate;
		this.executionTime = executionTime;
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

	public Long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}

	public Double getBias() {
		return bias;
	}

	public void setBias(Double bias) {
		this.bias = bias;
	}

	@Override
	public String toString() {
		return "Result [bias=" + bias + ", sumRate=" + sumRate + ", medianRate=" + medianRate + ", executionTime="
				+ executionTime + "]";
	}

	@Override
	public int compareTo(Result o) {
		if (this.sumRate > o.getSumRate()) 
			return -1;
		else if (this.sumRate == o.getSumRate())
				return 0;
		else return 1;
	}
}