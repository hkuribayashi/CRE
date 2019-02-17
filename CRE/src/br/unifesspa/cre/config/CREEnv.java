package br.unifesspa.cre.config;

import java.io.Serializable;

public class CREEnv implements Serializable{

	private static final long serialVersionUID = 6824819508443271546L;

	private Double lambdaUser = 10.0;

	private Double lambdaMacro = 10.0;

	private Double lambdaFemto = 10.0;

	private Double powerMacro = 46.0;

	private Double powerFemto = 23.0;

	private Double bandwidth = 20.0;

	private Double spatialLoadFactorMacro = 0.1;

	private Double spatialLoadFactorFemto = 0.1;

	private Double referenceDistance = 35.0;

	private Double constant = 33.88;

	private Double area = 100.0;

	private Double alpha = 3.75;

	private Double noisePower = -101.0;

	private Double antennasMacro = 32.0;

	private Double antennasFemto = 1.0;

	private Double antennasUser = 1.0;
	
	private Double heightMacro = 30.0;
	
	private Double heightFemto = 1.5;
	
	private Double heightUser = 1.0;
	

	public void set(Param param, Double value) {

		switch (param) {

		case lambdaMacro: this.lambdaMacro = value; break;

		case lambdaFemto: this.lambdaFemto = value; break;

		case lambdaUser: this.lambdaUser = value; break;
		
		case powerMacro: this.powerMacro = value; break;
		
		case powerFemto: this.powerFemto = value; break;
		
		case bandwith: this.bandwidth = value; break;
		
		case spatialLoadFactorMacro: this.spatialLoadFactorMacro = value; break;
		
		case spatialLoadFactorFemto: this.spatialLoadFactorFemto = value; break;
		
		case referenceDistance: this.referenceDistance = value; break;
		
		case constant: this.constant = value; break;
		
		case area: this.area = value; break;
		
		case alpha: this.alpha = value; break;
		
		case noisePower: this.noisePower = value; break;
		
		case antennasMacro: this.antennasMacro = value; break;
		
		case antennasFemto: this.antennasFemto = value; break;
		
		case antennasUser: this.antennasUser = value; break;
		
		case heightMacro: this.heightMacro = value; break;
		
		case heightFemto: this.heightFemto = value; break;
		
		case heightUser: this.heightUser = value; break;

		default: break;

		}
	}

	public Double getLambdaUser() {
		return lambdaUser;
	}

	public void setLambdaUser(Double lambdaUser) {
		this.lambdaUser = lambdaUser;
	}

	public Double getLambdaMacro() {
		return lambdaMacro;
	}

	public void setLambdaMacro(Double lambdaMacro) {
		this.lambdaMacro = lambdaMacro;
	}

	public Double getLambdaFemto() {
		return lambdaFemto;
	}

	public void setLambdaFemto(Double lambdaFemto) {
		this.lambdaFemto = lambdaFemto;
	}

	public Double getPowerMacro() {
		return powerMacro;
	}

	public void setPowerMacro(Double powerMacro) {
		this.powerMacro = powerMacro;
	}

	public Double getPowerFemto() {
		return powerFemto;
	}

	public void setPowerFemto(Double powerFemto) {
		this.powerFemto = powerFemto;
	}

	public Double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public Double getSpatialLoadFactorMacro() {
		return spatialLoadFactorMacro;
	}

	public void setSpatialLoadFactorMacro(Double spatialLoadFactorMacro) {
		this.spatialLoadFactorMacro = spatialLoadFactorMacro;
	}

	public Double getSpatialLoadFactorFemto() {
		return spatialLoadFactorFemto;
	}

	public void setSpatialLoadFactorFemto(Double spatialLoadFactorFemto) {
		this.spatialLoadFactorFemto = spatialLoadFactorFemto;
	}

	public Double getReferenceDistance() {
		return referenceDistance;
	}

	public void setReferenceDistance(Double referenceDistance) {
		this.referenceDistance = referenceDistance;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getAlpha() {
		return alpha;
	}

	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public Double getNoisePower() {
		return noisePower;
	}

	public void setNoisePower(Double noisePower) {
		this.noisePower = noisePower;
	}

	public Double getAntennasMacro() {
		return antennasMacro;
	}

	public void setAntennasMacro(Double antennasMacro) {
		this.antennasMacro = antennasMacro;
	}

	public Double getAntennasFemto() {
		return antennasFemto;
	}

	public void setAntennasFemto(Double antennasFemto) {
		this.antennasFemto = antennasFemto;
	}

	public Double getAntennasUser() {
		return antennasUser;
	}

	public void setAntennasUser(Double antennasUser) {
		this.antennasUser = antennasUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getConstant() {
		return constant;
	}

	public void setConstant(Double constant) {
		this.constant = constant;
	}

	public Double getHeightMacro() {
		return heightMacro;
	}

	public void setHeightMacro(Double heightMacro) {
		this.heightMacro = heightMacro;
	}

	public Double getHeightFemto() {
		return heightFemto;
	}

	public void setHeightFemto(Double heightFemto) {
		this.heightFemto = heightFemto;
	}

	public Double getHeightUser() {
		return heightUser;
	}

	public void setHeightUser(Double heightUser) {
		this.heightUser = heightUser;
	}

	@Override
	public String toString() {
		return "CREEnv [lambdaUser=" + lambdaUser + ", lambdaMacro=" + lambdaMacro + ", lambdaFemto=" + lambdaFemto
				+ ", powerMacro=" + powerMacro + ", powerFemto=" + powerFemto + ", bandwidth=" + bandwidth
				+ ", spatialLoadFactorMacro=" + spatialLoadFactorMacro + ", spatialLoadFactorFemto="
				+ spatialLoadFactorFemto + ", referenceDistance=" + referenceDistance + ", constant=" + constant
				+ ", area=" + area + ", alpha=" + alpha + ", noisePower=" + noisePower + ", antennasMacro="
				+ antennasMacro + ", antennasFemto=" + antennasFemto + ", antennasUser=" + antennasUser
				+ ", heightMacro=" + heightMacro + ", heightFemto=" + heightFemto + ", heightUser=" + heightUser + "]";
	}
}