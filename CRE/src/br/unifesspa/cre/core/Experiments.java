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

	/**
	 * 
	 * @param env
	 * @param simulations
	 * @return
	 */
	public static List<Result> getExperiment02(CREEnv env, int simulations) {

		List<Result> meanResults = new ArrayList<Result>();
		List<Result> maxResults = new ArrayList<Result>();
		
		for (int i=0; i<CREEnv.alphas.length; i++) {
			List<Result> abResults = new ArrayList<Result>();
			for (int j=0; j<simulations; j++) {
				Scenario scenario = new Scenario(env);			
				Engine e = new Engine(CREEnv.alphas[i], CREEnv.betas[i], scenario);
				List<Result> rs = e.execStaticBias();
				maxResults.add( Collections.max(rs) );
				abResults.add( Util.getMean(rs) );
			}
			meanResults.add(Collections.max(abResults));
		}
		
		System.out.println(Collections.max(maxResults));

		return meanResults;
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
		List<Result> rPSO = new ArrayList<Result>();
		
		for (int i=0; i<CREEnv.alphas.length; i++) {
			Engine e = new Engine(CREEnv.alphas[i], CREEnv.betas[i], scenario);
			rNoBias.add(e.execNoBias());
			rStaticBias.add(Collections.max(e.execStaticBias()));
			//rGA.add(e.getGA());
			rPSO.add(e.getPSO());
		}
		
		results.put("NoBias", rNoBias);
		results.put("StaticBias", rStaticBias);
		//results.put("GA", rGA);
		results.put("PSO", rPSO);
		
		return results;
	}
}