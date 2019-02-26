package br.unifesspa.cre.model;

public class Result extends Entity {
	
	private String label;

	private Double sumRate;
	
	private Double medianRate;
	
	private Long executionTime;

	public Result() {
		super();
	}

	public Result(Integer id) {
		super(id);
	}

	public Result(Integer id, String label, Double sumRate, Double medianRate, Long executionTime) {
		super(id);
		this.label = label;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Result [label=" + label + ", sumRate=" + sumRate + ", medianRate=" + medianRate + ", executionTime="
				+ executionTime + "]";
	}
}