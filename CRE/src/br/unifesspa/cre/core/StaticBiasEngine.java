package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

/**
 * 
 * @author hugo
 *
 */
public class StaticBiasEngine extends Engine{
	
	protected Double bias;
	
	protected List<Result> results;

	public StaticBiasEngine(CREEnv env, Double bias) {
		super(env);
		this.bias = bias;
	}

	public void run() {
		
		int simulations = this.env.getSimulationNumber();

		this.biasOffset = new Double[20];
		for (int i=0; i<this.biasOffset.length; i++)
			this.biasOffset[i] = this.bias;

		this.scenario.setBias(this.biasOffset);

		List<Result> results = new ArrayList<Result>();

		for (int i=0; i<simulations; i++) {
			
			this.scenario.evaluation();
			
			Result r = new Result();
			r.setBias( this.bias );
			r.setSumRate( this.scenario.getSumRate() );
			r.setMedianRate( this.scenario.getMedianRate() );
			r.setRequiredRate( this.scenario.getRequiredRate() );
			r.setUesServed( this.scenario.getUesServed() );
			r.setServingBSs( this.scenario.getServingBSs() );
			r.setEvaluation( r.getUesServed() + r.getServingBSs() );
			r.setScenario(this.scenario);
			results.add(r);
			
			this.scenario = new Scenario(this.env);
			this.scenario.setBias(this.biasOffset);
		}
		
		this.results = results;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
}