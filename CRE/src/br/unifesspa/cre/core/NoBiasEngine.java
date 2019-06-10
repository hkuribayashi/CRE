package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

/**
 * 
 * @author hugo
 *
 */
public class NoBiasEngine extends Engine{

	public NoBiasEngine(CREEnv env) {
		super(env);
	}

	public NoBiasEngine(Scenario scenario) {
		super(scenario);
	}

	public void run() {

		this.biasOffset = new Double[20];
		for (int i=0; i<this.biasOffset.length; i++)
			this.biasOffset[i] = 0.0;

		this.scenario.setBias(this.biasOffset);
		this.scenario.evaluation();
		
		Result r = new Result();
		r.setBias(0.0);
		r.setSumRate(this.scenario.getSumRate());
		r.setMedianRate(this.scenario.getMedianRate());
		r.setRequiredRate(this.scenario.getRequiredRate());
		r.setUesServed(this.scenario.getUesServed());
		r.setServingBSs( this.scenario.getServingBSs() );
		r.setSumRBs( this.scenario.getSumRBs() );
		r.setEvaluation( r.getUesServed() + r.getSumRBs() );
		r.setScenario(this.scenario);
		
		this.result =  r;
	}
}