package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;


public class DecreaseIWPSO extends StaticIWPSO {
	
	public static Double initialInertialWeight = 0.9;

	public static Double finalInertialWeight = 0.4;
	
	public Double[] inertiaWeight;

	public DecreaseIWPSO(Double alpha, Double beta, Scenario scenario, Double steps, Integer swarmSize, Double targetSolution) {
		
		super(alpha, beta, scenario, steps, swarmSize, targetSolution);
		
		double b = DecreaseIWPSO.initialInertialWeight;
		double a = (DecreaseIWPSO.finalInertialWeight - b)/this.steps;
		
		this.inertiaWeight = new Double[steps.intValue()];
		for (int i=0; i<inertiaWeight.length; i++)
			this.inertiaWeight[i] = (a * i) + b;
	}
	
	public void updateVelocity(Particle p, Integer step) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, IncreaseIWPSO.cognitiveCoeffcient);

		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, StaticIWPSO.socialCoeffcient);

		p.setVelocity( Util.product(Util.sum(p.getVelocity(), Util.sum(cognitiveComponent, socialComponent)), this.inertiaWeight[step]) );
	}
	
	@Override
	public Result search() {
		int counter = 0;
		Double meanEvaluation = 0.0;

		while (counter < this.steps) {
			for (int i=0; i<this.swarm.size(); i++) {
				updateVelocity(this.swarm.get(i), counter);
				updatePosition(this.swarm.get(i));
				updateGlobalBestPosition();
				meanEvaluation += this.swarm.get(i).getEvaluation();
			}
			counter++;

			//System.out.println("Step: "+counter);
			//System.out.println(this.gBest.getEvaluation());
			//System.out.println("Mean Evaluation: "+(meanEvaluation/this.swarm.size()));
			//System.out.println();
			meanEvaluation = 0.0;
		}

		Result r = new Result();

		r.setAlpha(this.gBest.getAlpha());
		r.setBeta(this.gBest.getBeta());
		r.setBias(1.0);
		r.setEvaluation(this.gBest.getEvaluation());
		r.setMedianRate(this.gBest.getScenario().getMedianRate());
		r.setScenario(this.gBest.getScenario());
		r.setRequiredRate(this.scenario.getRequiredRate());
		r.setServingBSs(this.scenario.getServingBSs());
		r.setUesServed(this.scenario.getUesServed());
		r.setSumRate(this.scenario.getSumRate());
		r.setSolution(this.gBest.getPosition());

		return r;
	}
}