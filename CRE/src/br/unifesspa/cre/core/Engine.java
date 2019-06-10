package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

public abstract class Engine implements Runnable {

	protected Double[] biasOffset;

	protected CREEnv env;

	protected Scenario scenario;
	
	protected Result result;

	public Engine(CREEnv env) {
		this.env = env;
		this.scenario = new Scenario(env);
	}
	
	public Engine(Scenario scenario) {
		this.scenario = scenario;
		this.env = this.scenario.getEnv();
	}

	@Override
	public abstract void run();

	public Double[] getBiasOffset() {
		return biasOffset;
	}

	public void setBiasOffset(Double[] biasOffset) {
		this.biasOffset = biasOffset;
	}

	public CREEnv getEnv() {
		return env;
	}

	public void setEnv(CREEnv env) {
		this.env = env;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}