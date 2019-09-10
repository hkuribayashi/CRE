package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.pso.DecreaseIWPSO;

public class DecreaseIWPSOEngine extends PSOEngine{

	public DecreaseIWPSOEngine(double alpha, double beta, CREEnv env, Integer swarmSize) {
		super(alpha, beta, env, swarmSize);
	}
	
	public DecreaseIWPSOEngine(double alpha, double beta, Scenario scenario, Integer swarmSize) {
		super(alpha, beta, scenario, swarmSize);
	}

	@Override
	public void run() {
		List<Result> results = new ArrayList<Result>();
		double targetSolution = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		DecreaseIWPSO[] pso = new DecreaseIWPSO[this.env.getSimulations()];
		for (int i=0; i<pso.length; i++) {
			pso[i] = new DecreaseIWPSO(this.alpha, this.beta, this.scenario.clone(), this.env.getPsoSteps(), this.swarmSize, targetSolution);
		}

		Thread[] psoThreads = new Thread[this.env.getSimulations()];
		for (int i=0; i<psoThreads.length; i++) {
			psoThreads[i] = new Thread(pso[i]);
		}

		for (int i=0; i<psoThreads.length; i++) {
			psoThreads[i].start();
		}

		try {
			for (int i=0; i<psoThreads.length; i++)
				psoThreads[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<psoThreads.length; i++) {
			results.add( pso[i].getResult() );
			
		}
		this.setResults(results);
	}


}
