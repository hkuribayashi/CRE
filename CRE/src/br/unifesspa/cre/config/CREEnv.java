package br.unifesspa.cre.config;

import java.io.Serializable;

public class CREEnv implements Serializable{

	private static final long serialVersionUID = 6824819508443271546L;

	private Double lambdaUser = 10.0;

	private Double lambdaMacro = 10.0;

	private Double lambdaFemto = 10.0;

	private Double powerMacro = 46.0;

	private Double powerFemto = 23.0;

	private Double bandwidth = 20000000.0;

	private Double area = 100.0;

	private Double noisePower = -101.0;
	
	private Double heightMacro = 35.0;
	
	private Double heightFemto = 25.0;
	
	private Double heightUser = 25.0;
	
	private Double bias = 0.0;
	

	public void set(Param param, Double value) {

		switch (param) {

		case lambdaMacro: this.lambdaMacro = value; break;

		case lambdaFemto: this.lambdaFemto = value; break;

		case lambdaUser: this.lambdaUser = value; break;
		
		case powerMacro: this.powerMacro = value; break;
		
		case powerFemto: this.powerFemto = value; break;
		
		case bandwith: this.bandwidth = value; break;
		
		case area: this.area = value; break;
		
		case noisePower: this.noisePower = value; break;
		
		case heightMacro: this.heightMacro = value; break;
		
		case heightFemto: this.heightFemto = value; break;
		
		case heightUser: this.heightUser = value; break;
		
		case bias: this.bias = value; break;

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

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getNoisePower() {
		return noisePower;
	}

	public void setNoisePower(Double noisePower) {
		this.noisePower = noisePower;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Double getBias() {
		return bias;
	}

	public void setBias(Double bias) {
		this.bias = bias;
	}

	@Override
	public String toString() {
		return "CREEnv [lambdaUser=" + lambdaUser + ", lambdaMacro=" + lambdaMacro + ", lambdaFemto=" + lambdaFemto
				+ ", powerMacro=" + powerMacro + ", powerFemto=" + powerFemto + ", bandwidth=" + bandwidth + ", area="
				+ area + ", noisePower=" + noisePower + ", heightMacro=" + heightMacro + ", heightFemto=" + heightFemto
				+ ", heightUser=" + heightUser + ", bias=" + bias + "]";
	}
}