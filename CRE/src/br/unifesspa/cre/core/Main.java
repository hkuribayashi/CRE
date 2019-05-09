package br.unifesspa.cre.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Main {

	public static void main(String[] args) {
		
		int simulations = 1000;
		
		String path = "/Users/hugo/Desktop/CRE/";
		if (args.length != 0)
			path = args[0];
		
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
		
		//Setting Parameters to Phase 1: Static Bias		
		env.set(Param.totalBias, 15);
		env.set(Param.biasStep, 5.0);
		env.set(Param.initialBias, 0.0);
		
		//Setting GA Parameters
		env.set(Param.initialCrossoverProbability, 0.9);
		env.set(Param.finalCrossoverProbability, 0.5);
		env.set(Param.initialMutationProbability, 0.5);
		env.set(Param.finalMutationProbability, 0.99);
		env.set(Param.populationSize, (env.getLambdaSmall()*env.getArea()));
		env.set(Param.generationSize, 100);
		env.set(Param.kElitism, 2);
		
		//Setting PSO Parameters
		env.set(Param.psoGroupSize, 100);
		env.set(Param.psoSteps, 100);
		
		DAO<List<Result>> dao = new DAO<List<Result>>();
		
		String fileE1 = path + "experiment1.data";
		List<Result> re1;
		if (!dao.verifyPath(fileE1)) {
			re1 = Experiments.getExperiment01(env, simulations);
			dao.save(re1, fileE1);
		}else re1 = dao.restore(fileE1);
		
		Util.print(re1);
		
		String fileE2 = path + "experiment2.data";
		List<Result> re2;
		if (!dao.verifyPath(fileE2)) {
			re2 = Experiments.getExperiment02(env, simulations);
			dao.save(re2, fileE2);
		}else re2 = dao.restore(fileE2);
		
		Util.print(re2);
		
		
		HashMap<String, Double> map = Util.getChromossomeRange(re2);
		env.set(Param.initialGeneRange, map.get("minBias"));
		env.set(Param.finalGeneRange, map.get("maxBias"));
		
		Scenario scenario = Collections.max(re2).getScenario();
		scenario.setEnv(env);
		
		HashMap<String, List<Result>> results = Experiments.getExperiment03(scenario);
		
		Util.print( results.get("NoBias") );
		Util.print( results.get("StaticBias") );
		Util.print( results.get("GA") );
		Util.print( results.get("PSO") );
		
		
	}
}