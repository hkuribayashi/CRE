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

		String psoOption = "Default";
		String path = "/Users/hugo/Desktop/CRE/CRE/";
		if (args.length != 0) {
			path = args[0];
			psoOption = args[1];
		}

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
		env.set(Param.workingDirectory, path); // Working Directory
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

		//Experiment 01

		System.out.println("Experiment 01:");

		DAO<List<Result>> daoE1 = new DAO<List<Result>>();
		String fileE1 = path + "experiment1.data";
		List<Result> re1;
		if (!daoE1.verifyPath(fileE1)) {
			re1 = Experiments.getExperiment01(env, env.getSimulations());
			daoE1.save(re1, fileE1);
		}else re1 = daoE1.restore(fileE1);

		Util.print(re1);

		//Experiment 02

		System.out.println("Experiment 02:");

		DAO<List<List<Result>>> daoE2 = new DAO<List<List<Result>>>();
		String fileE2 = path + "experiment2.data";
		List<List<Result>> re2;
		if (!daoE1.verifyPath(fileE2)) {
			re2 = Experiments.getExperiment02(env, env.getSimulations());
			daoE2.save(re2, fileE2);

			for (List<Result> list : re2) {
				double alpha = list.get(0).getAlpha();
				double beta = list.get(0).getBeta();

				Double[] boxplotValues = Util.getBoxPlotData(list);
				String file = env.getWorkingDirectory() + "e2-alpha-"+alpha+"-beta-"+beta+".csv";
				Util.writeToCSV(file, boxplotValues, "");
			}

		}else re2 = daoE2.restore(fileE2);

		System.out.println();

		switch(psoOption) {
		case "CoPSO": Main.CoPSO(re2); break;
		case "DecreasePSO": Main.DecreasePSO(re2); break;
		case "VsPSO": Main.VsPSO(re2); break;
		default: System.out.println("Usage: java -jar CRE.jar <path> <PSO-Option>"); break;
		}
	}

	public static void CoPSO(List<List<Result>> re2) {

		System.out.println("Experiment 05: CoPSO");

		for (List<Result> list : re2) {

			double alpha = list.get(0).getAlpha();
			double beta = list.get(0).getBeta();

			Scenario scenario = Collections.max(list).getScenario();

			HashMap<String, List<Result>> re5 = Experiments.getExperiment05(scenario, alpha, beta);

			System.out.println("ALPHA = "+alpha+" BETA = "+beta);
			System.out.println("CoPSO-20");
			for (Result l : re5.get("CoPSO-20")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("CoPSO-20")));
			System.out.println("Max: "+Collections.max(re5.get("CoPSO-20")));
			System.out.println();

			System.out.println("CoPSO-40");
			for (Result l : re5.get("CoPSO-40")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("CoPSO-40")));
			System.out.println("Max: "+Collections.max(re5.get("CoPSO-40")));
			System.out.println();

			System.out.println("CoPSO-60");
			for (Result l : re5.get("CoPSO-60")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("CoPSO-60")));
			System.out.println("Max: "+Collections.max(re5.get("CoPSO-60")));
			System.out.println();

			System.out.println("CoPSO-80");
			for (Result l : re5.get("CoPSO-80")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("CoPSO-80")));
			System.out.println("Max: "+Collections.max(re5.get("CoPSO-80")));
			System.out.println();
		}
		System.out.println();		
	}

	public static void DecreasePSO(List<List<Result>> re2) {
		System.out.println("Experiment 06: DecreaseIWPSO");
		for (List<Result> list : re2) {

			double alpha = list.get(0).getAlpha();
			double beta = list.get(0).getBeta();

			Scenario scenario = Collections.max(list).getScenario();

			HashMap<String, List<Result>> re5 = Experiments.getExperiment06(scenario, alpha, beta);

			System.out.println("ALPHA = "+alpha+" BETA = "+beta);
			System.out.println("DecreaseIWPSO-20");
			for (Result l : re5.get("DecreaseIWPSO-20")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("DecreaseIWPSO-20")));
			System.out.println("Max: "+Collections.max(re5.get("DecreaseIWPSO-20")));
			System.out.println();

			System.out.println("DecreaseIWPSO-40");
			for (Result l : re5.get("DecreaseIWPSO-40")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("DecreaseIWPSO-40")));
			System.out.println("Max: "+Collections.max(re5.get("DecreaseIWPSO-40")));
			System.out.println();

			System.out.println("DecreaseIWPSO-60");
			for (Result l : re5.get("DecreaseIWPSO-60")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("DecreaseIWPSO-60")));
			System.out.println("Max: "+Collections.max(re5.get("DecreaseIWPSO-60")));
			System.out.println();

			System.out.println("DecreaseIWPSO-80");
			for (Result l : re5.get("DecreaseIWPSO-80")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("DecreaseIWPSO-80")));
			System.out.println("Max: "+Collections.max(re5.get("DecreaseIWPSO-80")));
			System.out.println();
		}
		System.out.println();
	}

	public static void VsPSO(List<List<Result>> re2) {
		System.out.println("Experiment 06: VsPSO");
		for (List<Result> list : re2) {

			double alpha = list.get(0).getAlpha();
			double beta = list.get(0).getBeta();

			Scenario scenario = Collections.max(list).getScenario();

			HashMap<String, List<Result>> re5 = Experiments.getExperiment07(scenario, alpha, beta);

			System.out.println("ALPHA = "+alpha+" BETA = "+beta);
			System.out.println("VsPSO-20");
			for (Result l : re5.get("VsPSO-20")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("VsPSO-20")));
			System.out.println("Max: "+Collections.max(re5.get("VsPSO-20")));
			System.out.println();

			System.out.println("VsPSO-40");
			for (Result l : re5.get("VsPSO-40")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("VsPSO-40")));
			System.out.println("Max: "+Collections.max(re5.get("VsPSO-40")));
			System.out.println();

			System.out.println("VsPSO-60");
			for (Result l : re5.get("VsPSO-60")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("VsPSO-60")));
			System.out.println("Max: "+Collections.max(re5.get("VsPSO-60")));
			System.out.println();

			System.out.println("VsPSO-80");
			for (Result l : re5.get("VsPSO-80")) {
				System.out.println(l);
			}

			System.out.println();
			System.out.println("Mean: "+Util.getMean(re5.get("VsPSO-80")));
			System.out.println("Max: "+Collections.max(re5.get("VsPSO-80")));
			System.out.println();
		}
		System.out.println();		
	}
}