package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class StochasticIWPSO extends StaticIWPSO{

	public Double[] inertiaWeight;

	private static Double initialInertialWeight = 0.5;

	private static Double finalInertialWeight = 1.0;

	protected static Double cognitiveCoeffcient = 2.05;

	protected static Double socialCoeffcient = 2.05;

	public StochasticIWPSO(Double alpha, Double beta, Scenario scenario, Double steps, Integer swarmSize,
			Double targetSolution) {
		super(alpha, beta, scenario, steps, swarmSize, targetSolution);

		this.inertiaWeight = new Double[steps.intValue()];
		for (int i=0; i<this.inertiaWeight.length; i++)
			this.inertiaWeight[i] = Util.getUniformRealDistribution(StochasticIWPSO.initialInertialWeight, 
					StochasticIWPSO.finalInertialWeight);
	}

	public void updateVelocity(Particle p, Integer step) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, StochasticIWPSO.cognitiveCoeffcient);

		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, StochasticIWPSO.socialCoeffcient);

		p.setVelocity( Util.product(Util.sum(p.getVelocity(), Util.sum(cognitiveComponent, socialComponent)), this.inertiaWeight[step]) );
	}

	@Override
	public void search() {
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
		r.setRequiredRate(this.gBest.getScenario().getRequiredRate());
		r.setServingBSs(this.gBest.getScenario().getServingBSs());
		r.setUesServed(this.gBest.getScenario().getUesServed());
		r.setSumRate(this.gBest.getScenario().getSumRate());
		r.setSolution(this.gBest.getPosition());

		this.setResult(r);
	}
}