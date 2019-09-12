package br.unifesspa.cre.pso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

/**
 * Classical PSO
 * 
 * @author hugo
 *
 */
public abstract class PSO implements Runnable{

	protected Scenario scenario;

	protected List<Particle> swarm;

	protected double steps;

	protected Particle gBest;

	protected double alpha;

	protected double beta;
	
	protected Result result;
	
	private static Double cognitiveCoeffcient = 2.0;
	
	private static Double socialCoeffcient = 2.0;

	public PSO(double alpha, double beta, Scenario scenario, double steps, int swarmSize, double targetSolution) {
		this.alpha = alpha;
		this.beta = beta;
		this.scenario = scenario;
		this.steps = steps;
		
		Double qtdMacro = this.scenario.getEnv().getArea() * this.scenario.getEnv().getLambdaMacro();
		int particleDimension = this.scenario.getAllBS().size()-(qtdMacro.intValue());
		
		this.swarm = new ArrayList<Particle>();
		for (int i=0; i<swarmSize; i++)
			this.swarm.add(new Particle(this.alpha, this.beta, this.scenario.clone(), particleDimension));
		
		this.gBest = Collections.max(this.swarm);
	}
	
	public void updateVelocity(Particle p) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, PSO.cognitiveCoeffcient);
		
		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, PSO.socialCoeffcient);
		
		p.setVelocity( Util.sum(p.getVelocity(), Util.sum(cognitiveComponent, socialComponent)) );
	}
	
	public void updatePosition(Particle p) {
		p.setPosition( Util.sum(p.getPosition(), p.getVelocity()) );
		p.evaluation();
		p.updateBestPosition();
	}
	
	public void updateGlobalBestPosition() {
		Particle gBestCandidate = Collections.max(this.swarm);
		if (gBestCandidate.getEvaluation() > this.gBest.getEvaluation()) {
			this.gBest = (Particle) gBestCandidate.clone(); 
		}
	}
	
	public void search() {
		int counter = 0;
		Double meanEvaluation = 0.0;
		
		while (counter < this.steps) {
			for (int i=0; i<this.swarm.size(); i++) {
				updateVelocity(this.swarm.get(i));
				updatePosition(this.swarm.get(i));
				updateGlobalBestPosition();
				meanEvaluation += this.swarm.get(i).getEvaluation();
			}
			counter++;
			
			//System.out.println("Step: "+counter);
			//System.out.println("gBest Evaluation: "+this.gBest.getEvaluation());
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}