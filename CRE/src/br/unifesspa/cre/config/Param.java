package br.unifesspa.cre.config;

public enum Param {
	
	/* General Parameters */
	
	workingDirectory, 
	
	lambdaUser, lambdaMacro, lambdaFemto, 
	
	powerMacro, powerSmall, bandwith, area, noisePower, 
	
	heightMacro, heightSmall, heightUser, 
	
	gainMacro, gainSmall, nSubcarriers, nOFDMSymbols, 
	
	subframeDuration, simulationNumber,
	
	/* Static Bias Parameters */
	
	totalBias, biasStep, initialBias,
	
	
	/* GA Parameters */
	
	initialCrossoverProbability, finalCrossoverProbability,
	
	initialMutationProbability, finalMutationProbability,
	
	initialGeneRange, finalGeneRange,
	
	populationSize, generationSize, kElitism,
	
	
	/* PSO Parameters */
	
	psoGroupSize, psoSteps;
	
}