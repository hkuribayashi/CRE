package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Experiments {

	public static List<Result> getExperiment01(CREEnv env, int simulations) {

		List<Result> abResults = new ArrayList<Result>();
		for (int i=0; i<env.getAlphas().length; i++) {

			List<Result> results = new ArrayList<Result>();

			for (int j=0; j<simulations; j++) {

				Scenario scenario = new Scenario(env);			
				Engine e = new Engine(env.getAlphas()[i], env.getBetas()[i], scenario);
				results.add(e.execNoBias());

			}

			abResults.add( Util.getMean(results) );
		}

		return abResults; 
	}

	public static List<List<Result>> getExperiment02(CREEnv env, int simulations){
		List<List<Result>> results = new ArrayList<List<Result>>();
		for (int i=0; i<env.getAlphas().length; i++) {
			results.add( Experiments.getExperiment02(env, simulations, env.getAlphas()[i], env.getBetas()[i]) );
		}
		return results;
	}


	private static List<Result> getExperiment02(CREEnv env, int simulations, double alpha, double beta) {

		List<Result> meanResults = new ArrayList<Result>();

		Double initialBias = env.getInitialBias();
		Double stepBias = env.getBiasStep();
		double[] biasOffset = new double[env.getTotalBias()];
		for (int i=0; i<biasOffset.length; i++) {
			biasOffset[i] = initialBias + (stepBias * i);
		}

		Util.print(biasOffset);

		for (int i=0; i<biasOffset.length; i++) {
			List<Result> temp = new ArrayList<Result>();
			for (int j=0; j<simulations; j++) {
				Scenario scenario = new Scenario(env);			
				Engine e = new Engine(alpha, beta, scenario);
				temp.add( e.execUnifiedBias(biasOffset[i]) );
			}

			Double[] boxplotValues = Util.getBoxPlotData(temp);
			String path = env.getWorkingDirectory() + "e2-bias-"+i+"-alpha-"+alpha+"-beta-"+beta+".csv";
			Util.writeToCSV(path, boxplotValues, ""+biasOffset[i]);

			meanResults.add( Util.getMean(temp) );
		}

		return meanResults;
	}

	public static List<List<Result>> getExperiment03(CREEnv env, int simulations){
		List<List<Result>> results = new ArrayList<List<Result>>();
		for (int i=0; i<env.getAlphas().length; i++) {
			results.add( Experiments.getExperiment03(env, simulations, env.getAlphas()[i], env.getBetas()[i]) );
		}
		return results;
	}


	private static List<Result> getExperiment03(CREEnv env, int simulations, double alpha, double beta) {

		List<Result> meanResults = new ArrayList<Result>();

		Double initialBias = env.getInitialBias();
		Double stepBias = env.getBiasStep();
		double[] biasOffset = new double[env.getTotalBias()];
		for (int i=0; i<biasOffset.length; i++) {
			biasOffset[i] = initialBias + (stepBias * i);
		}

		Util.print(biasOffset);

		for (int i=0; i<biasOffset.length; i++) {
			List<Result> temp = new ArrayList<Result>();
			for (int j=0; j<simulations; j++) {
				Scenario scenario = new Scenario(env);			
				Engine e = new Engine(alpha, beta, scenario);
				temp.add( e.execUnifiedBiasEvolved(biasOffset[i]) );
			}

			Double[] boxplotValues = Util.getBoxPlotData(temp);
			String path = env.getWorkingDirectory() + "e3-bias-"+i+"-alpha-"+alpha+"-beta-"+beta+".csv";
			Util.writeToCSV(path, boxplotValues, ""+biasOffset[i]);

			meanResults.add( Util.getMean(temp) );
		}

		return meanResults;
	}

	public static HashMap<String, Result> getExperiment04(Scenario scenario, double alpha, double beta) {

		HashMap<String, Result> results = new HashMap<String, Result>();

		Engine e = new Engine(alpha, beta, scenario);

		results.put("UCB", e.execUnifiedBias(30.0));
		results.put("UCB2", e.execUnifiedBiasEvolved(30.0));

		return results;
	}

	/**
	 * CoPSO
	 * 
	 * @param scenario
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public static HashMap<String, List<Result>> getExperiment05(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e5-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			CoPSOEngine pso20  = new CoPSOEngine(alpha, beta, scenario.clone(), 20);
			Thread t20 = new Thread(pso20);

			CoPSOEngine pso40  = new CoPSOEngine(alpha, beta, scenario.clone(), 40);
			Thread t40 = new Thread(pso40);

			CoPSOEngine pso60  = new CoPSOEngine(alpha, beta, scenario.clone(), 60);
			Thread t60 = new Thread(pso60);

			CoPSOEngine pso80  = new CoPSOEngine(alpha, beta, scenario.clone(), 80);
			Thread t80 = new Thread(pso80);

			t20.start();
			t40.start();
			t60.start();
			t80.start();

			try {
				t20.join();
				t40.join();
				t60.join();
				t80.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			results.put("CoPSO-20", pso20.getResults());
			results.put("CoPSO-40", pso40.getResults());
			results.put("CoPSO-60", pso60.getResults());
			results.put("CoPSO-80", pso80.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}

	/**
	 * Stochastic PSO
	 * 
	 * @param scenario
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public static HashMap<String, List<Result>> getExperiment06(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e6-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			StochasticPSOEngine pso20  = new StochasticPSOEngine(alpha, beta, scenario.clone(), 20);
			Thread t20 = new Thread(pso20);

			StochasticPSOEngine pso40  = new StochasticPSOEngine(alpha, beta, scenario.clone(), 40);
			Thread t40 = new Thread(pso40);

			StochasticPSOEngine pso60  = new StochasticPSOEngine(alpha, beta, scenario.clone(), 60);
			Thread t60 = new Thread(pso60);

			StochasticPSOEngine pso80  = new StochasticPSOEngine(alpha, beta, scenario.clone(), 80);
			Thread t80 = new Thread(pso80);

			t20.start();
			t40.start();
			t60.start();
			t80.start();

			try {
				t20.join();
				t40.join();
				t60.join();
				t80.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			results.put("StochasticPSO-20", pso20.getResults());
			results.put("StochasticPSO-40", pso40.getResults());
			results.put("StochasticPSO-60", pso60.getResults());
			results.put("StochasticPSO-80", pso80.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}

	/**
	 * DecreaseIWPSO
	 * 
	 * @param scenario
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public static HashMap<String, List<Result>> getExperiment07(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e7-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			DecreaseIWPSOEngine pso20  = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 20);
			Thread t20 = new Thread(pso20);

			DecreaseIWPSOEngine pso40  = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 40);
			Thread t40 = new Thread(pso40);

			DecreaseIWPSOEngine pso60  = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 60);
			Thread t60 = new Thread(pso60);

			DecreaseIWPSOEngine pso80  = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 80);
			Thread t80 = new Thread(pso80);

			t20.start();
			t40.start();
			t60.start();
			t80.start();

			try {
				t20.join();
				t40.join();
				t60.join();
				t80.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			results.put("DecreaseIWPSO-20", pso20.getResults());
			results.put("DecreaseIWPSO-40", pso40.getResults());
			results.put("DecreaseIWPSO-60", pso60.getResults());
			results.put("DecreaseIWPSO-80", pso80.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}

	/**
	 * IncreaseIWPSO
	 * 
	 * @param scenario
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public static HashMap<String, List<Result>> getExperiment08(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e8-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			IncreaseIWPSOEngine pso20  = new IncreaseIWPSOEngine(alpha, beta, scenario.clone(), 20);
			Thread t20 = new Thread(pso20);

			IncreaseIWPSOEngine pso40  = new IncreaseIWPSOEngine(alpha, beta, scenario.clone(), 40);
			Thread t40 = new Thread(pso40);

			IncreaseIWPSOEngine pso60  = new IncreaseIWPSOEngine(alpha, beta, scenario.clone(), 60);
			Thread t60 = new Thread(pso60);

			IncreaseIWPSOEngine pso80  = new IncreaseIWPSOEngine(alpha, beta, scenario.clone(), 80);
			Thread t80 = new Thread(pso80);

			t20.start();
			t40.start();
			t60.start();
			t80.start();

			try {
				t20.join();
				t40.join();
				t60.join();
				t80.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			results.put("IncreaseIWPSO-20", pso20.getResults());
			results.put("IncreaseIWPSO-40", pso40.getResults());
			results.put("IncreaseIWPSO-60", pso60.getResults());
			results.put("IncreaseIWPSO-80", pso80.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}

	/**
	 * StaticIWPSO
	 * 
	 * @param scenario
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public static HashMap<String, List<Result>> getExperiment09(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e9-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			StaticIWPSOEngine pso20  = new StaticIWPSOEngine(alpha, beta, scenario.clone(), 20);
			Thread t20 = new Thread(pso20);

			StaticIWPSOEngine pso40  = new StaticIWPSOEngine(alpha, beta, scenario.clone(), 40);
			Thread t40 = new Thread(pso40);

			StaticIWPSOEngine pso60  = new StaticIWPSOEngine(alpha, beta, scenario.clone(), 60);
			Thread t60 = new Thread(pso60);

			StaticIWPSOEngine pso80  = new StaticIWPSOEngine(alpha, beta, scenario.clone(), 80);
			Thread t80 = new Thread(pso80);

			t20.start();
			t40.start();
			t60.start();
			t80.start();

			try {
				t20.join();
				t40.join();
				t60.join();
				t80.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			results.put("StaticIWPSO-20", pso20.getResults());
			results.put("StaticIWPSO-40", pso40.getResults());
			results.put("StaticIWPSO-60", pso60.getResults());
			results.put("StaticIWPSO-80", pso80.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}	
	
	
	/**
	 * StaticIWPSO
	 * 
	 * @param scenario
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public static HashMap<String, List<Result>> getExperiment10(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e10-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			VsPSOEngine pso20  = new VsPSOEngine(alpha, beta, scenario.clone(), 20);
			Thread t20 = new Thread(pso20);

			VsPSOEngine pso40  = new VsPSOEngine(alpha, beta, scenario.clone(), 40);
			Thread t40 = new Thread(pso40);

			VsPSOEngine pso60  = new VsPSOEngine(alpha, beta, scenario.clone(), 60);
			Thread t60 = new Thread(pso60);

			VsPSOEngine pso80  = new VsPSOEngine(alpha, beta, scenario.clone(), 80);
			Thread t80 = new Thread(pso80);

			t20.start();
			t40.start();
			t60.start();
			t80.start();

			try {
				t20.join();
				t40.join();
				t60.join();
				t80.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			results.put("VsPSO-20", pso20.getResults());
			results.put("VsPSO-40", pso40.getResults());
			results.put("VsPSO-60", pso60.getResults());
			results.put("VsPSO-80", pso80.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}
}