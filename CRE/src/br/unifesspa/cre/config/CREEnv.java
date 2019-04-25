package br.unifesspa.cre.config;

import java.io.Serializable;

/**
 * @author hugo
 *
 */
public class CREEnv implements Serializable{

	private static final long serialVersionUID = 6824819508443271546L;
	
	private String workingDirectory = "/Users/hugo/Desktop/CRE/";

	private Double lambdaUser = 10.0;

	private Double lambdaMacro = 10.0;

	private Double lambdaFemto = 10.0;

	private Double powerMacro = 46.0;

	private Double powerFemto = 30.0;

	private Double bandwidth = 20000000.0;

	private Double area = 100.0;

	private Double noisePower = -176.0;
	
	private Double heightMacro = 25.0;
	
	private Double heightFemto = 10.0;
	
	private Double heightUser = 2.0;
	
	private Double initialCrossoverProbability = 0.9;
	
	private Double finalCrossoverProbability = 0.6;
	
	private Double initialMutationProbability = 0.2;
	
	private Double finalMutationProbability = 0.8;
	
	private Double initialGeneRange = -3.0;
	
	private Double finalGeneRange = 3.0;
	
	private Integer populationSize = 20;
	
	private Integer kElitism = 2;
	
	private Integer generationSize = 1000;
	
	private Integer totalBias = 200;
	
	private Double biasStep = 0.05;
	
	private Double numberOfSimulations = 1000.0;
	
	
	public void set(Param param, String value) {
		
		switch(param) {
		
		case workingDirectory: this.workingDirectory = value; break;
		
		default: break;
		
		}
	}
	
	public void set(Param param, Integer value) {

		switch (param) {
			
		case populationSize: this.populationSize = value; break;
		
		case kElitism: this.kElitism = value; break;
		
		case generationSize: this.generationSize = value; break;
		
		case totalBias: this.totalBias = value; break;
		
		default: break;
		
		}
	}

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
		
		case initialCrossoverProbability: this.initialCrossoverProbability = value; break;
		
		case finalCrossoverProbability: this.finalCrossoverProbability = value; break;
		
		case initialGeneRange: this.initialGeneRange = value; break;
		
		case finalGeneRange: this.finalGeneRange = value; break;
		
		case initialMutationProbability: this.initialMutationProbability = value; break;
		
		case finalMutationProbability: this.finalMutationProbability = value; break;
		
		case biasStep: this.biasStep = value; break;
		
		case numberOfSimulations: this.numberOfSimulations = value; break;

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

	public Double getInitialCrossoverProbability() {
		return initialCrossoverProbability;
	}

	public void setInitialCrossoverProbability(Double initialCrossoverProbability) {
		this.initialCrossoverProbability = initialCrossoverProbability;
	}

	public Double getFinalCrossoverProbability() {
		return finalCrossoverProbability;
	}

	public void setFinalCrossoverProbability(Double finalCrossoverProbability) {
		this.finalCrossoverProbability = finalCrossoverProbability;
	}

	public Double getInitialGeneRange() {
		return initialGeneRange;
	}

	public void setInitialGeneRange(Double initialGeneRange) {
		this.initialGeneRange = initialGeneRange;
	}

	public Double getFinalGeneRange() {
		return finalGeneRange;
	}

	public void setFinalGeneRange(Double finalGeneRange) {
		this.finalGeneRange = finalGeneRange;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(Integer populationSize) {
		this.populationSize = populationSize;
	}

	public Integer getkElitism() {
		return kElitism;
	}

	public void setkElitism(Integer kElitism) {
		this.kElitism = kElitism;
	}

	public Integer getGenerationSize() {
		return generationSize;
	}

	public void setGenerationSize(Integer generationSize) {
		this.generationSize = generationSize;
	}

	public Double getInitialMutationProbability() {
		return initialMutationProbability;
	}

	public void setInitialMutationProbability(Double initialMutationProbability) {
		this.initialMutationProbability = initialMutationProbability;
	}

	public Double getFinalMutationProbability() {
		return finalMutationProbability;
	}

	public void setFinalMutationProbability(Double finalMutationProbability) {
		this.finalMutationProbability = finalMutationProbability;
	}

	public Integer getTotalBias() {
		return totalBias;
	}

	public void setTotalBias(Integer totalBias) {
		this.totalBias = totalBias;
	}

	public Double getBiasStep() {
		return biasStep;
	}

	public void setBiasStep(Double biasStep) {
		this.biasStep = biasStep;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
	public Double getNumberOfSimulations() {
		return numberOfSimulations;
	}

	public void setNumberOfSimulations(Double numberOfSimulations) {
		this.numberOfSimulations = numberOfSimulations;
	}

	@Override
	public String toString() {
		return "CREEnv [workingDirectory=" + workingDirectory + ", lambdaUser=" + lambdaUser + ", lambdaMacro="
				+ lambdaMacro + ", lambdaFemto=" + lambdaFemto + ", powerMacro=" + powerMacro + ", powerFemto="
				+ powerFemto + ", bandwidth=" + bandwidth + ", area=" + area + ", noisePower=" + noisePower
				+ ", heightMacro=" + heightMacro + ", heightFemto=" + heightFemto + ", heightUser=" + heightUser
				+ ", initialCrossoverProbability=" + initialCrossoverProbability + ", finalCrossoverProbability="
				+ finalCrossoverProbability + ", initialMutationProbability=" + initialMutationProbability
				+ ", finalMutationProbability=" + finalMutationProbability + ", initialGeneRange=" + initialGeneRange
				+ ", finalGeneRange=" + finalGeneRange + ", populationSize=" + populationSize + ", kElitism=" + kElitism
				+ ", generationSize=" + generationSize + ", totalBias=" + totalBias + ", biasStep=" + biasStep
				+ ", numberOfSimulations=" + numberOfSimulations + "]";
	}
}