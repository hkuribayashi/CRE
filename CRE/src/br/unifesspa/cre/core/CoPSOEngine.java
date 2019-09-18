package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.pso.CoPSO;

public class CoPSOEngine extends PSOEngine{


	public CoPSOEngine(Double alpha, Double beta, CREEnv env, Integer swarmSize) {
		super(alpha, beta, env, swarmSize);
	}

	public CoPSOEngine(Double alpha, Double beta, Scenario scenario, Integer swarmSize) {
		super(alpha, beta, scenario, swarmSize);
	}

	public void run() {
		List<Result> results = new ArrayList<Result>();
		double targetSolution = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		
		for (int i=0; i<this.env.getSimulations(); i++) {
			CoPSO pso = new CoPSO(this.alpha, this.beta, this.scenario.clone(), this.env.getPsoSteps(), this.swarmSize, targetSolution);
			pso.search();
			results.add( pso.getResult() );
		}

		this.setResults(results);
	}
}