package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.pso.PSO;


/**
 * 
 * @author hugo
 *
 */
public class PSOEngine extends Engine{

	public PSOEngine(CREEnv env) {
		super(env);
	}

	public PSOEngine(Scenario scenario) {
		super(scenario);
	}

	public void run() {		
		double target = (this.scenario.getUe().size() + this.scenario.getRequiredRate());
		PSO pso = new PSO(this.scenario, target, this.scenario.getEnv().getPsoGroupSize(), this.scenario.getEnv().getPsoSteps());		
		this.result = pso.search(); 
	}
}