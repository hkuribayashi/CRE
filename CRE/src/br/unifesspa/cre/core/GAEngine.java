package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.ga.GA;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

public class GAEngine extends Engine{

	public GAEngine(double alpha, double beta, CREEnv env) {
		super(alpha, beta, env);
	}
	
	public GAEngine(double alpha, double beta, Scenario scenario) {
		super(alpha, beta, scenario);
	}

	public Result getGA() {
		GA ga = new GA(this.alpha, this.beta, this.scenario);
		ga.evolve();
		return ga.getBestIndividual().getResult();
	}
}
