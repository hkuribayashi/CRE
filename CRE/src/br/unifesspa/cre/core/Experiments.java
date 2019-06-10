package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Experiments {

	/**
	 * 
	 * @param env
	 * @param simulations
	 * @return
	 */
	public static List<Result> getExperiment01(CREEnv env) {

		int simulations = env.getSimulationNumber();

		List<Result> noBiasResults = new ArrayList<Result>();
		List<Result> results = new ArrayList<Result>();

		List<Thread> threadList = new ArrayList<Thread>();
		List<NoBiasEngine> engineList = new ArrayList<NoBiasEngine>();

		for (int j=0; j<simulations; j++) {
			Scenario scenario = new Scenario(env);			
			NoBiasEngine eNB = new NoBiasEngine(scenario);
			engineList.add(eNB);
			Thread tNB = new Thread(engineList.get(engineList.size()-1));
			tNB.start();
			threadList.add(tNB);
		}

		for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (NoBiasEngine n : engineList) {
			results.add(n.getResult());
		}

		noBiasResults.add( Util.getMean(results) );

		return noBiasResults; 
	}

	public static List<Result> getExperiment02(CREEnv env) {
		// This storages the results of all Simulations
		List<Result> allResults = new ArrayList<Result>();

		//Auxiliary vars for thread functioning
		List<Thread> threadList = new ArrayList<Thread>();
		List<StaticBiasEngine> engineList = new ArrayList<StaticBiasEngine>();
		
		int totalBias = env.getTotalBias();
		double biasStep = env.getBiasStep();
		double initialBias = env.getInitialBias();
		
		Double[] biasOffset = new Double[totalBias];
		for (int i=0; i<totalBias; i++) {
			biasOffset[i] = initialBias + (i * biasStep); 
		}
		
		//For each bias value
		for (int j=0; j<totalBias; j++) {

			//Analyze this scenario over all bias values
			StaticBiasEngine eSB = new StaticBiasEngine(env, biasOffset[j]);
			engineList.add(eSB);

			//Starts the thread
			Thread tSB = new Thread(engineList.get(engineList.size()-1));
			tSB.start();
			threadList.add(tSB);
		}

		for (Thread t: threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (StaticBiasEngine s: engineList) {
			allResults.add( Util.getMean(s.getResults()) );
		}

		return allResults;
	}

	/**
	 * 
	 * @param scenario
	 * @return
	 */
	public static HashMap<String, List<Result>> getExperiment03(Scenario scenario) {
		HashMap<String, List<Result>> results = new HashMap<String, List<Result>>();

		List<Result> rNoBias = new ArrayList<Result>();
		List<Result> rStaticBias = new ArrayList<Result>();


		NoBiasEngine eNB = new NoBiasEngine(scenario);
		Thread tNB = new Thread(eNB);
		tNB.start();

		StaticBiasEngine eSB = new StaticBiasEngine(scenario.getEnv(), 0.0);
		Thread tSB = new Thread(eSB);
		tSB.start();

		try {
			tNB.join();
			tSB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rNoBias.add(eNB.getResult());
		rStaticBias.add(Collections.max(eSB.getResults()));


		results.put("NoBias", rNoBias);
		results.put("StaticBias", rStaticBias);

		return results;
	}

	public static List<Result> getExperiment04(Scenario scenario) {
		List<Result> results = new ArrayList<Result>();



		GAEngine eGA = new GAEngine(scenario);
		Thread tGA = new Thread(eGA);
		tGA.start();

		try {
			tGA.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		results.add(eGA.getResult());


		return results;
	}

	public static List<Result> getExperiment05(Scenario scenario) {
		List<Result> results = new ArrayList<Result>();


		PSOEngine ePSO = new PSOEngine(scenario);
		Thread tPSO = new Thread(ePSO);
		tPSO.start();

		try {
			tPSO.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		results.add(ePSO.getResult());


		return results;
	}
}