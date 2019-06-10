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
	
		
		
		//Configuration Variable
		CREEnv env = new CREEnv();
		
		//Setting General Simulations Parameters
		env.set(Param.simulationNumber, 100);
		
		//Setting Model System Parameters
		env.set(Param.area, 1000000.0); 	   // 1 km^2
		env.set(Param.lambdaFemto, 0.00002);   // 0.00002 Femto/m^2 = 20 Femtos  
		env.set(Param.lambdaUser, 0.0002);     // 0.0003 Users/m^2 = 300 Users 
		env.set(Param.lambdaMacro, 0.000002);  // 0.000002 Macros/m^2 = 2 Macros
		env.set(Param.powerMacro, 46.0);	   // dBm
		env.set(Param.powerSmall, 30.0);	   // dBm
		env.set(Param.noisePower, -174.0);	   // dBm/Hz
		env.set(Param.gainMacro, 5.0);		   // dBi
		env.set(Param.gainSmall, 0.0);		   // dBi
		env.set(Param.nSubcarriers, 12);	   // 12 Sub-carriers per Resource Block
		env.set(Param.nOFDMSymbols, 14);	   // 14 OFDM Symbols per subframe
		env.set(Param.subframeDuration, 1.0);  // 1 ms
		
		//Setting Parameters to Phase 1: Static Bias		
		env.set(Param.totalBias, 40);
		env.set(Param.biasStep, 5.0);
		env.set(Param.initialBias, -40.0);
		
		//Setting GA Parameters
		env.set(Param.initialGeneRange, 25.0);
		env.set(Param.finalGeneRange, 60.0);
		env.set(Param.initialCrossoverProbability, 0.95);
		env.set(Param.finalCrossoverProbability, 0.65);
		env.set(Param.initialMutationProbability, 0.5);
		env.set(Param.finalMutationProbability, 0.99);
		env.set(Param.populationSize, 100);
		env.set(Param.generationSize, 150);
		env.set(Param.kElitism, 2);
		
		//Setting PSO Parameters
		env.set(Param.psoGroupSize, 2000);
		env.set(Param.psoSteps, 200);
		
		String path = "/Users/hugo/Desktop/CRE/";
		
		if (args.length != 0) {
			path = args[0];
			env.set(Param.simulationNumber, Integer.parseInt(args[1]));
		}else {
			String msgError = "Usage: java -jar CRE.java <Working Directory> <numberOfSimulations>";
			System.out.println(msgError);
			System.exit(0);
		}
		
		DAO<List<Result>> dao = new DAO<List<Result>>();
		
		String fileE1 = path + "experiment1.data";
		List<Result> re1;
		if (!dao.verifyPath(fileE1)) {
			re1 = Experiments.getExperiment01(env);
			dao.save(re1, fileE1);
		}else re1 = dao.restore(fileE1);
		
		System.out.println("Experiment 1 Results");
		Util.print(re1);
		System.out.println();
		
		String fileE2 = path + "experiment2.data";
		List<Result> re2;
		if (!dao.verifyPath(fileE2)) {
			re2 = Experiments.getExperiment02(env);
			dao.save(re2, fileE2);
		}else re2 = dao.restore(fileE2);
		
		System.out.println("Experiment 2 Results");
		Util.print(re2);
		System.out.println();
		
		/*
		Scenario scenario = Collections.max(re2).getScenario();
		
		System.out.println("Experiment 3 Results");
		HashMap<String, List<Result>> re3 = Experiments.getExperiment03(scenario);
		
		System.out.println("No Bias");
		Util.print( re3.get("NoBias") );
		
		System.out.println("Static Bias");
		Util.print( re3.get("StaticBias") );
		
		System.out.println();
		
		//List<Result> re4 = Experiments.getExperiment04(scenario);
		//Util.print(re4);
		
		List<Result> re5 = Experiments.getExperiment05(scenario);
		Util.print(re5);*/
	}
}