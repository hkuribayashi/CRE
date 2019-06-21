package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Experiments {

	public static List<Result> getExperiment01(CREEnv env, int simulations) {

		List<Result> abResults = new ArrayList<Result>();
		for (int i=0; i<CREEnv.alphas.length; i++) {

			List<Result> results = new ArrayList<Result>();

			for (int j=0; j<simulations; j++) {

				Scenario scenario = new Scenario(env);			
				Engine e = new Engine(CREEnv.alphas[i], CREEnv.betas[i], scenario);
				results.add(e.execNoBias());

			}

			abResults.add( Util.getMean(results) );
		}

		return abResults; 
	}
	
	public static List<List<Result>> getExperiment02(CREEnv env, int simulations){
		List<List<Result>> results = new ArrayList<List<Result>>();
		for (int i=0; i<CREEnv.getAlphas().length; i++) {
			results.add( Experiments.getExperiment02(env, simulations, CREEnv.alphas[i], CREEnv.betas[i]) );
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
			String path = env.getWorkingDirectory() + "bias-"+i+"-alpha-"+alpha+"-beta-"+beta+".csv";
			Util.writeToCSV(path, boxplotValues, ""+biasOffset[i]);
			
			meanResults.add( Util.getMean(temp) );
		}
		
		return meanResults;
	}
	
	public static HashMap<String, Result> getExperiment03(Scenario scenario, double alpha, double beta) {
		List<Double> solutions = new ArrayList<Double>();
		HashMap<String, Result> results = new HashMap<String, Result>();
		
		Engine e = new Engine(alpha, beta, scenario);
		
		results.put("UCB", e.execUnifiedBias(30.0));
		
		int i = 0;
		while (i < 10) {
			Result r = e.getPSO();
			Double[] solution = r.getSolution();
			solutions.addAll(Arrays.asList(solution));
			results.put("PSO"+i, r);
			i++;
		}
		
		Double[] finalSolution = new Double[solutions.size()];
		finalSolution = solutions.toArray(finalSolution);
		
		String file = scenario.getEnv().getWorkingDirectory() + "solution-alpha-"+alpha+"-beta-"+beta+".csv";
		Util.writeToCSV(file, finalSolution, "Solution-alpha-"+alpha+"-beta-"+beta);

		return results;
	}
	
}