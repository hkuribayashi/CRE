package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.pso.CoPSO;
import br.unifesspa.cre.util.Util;

public class CoPSOEngine extends Engine implements Runnable{

	private CoPSO pso;

	private Integer swarmSize;
	
	private Result max;
	
	private Result mean;

	public CoPSOEngine(Double alpha, Double beta, CREEnv env) {
		super(alpha, beta, env);
	}
	
	public CoPSOEngine(Double alpha, Double beta, CREEnv env, Integer swarmSize) {
		super(alpha, beta, env);
		this.setSwarmSize(swarmSize);
	}

	public CoPSOEngine(Double alpha, Double beta, Scenario scenario) {
		super(alpha, beta, scenario);
	}
	
	public CoPSOEngine(Double alpha, Double beta, Scenario scenario, Integer swarmSize) {
		super(alpha, beta, scenario);
		this.setSwarmSize(swarmSize);
	}

	@Override
	public void run() {
		int counter = 0;
		List<Result> results = new ArrayList<Result>();
		double targetSolution = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		while(counter < this.env.getSimulations()) {
			this.pso = new CoPSO(this.alpha, this.beta, this.scenario, 100.0, this.swarmSize, targetSolution);
			results.add(this.pso.search());
			counter++;
			System.out.println(counter);
		}
		this.setMax( Collections.max(results) );
		this.setMean( Util.getMean(results) );
		
	}

	public CoPSO getPso() {
		return pso;
	}

	public void setPso(CoPSO pso) {
		this.pso = pso;
	}

	public Integer getSwarmSize() {
		return swarmSize;
	}

	public void setSwarmSize(Integer swarmSize) {
		this.swarmSize = swarmSize;
	}

	public Result getMax() {
		return max;
	}

	public void setMax(Result max) {
		this.max = max;
	}

	public Result getMean() {
		return mean;
	}

	public void setMean(Result mean) {
		this.mean = mean;
	}
}
