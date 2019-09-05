package br.unifesspa.cre.config;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author hugo
 *
 */
public class CREEnv implements Serializable, Cloneable{

	private static final long serialVersionUID = 6824819508443271546L;

	public Double[] alphas = {10.0, 2.0, 1.0, 1.0, 1.0};

	public Double[] betas = {1.0, 1.0, 1.0, 2.0, 10.0};

	public String workingDirectory = "/Users/hugo/Desktop/CRE/";
	
	private Integer simulations = 100;

	private Double lambdaUser = 10.0;

	private Double lambdaMacro = 10.0;

	private Double lambdaSmall = 10.0;

	private Double powerMacro = 46.0;

	private Double powerSmall = 30.0;

	private Double bandwidth = 20.0;

	private Double area = 100.0;

	private Double noisePower = -176.0;

	private Double heightMacro = 25.0;

	private Double heightSmall = 10.0;

	private Double heightUser = 2.0;

	private Integer totalBias = 200;

	private Double initialBias = 10.0;

	private Double biasStep = 5.0;

	private Double gainMacro = 15.0;

	private Double gainSmall = 5.0;

	private Integer nSubcarriers = 12;

	private Integer nOFDMSymbols = 14;

	private Double subframeDuration = 1.0;

	private Double initialCrossoverProbability = 0.9;

	private Double finalCrossoverProbability = 0.6;

	private Double initialMutationProbability = 0.2;

	private Double finalMutationProbability = 0.8;

	private Double initialGeneRange = -3.0;

	private Double finalGeneRange = 3.0;

	private Integer populationSize = 20;

	private Integer kElitism = 2;

	private Integer generationSize = 1000;

	private Double psoSteps = 100.0;
	
	private Integer[] swarmSizeOptions = {20, 40, 60};


	public void set(Param param, String value) {
		switch(param) {

		case workingDirectory: this.workingDirectory = value; break;
		
		default: break;
		
		}
	}
	
	public void set(Param param, Integer[] value) {
		switch(param) {

		case swarmSizeOptions: this.swarmSizeOptions = value; break;
		
		default: break;
		
		}
	}

	public void set(Param param, Integer value) {

		switch (param) {

		case populationSize: this.populationSize = value; break;

		case kElitism: this.kElitism = value; break;

		case generationSize: this.generationSize = value; break;

		case totalBias: this.totalBias = value; break;

		case nSubcarriers: this.nSubcarriers = value;  break;

		case nOFDMSymbols: this.nOFDMSymbols = value; break;
		
		case simulations: this.simulations = value; break;

		default: break;

		}
	}

