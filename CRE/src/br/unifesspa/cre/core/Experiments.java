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

	public static HashMap<String, List<Result>> getExperiment05(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e5-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			CoPSOEngine pso;
			
			System.out.println("Alpha="+alpha+",Beta="+beta+",CoPSO-20");
			pso = new CoPSOEngine(alpha, beta, scenario.clone(), 20);
			pso.run();
		    results.put("CoPSO-20", pso.getResults());

		    System.out.println("Alpha="+alpha+",Beta="+beta+",CoPSO-40");
		    pso = new CoPSOEngine(alpha, beta, scenario.clone(), 40);
			pso.run();
		    results.put("CoPSO-40", pso.getResults());
		    
		    System.out.println("Alpha="+alpha+",Beta="+beta+",CoPSO-60");
		    pso = new CoPSOEngine(alpha, beta, scenario.clone(), 60);
			pso.run();
		    results.put("CoPSO-60", pso.getResults());
		    
		    System.out.println("Alpha="+alpha+",Beta="+beta+",CoPSO-80");
		    pso = new CoPSOEngine(alpha, beta, scenario.clone(), 80);
			pso.run();
		    results.put("CoPSO-80", pso.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}

	public static HashMap<String, List<Result>> getExperiment06(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e6-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			DecreaseIWPSOEngine pso;
			
			System.out.println("Alpha="+alpha+",Beta="+beta+",DecreaseIWPSO-20");
			pso = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 20);
			pso.run();
		    results.put("DecreaseIWPSO-20", pso.getResults());

		    System.out.println("Alpha="+alpha+",Beta="+beta+",DecreaseIWPSO-40");
		    pso = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 40);
			pso.run();
		    results.put("DecreaseIWPSO-40", pso.getResults());
		    
		    System.out.println("Alpha="+alpha+",Beta="+beta+",DecreaseIWPSO-60");
		    pso = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 60);
			pso.run();
		    results.put("DecreaseIWPSO-60", pso.getResults());
		    
		    System.out.println("Alpha="+alpha+",Beta="+beta+",DecreaseIWPSO-80");
		    pso = new DecreaseIWPSOEngine(alpha, beta, scenario.clone(), 80);
			pso.run();
		    results.put("DecreaseIWPSO-80", pso.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}
	
	public static HashMap<String, List<Result>> getExperiment07(Scenario scenario, double alpha, double beta) {
		HashMap<String, List<Result>> results;
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		String path = scenario.getEnv().getWorkingDirectory()+"e7-alpha-"+alpha+"-beta-"+beta+".data";
		if(!dao.verifyPath(path)) {

			results = new HashMap<String, List<Result>>();

			VsPSOEngine pso;
			
			System.out.println("Alpha="+alpha+",Beta="+beta+",VsPSO-20");
			pso = new VsPSOEngine(alpha, beta, scenario.clone(), 20);
			pso.run();
		    results.put("VsPSO-20", pso.getResults());

		    System.out.println("Alpha="+alpha+",Beta="+beta+",VsPSO-40");
		    pso = new VsPSOEngine(alpha, beta, scenario.clone(), 40);
			pso.run();
		    results.put("VsPSO-40", pso.getResults());
		    
		    System.out.println("Alpha="+alpha+",Beta="+beta+",VsPSO-60");
		    pso = new VsPSOEngine(alpha, beta, scenario.clone(), 60);
			pso.run();
		    results.put("VsPSO-60", pso.getResults());
		    
		    System.out.println("Alpha="+alpha+",Beta="+beta+",VsPSO-80");
		    pso = new VsPSOEngine(alpha, beta, scenario.clone(), 80);
			pso.run();
		    results.put("VsPSO-80", pso.getResults());

			dao.save(results, path);

		}else  results = dao.restore(path);

		return results;
	}
}