package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.ga.GA;
import br.unifesspa.cre.hetnet.Scenario;


/**
 * 
 * @author hugo
 *
 */
public class GAEngine extends Engine{

	public GAEngine(CREEnv env) {
		super(env);
	}

	public GAEngine(Scenario scenario) {
		super(scenario);
	}

	public void run() {
		GA ga = new GA(this.scenario);
		ga.evolve();
		this.result = ga.getBestIndividual().getResult();
	}
}