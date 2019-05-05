package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

public class Experiments {

	/**
	 * 
	 * @param env
	 * @param simulations
	 * @return
	 */
	public static List<Result> getExperiment01(CREEnv env, int simulations) {

		List<Result> abResults = new ArrayList<Result>();
		for (int i=0; i<CREEnv.alphas.length; i++) {

			List<Result> results = new ArrayList<Result>();

			for (int j=0; j<simulations; j++) {

				Scenario scenario = new Scenario(env);			
				Engine e = new Engine(CREEnv.alphas[i], CREEnv.betas[i], scenario);
				results.add(e.execNoBias());

			}

			abResults.add( Collections.max(results) );
		}

		return abResults; 
	}

	/**
	 * 
	 * @param env
	 * @param simulations
	 * @return
	 */
	public static List<Result> getExperiment02(CREEnv env, int simulations) {

		List<Result> results = new ArrayList<Result>();
		for (int i=0; i<CREEnv.alphas.length; i++) {
			List<Result> abResults = new ArrayList<Result>();
			for (int j=0; j<simulations; j++) {
				Scenario scenario = new Scenario(env);			
				Engine e = new Engine(CREEnv.alphas[i], CREEnv.betas[i], scenario);
				List<Result> rs = e.execStaticBias();
				abResults.add( Collections.max(rs) );
			}
			results.add(Collections.max(abResults));
		}

		return results;
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
		List<Result> rGA = new ArrayList<Result>();
		
		for (int i=0; i<CREEnv.alphas.length; i++) {
			Engine e = new Engine(CREEnv.alphas[i], CREEnv.betas[i], scenario);
			rNoBias.add(e.execNoBias());
			rStaticBias.addAll(e.execStaticBias());
			rGA.add(e.getGA());
		}
		
		results.put("NoBias", rNoBias);
		results.put("StaticBias", rStaticBias);
		results.put("GA", rGA);
		
		return results;
	}

}