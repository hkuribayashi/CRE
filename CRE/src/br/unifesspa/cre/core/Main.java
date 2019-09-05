package br.unifesspa.cre.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.pso.CoPSO;
import br.unifesspa.cre.util.Util;

public class Main {

	public static void main(String[] args) {

		String path = "/Users/hugo/Desktop/CRE/CREV1/";
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
		env.set(Param.workingDirectory, path); // Working Directory
		env.set(Param.simulations, 100.0);	   // Number of Simulations

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
		
		Scenario s1 = new Scenario(env);
		
		int simulations = 50;
		
		CoPSO[] pso = new CoPSO[simulations];
		for (int i=0; i<pso.length; i++) {
			pso[i] = new CoPSO(10.0, 1.0, s1.clone(), 100.0, 20, 3022.0);
		}
		
		Thread[] psoThreads = new Thread[simulations];
		for (int i=0; i<psoThreads.length; i++) {
			psoThreads[i] = new Thread(pso[i]);
			psoThreads[i].setName("Thread-"+i);
		}
		
		for (int i=0; i<psoThreads.length; i++) {
			psoThreads[i].start();
			System.out.println("Starting: "+psoThreads[i].getName());
		}
		
			try {
				for (int i=0; i<psoThreads.length; i++) {
					psoThreads[i].join();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		/*
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
		
		*/

		//Experiment 03

		/* 
		System.out.println("Experiment 03:");

		DAO<List<List<Result>>> daoE3 = new DAO<List<List<Result>>>();
		String fileE3 = path + "experiment3.data";
		List<List<Result>> re3;
		if (!daoE1.verifyPath(fileE3)) {
			re3 = Experiments.getExperiment03(env, env.getSimulations());
			daoE3.save(re3, fileE3);
			
			for (List<Result> list : re3) {
				double alpha = list.get(0).getAlpha();
				double beta = list.get(0).getBeta();

				Double[] boxplotValues = Util.getBoxPlotData(list);
				String file = env.getWorkingDirectory() + "e3-alpha-"+alpha+"-beta-"+beta+".csv";
				Util.writeToCSV(file, boxplotValues, "");
			}
			
		}else  re3 = daoE3.restore(fileE3);
		
		*/
		
		//System.out.println();
		
		//Experiment 04
		
		//System.out.println("Experiment 04:");

		/*
		for (List<Result> list : re2) {

			double alpha = list.get(0).getAlpha();
			double beta = list.get(0).getBeta();

			Scenario scenario = Collections.max(list).getScenario();
			scenario.setEnv(env);

			HashMap<String, Result> results = Experiments.getExperiment04(scenario, alpha, beta);

			System.out.println("Alpha="+alpha+" Beta="+beta);

			System.out.println("UCB");
			System.out.println( results.get("UCB") );
			System.out.println();

			System.out.println("UCB2");
			System.out.println( results.get("UCB2") );
			System.out.println(); 

		}
		*/

		//Experiment 05

		/*
		
		System.out.println("Experiment 05: CoPSO");
		
		for (List<Result> list : re2) {
			
			Experiments ex = new Experiments();
			
			double alpha = list.get(0).getAlpha();
			double beta = list.get(0).getBeta();

			Scenario scenario = Collections.max(list).getScenario();
			scenario.setEnv(env);

			HashMap<String, Result> results = ex.getExperiment05(scenario, alpha, beta);
			
			System.out.println("Alpha="+alpha+" Beta="+beta);
						
			System.out.println("CoPSO-20");
			System.out.println( results.get("CoPSO-20-max") );
			System.out.println( results.get("CoPSO-20-mean") );
			System.out.println();
			
			System.out.println("CoPSO-40");
			System.out.println( results.get("CoPSO-40-max") );
			System.out.println( results.get("CoPSO-40-mean") );
			System.out.println();
			
			System.out.println("CoPSO-60");
			System.out.println( results.get("CoPSO-60-max") );
			System.out.println( results.get("CoPSO-60-mean") );
			System.out.println();
		}
		
		*/
	
	}
}