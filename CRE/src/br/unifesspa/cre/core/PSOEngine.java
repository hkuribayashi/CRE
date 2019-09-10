package br.unifesspa.cre.core;

import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

public abstract class PSOEngine extends Engine implements Runnable{
	
	protected Integer swarmSize;
	
	protected List<Result> results;

	public PSOEngine(double alpha, double beta, CREEnv env, Integer swarmSize) {
		super(alpha, beta, env);
		this.swarmSize = swarmSize;
	}
	
	public PSOEngine(double alpha, double beta, Scenario scenario, Integer swarmSize) {
		super(alpha, beta, scenario);
		this.swarmSize = swarmSize;
	}

	public Integer getSwarmSize() {
		return swarmSize;
	}

	public void setSwarmSize(Integer swarmSize) {
		this.swarmSize = swarmSize;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
}