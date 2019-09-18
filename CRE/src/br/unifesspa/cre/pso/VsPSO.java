package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class VsPSO extends PSO {
	
	protected static Double cognitiveCoeffcient = 2.05;
	
	protected static Double socialCoeffcient = 2.05;
	
	public static Double initialInertialWeight = 0.9;

	public static Double finalInertialWeight = 0.4;
	
	public Double[] inertiaWeight;

	public VsPSO(double alpha, double beta, Scenario scenario, Double steps, int swarmSize, double targetSolution) {
		
		super(alpha, beta, scenario, (steps * 2), swarmSize, targetSolution);
		
		Double localSteps = (this.steps * 2);
		
		double b = DecreaseIWPSO.initialInertialWeight;
		double a = (DecreaseIWPSO.finalInertialWeight - b)/localSteps;
		
		this.inertiaWeight = new Double[localSteps.intValue()];
		for (int i=0; i<inertiaWeight.length; i++)
			this.inertiaWeight[i] = (a * i) + b;
	}
	
	public void updateVelocity(Particle p, Integer step) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, VsPSO.cognitiveCoeffcient);
		
		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, VsPSO.socialCoeffcient);
		
		p.setVelocity( Util.product(Util.sum(p.getVelocity(), Util.sum(cognitiveComponent, socialComponent)), this.inertiaWeight[step]) );
	}
	
	@Override
	public void search() {
		int counter = 0;
		Double lastMeanEvaluation = 0.0;
		Double currentMeanEvaluation = 0.0;
		Double convergencyCounter = 0.0;
		
		while (counter < this.steps) {
			for (int i=0; i<this.swarm.size(); i++) {
				updateVelocity(this.swarm.get(i), counter);
				updatePosition(this.swarm.get(i));
				updateGlobalBestPosition();
				currentMeanEvaluation += this.swarm.get(i).getEvaluation();
			}
			counter++;
			
			double dif = Math.abs((lastMeanEvaluation - currentMeanEvaluation)/this.swarm.size());
			
			//System.out.println("Step: "+counter);
			//System.out.println("Mean Evaluation: "+(currentMeanEvaluation/this.swarm.size()));
			//System.out.println("Diference from last Evaluation: "+dif);
			//System.out.println("gBest Evaluation: "+this.gBest.getBestEvaluation());
			
			if (dif <= 0.5)
				convergencyCounter++;
			if (convergencyCounter > 5) {
				this.updateSwarm(currentMeanEvaluation);
				convergencyCounter = -10.0;
			}
	
			lastMeanEvaluation = currentMeanEvaluation;
			currentMeanEvaluation = 0.0;
		}
		
		Result r = new Result();
		
		r.setAlpha(this.gBest.getAlpha());
		r.setBeta(this.gBest.getBeta());
		r.setBias(1.0);
		r.setEvaluation(this.gBest.getEvaluation());
		r.setMedianRate(this.gBest.getScenario().getMedianRate());
		r.setScenario(this.gBest.getScenario());
		r.setRequiredRate(this.gBest.getScenario().getRequiredRate());
		r.setServingBSs(this.gBest.getScenario().getServingBSs());
		r.setUesServed(this.gBest.getScenario().getUesServed());
		r.setSumRate(this.gBest.getScenario().getSumRate());
		r.setSolution(this.gBest.getPosition());
		
		System.out.println(r);
		
		this.setResult(r);
	}
	
	public void updateSwarm(Double currentEvaluation) {
		
		int maxUpdates = (int) (0.5 * this.swarm.size());
		
		Double qtdMacro = this.scenario.getEnv().getArea() * this.scenario.getEnv().getLambdaMacro();
		int particleDimension = this.scenario.getAllBS().size()-(qtdMacro.intValue());
		
		for (int i=0; i<this.swarm.size(); i++) {
			if (this.swarm.get(i).getEvaluation() < currentEvaluation && maxUpdates > 0) {
				this.swarm.set(i, new Particle(this.alpha, this.beta, this.scenario.clone(), particleDimension));
				maxUpdates--;
			}
		}		
	}
	
}
