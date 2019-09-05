package br.unifesspa.cre.ga;

import java.io.Serializable;

public class NetworkElement implements Comparable<NetworkElement>,Serializable,Cloneable{

	private static final long serialVersionUID = -670526476345447198L;

	private Double distance;
	
	private Boolean coverageStatus = false;
	
	private double sinr;
	
	private Double bandwith;
	
	private Double delay;

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Boolean getCoverageStatus() {
		return coverageStatus;
	}

	public void setCoverageStatus(Boolean coverageStatus) {
		this.coverageStatus = coverageStatus;
	}

	public Double getSinr() {
		return sinr;
	}

	public void setSinr(Double sinr) {
		this.sinr = sinr;
	}

	public Double getBandwith() {
		return bandwith;
	}

	public void setBandwith(Double bandwith) {
		this.bandwith = bandwith;
	}

	public Double getDelay() {
		return delay;
	}

	public void setDelay(Double delay) {
		this.delay = delay;
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return this;
		}
	}

	@Override
	public int compareTo(NetworkElement o) {
		if (this.sinr < o.sinr)
			return -1;
		else if (this.sinr > o.sinr)
			return 1;
		else return 0;
	}
}