	public void set(Param param, Double value) {

		switch (param) {

		case lambdaMacro: this.lambdaMacro = value; break;

		case lambdaFemto: this.lambdaSmall = value; break;

		case lambdaUser: this.lambdaUser = value; break;

		case powerMacro: this.powerMacro = value; break;

		case powerSmall: this.powerSmall = value; break;

		case bandwith: this.bandwidth = value; break;

		case area: this.area = value; break;

		case noisePower: this.noisePower = value; break;

		case heightMacro: this.heightMacro = value; break;

		case heightSmall: this.heightSmall = value; break;

		case heightUser: this.heightUser = value; break;

		case initialCrossoverProbability: this.initialCrossoverProbability = value; break;

		case finalCrossoverProbability: this.finalCrossoverProbability = value; break;

		case initialGeneRange: this.initialGeneRange = value; break;

		case finalGeneRange: this.finalGeneRange = value; break;

		case initialMutationProbability: this.initialMutationProbability = value; break;

		case finalMutationProbability: this.finalMutationProbability = value; break;

		case biasStep: this.biasStep = value; break;

		case gainMacro: this.gainMacro = value; break;

		case gainSmall: this.gainSmall = value; break;

		case subframeDuration: this.subframeDuration = value; break;

		case initialBias: this.initialBias = value; break;
		
		case psoSteps: this.psoSteps = value; break;

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

	public Double getPowerMacro() {
		return powerMacro;
	}

	public void setPowerMacro(Double powerMacro) {
		this.powerMacro = powerMacro;
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

	public Double getGainMacro() {
		return gainMacro;
	}

	public void setGainMacro(Double gainMacro) {
		this.gainMacro = gainMacro;
	}

	public Double getGainSmall() {
		return gainSmall;
	}

	public void setGainFemto(Double gainSmall) {
		this.gainSmall = gainSmall;
	}

	public Double getLambdaSmall() {
		return lambdaSmall;
	}

	public void setLambdaSmall(Double lambdaSmall) {
		this.lambdaSmall = lambdaSmall;
	}

	public Double getPowerSmall() {
		return powerSmall;
	}

	public void setPowerSmall(Double powerSmall) {
		this.powerSmall = powerSmall;
	}

	public Double getHeightSmall() {
		return heightSmall;
	}

	public void setHeightSmall(Double heightSmall) {
		this.heightSmall = heightSmall;
	}

	public void setGainSmall(Double gainSmall) {
		this.gainSmall = gainSmall;
	}

	public Double getInitialBias() {
		return initialBias;
	}

	public void setInitialBias(Double initialBias) {
		this.initialBias = initialBias;
	}

	public Integer getnSubcarriers() {
		return nSubcarriers;
	}

	public void setnSubcarriers(Integer nSubcarriers) {
		this.nSubcarriers = nSubcarriers;
	}

	public Integer getnOFDMSymbols() {
		return nOFDMSymbols;
	}

	public void setnOFDMSymbols(Integer nOFDMSymbols) {
		this.nOFDMSymbols = nOFDMSymbols;
	}

	public Double getSubframeDuration() {
		return subframeDuration;
	}

	public void setSubframeDuration(Double subframeDuration) {
		this.subframeDuration = subframeDuration;
	}

	public Double[] getAlphas() {
		return alphas;
	}

	public void setAlphas(Double[] alphas) {
		this.alphas = alphas;
	}

	public Double[] getBetas() {
		return betas;
	}

	public void setBetas(Double[] betas) {
		this.betas = betas;
	}

	public Double getPsoSteps() {
		return psoSteps;
	}

	public void setPsoSteps(Double psoSteps) {
		this.psoSteps = psoSteps;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public Integer[] getSwarmSizeOptions() {
		return swarmSizeOptions;
	}

	public void setSwarmSizeOptions(Integer[] swarmSizeOptions) {
		this.swarmSizeOptions = swarmSizeOptions;
	}

	public Integer getSimulations() {
		return simulations;
	}

	public void setSimulations(Integer simulations) {
		this.simulations = simulations;
	}

	@Override
	public String toString() {
		return "CREEnv [workingDirectory=" + workingDirectory + ", simulations=" + simulations + ", lambdaUser="
				+ lambdaUser + ", lambdaMacro=" + lambdaMacro + ", lambdaSmall=" + lambdaSmall + ", powerMacro="
				+ powerMacro + ", powerSmall=" + powerSmall + ", bandwidth=" + bandwidth + ", area=" + area
				+ ", noisePower=" + noisePower + ", heightMacro=" + heightMacro + ", heightSmall=" + heightSmall
				+ ", heightUser=" + heightUser + ", totalBias=" + totalBias + ", initialBias=" + initialBias
				+ ", biasStep=" + biasStep + ", gainMacro=" + gainMacro + ", gainSmall=" + gainSmall + ", nSubcarriers="
				+ nSubcarriers + ", nOFDMSymbols=" + nOFDMSymbols + ", subframeDuration=" + subframeDuration
				+ ", initialCrossoverProbability=" + initialCrossoverProbability + ", finalCrossoverProbability="
				+ finalCrossoverProbability + ", initialMutationProbability=" + initialMutationProbability
				+ ", finalMutationProbability=" + finalMutationProbability + ", initialGeneRange=" + initialGeneRange
				+ ", finalGeneRange=" + finalGeneRange + ", populationSize=" + populationSize + ", kElitism=" + kElitism
				+ ", generationSize=" + generationSize + ", psoSteps=" + psoSteps + ", swarmSizeOptions="
				+ Arrays.toString(swarmSizeOptions) + "]";
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
}