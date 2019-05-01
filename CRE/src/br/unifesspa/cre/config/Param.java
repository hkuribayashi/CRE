package br.unifesspa.cre.config;

public enum Param {
	
	/* General Parameters */
	
	workingDirectory, 
	
	lambdaUser, lambdaMacro, lambdaFemto, 
	
	powerMacro, powerSmall, bandwith, area, noisePower, 
	
	heightMacro, heightSmall, heightUser, 
	
	gainMacro, gainSmall,
	
	/* Phase 1 Parameters */
	
	totalBias, biasStep, numberOfSimulations,
	
	
	/* Phase 2 Parameters */
	
	initialCrossoverProbability, finalCrossoverProbability,
	
	initialMutationProbability, finalMutationProbability,
	
	initialGeneRange, finalGeneRange,
	
	populationSize, generationSize, kElitism;
	
}