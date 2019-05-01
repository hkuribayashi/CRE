package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;

public class Main {

	public static void main(String[] args) {
		
		String path = "/Users/hugo/Desktop/CRE/";
		if (args.length != 0)
			path = args[0];
		
		//Setting alpha and beta values
		Double[] alphas = {100.0, 10.0, 2.0, 1.0, 1.0, 1.0, 1.0 };
		Double[] betas = {1.0, 1.0, 1.0, 1.0, 2.0, 10.0, 100.0};
		
		CREEnv env = new CREEnv();
		
		//Setting general simulations parameters
		env.set(Param.area, 10000.0); 	  // 1 km^2
		env.set(Param.lambdaFemto, 0.0001);  // 0.00002 Femto/m^2 = 20 Femtos  
		env.set(Param.lambdaUser, 0.0005);    // 0.0001 Users/m^2 = 100 Users 
		env.set(Param.lambdaMacro, 0.000002); // 0.000002 Macros/m^2 = 2 Macros
		env.set(Param.powerMacro, 46.0);	  // dBm
		env.set(Param.powerSmall, 30.0);	  // dBm
		env.set(Param.noisePower, -174.0);	  // dBm/Hz
		env.set(Param.gainMacro, 15.0);		  // dBi
		env.set(Param.gainSmall, 5.0);		  // dBi
		
		//Setting Parameters to Phase 1: Static Bias		
		env.set(Param.totalBias, 8);
		env.set(Param.biasStep, 10.0);
		env.set(Param.numberOfSimulations, 2.0);
		
		//Setting parameters to Pahse 2: GA
		env.set(Param.initialCrossoverProbability, 0.9);
		env.set(Param.finalCrossoverProbability, 0.5);
		env.set(Param.initialMutationProbability, 0.2);
		env.set(Param.finalMutationProbability, 0.8);
		env.set(Param.populationSize, (env.getLambdaSmall()*env.getArea()));
		env.set(Param.generationSize, 1000);
		env.set(Param.kElitism, 5);
		env.set(Param.workingDirectory, path);
		
	
		for (int i=0; i<alphas.length; i++) {
			
			double alpha = alphas[i];
			double beta = betas[i];
			Engine e = new Engine(alpha, beta, env);
			e.run();
			
		}
		
		
	}
}
