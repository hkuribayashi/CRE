package br.unifesspa.cre.config;

public enum Param {
	
	/* General Parameters */
	alpha, beta,workingDirectory, lambdaUser, lambdaMacro, lambdaFemto, 
	
	powerMacro, powerFemto, bandwith, area, noisePower, antennasMacro, 
	
	antennasFemto, antennasUser, heightMacro, heightFemto, heightUser,
	
	/* Phase 1 Parameters */
	totalBias, biasStep, numberOfSimulations,
	
	
	/* Phase 2 Parameters */
	initialCrossoverProbability, finalCrossoverProbability,
	
	initialMutationProbability, finalMutationProbability,
	
	initialGeneRange, finalGeneRange,
	
	populationSize, generationSize, kElitism;
	
}