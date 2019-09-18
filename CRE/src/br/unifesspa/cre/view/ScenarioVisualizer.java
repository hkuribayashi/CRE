package br.unifesspa.cre.view;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.hetnet.Scenario;

public class ScenarioVisualizer{
	
	public static void main(String[] args) {
		
		CREEnv env = new CREEnv();

		//Setting general simulations parameters
		env.set(Param.area, 1000000.0); 	   // 1 km^2
		env.set(Param.lambdaFemto, 0.00002);   // 0.00002 Femto/m^2 = 20 Femtos  
		env.set(Param.lambdaUser, 0.0003);     // 0.0003 Users/m^2 = 300 Users 
		env.set(Param.lambdaMacro, 0.000002);  // 0.000002 Macros/m^2 = 2 Macros
		env.set(Param.powerMacro, 46.0);	   // dBm
		env.set(Param.powerSmall, 30.0);	   // dBm
		env.set(Param.noisePower, -174.0);	   // dBm/Hz
		env.set(Param.gainMacro, 15.0);		   // dBi
		env.set(Param.gainSmall, 5.0);		   // dBi
		env.set(Param.nSubcarriers, 12);	   // 12 Sub-carriers per Resource Block
		env.set(Param.nOFDMSymbols, 14);	   // 14 OFDM Symbols per subframe
		env.set(Param.subframeDuration, 1.0);  // 1 ms
		env.set(Param.workingDirectory, "/Users/hugo/Desktop/CRE/"); // Working Directory
		env.set(Param.simulations, 50.0);	   // Number of Simulations

		//Setting Parameters to Phase 1: Static Bias		
		env.set(Param.totalBias, 100);
		env.set(Param.biasStep, 1.0);
		env.set(Param.initialBias, -10.0);

		//Setting GA Parameters
		env.set(Param.initialCrossoverProbability, 0.9);
		env.set(Param.finalCrossoverProbability, 0.6);
		env.set(Param.initialMutationProbability, 0.5);
		env.set(Param.finalMutationProbability, 0.9);
		env.set(Param.populationSize, (env.getLambdaSmall() * env.getArea()));
		env.set(Param.generationSize, 100);
		env.set(Param.kElitism, 1);
		env.set(Param.initialGeneRange, -10.0);
		env.set(Param.finalGeneRange, 80.0);

		//Setting PSO Parameters
		env.set(Param.psoSteps, 100);
		
		Scenario s = new Scenario(env);
		s.initBias(40.0);
		s.evaluation();
		
		new Topology(s, "e5", 10.0, 1.0).paint();;
	}

}
