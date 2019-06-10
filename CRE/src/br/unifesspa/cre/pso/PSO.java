package br.unifesspa.cre.pso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;

public class PSO {

	private Scenario scenario;

	private List<Particule> population;

	private Integer steps;

	private Particule gBest;

	private Double targetSolution;

	public PSO(Scenario scenario, Double targetSolution, Integer groupSize, Integer steps) {

		this.scenario = scenario;
		this.steps = steps;
		this.targetSolution = targetSolution;

		this.population = new ArrayList<Particule>();
		for (int i=0; i<groupSize; i++)
			this.population.add(new Particule(this.scenario));

		this.gBest = null;
	}

	public void evaluate() {
		for (int i=0; i<this.population.size(); i++)
			this.population.get(i).evaluate(this.targetSolution);
		this.gBest = Collections.min(this.population);
	}

	public Result search() {

		int counter = 0;
		while (counter < this.steps) {

			this.evaluate();
			for (int i=0; i<this.population.size(); i++) 
				this.population.get(i).updateVelocity(this.gBest.getBestConfiguration());
			
			System.out.println("Step: "+counter);
			System.out.println("Solution: "+this.gBest.getBestPosition());
			System.out.println("UEs served: "+this.gBest.getResult().getUesServed());
			System.out.println("Serving BSs: "+this.gBest.getResult().getServingBSs());
			System.out.println("Sum Rate: "+this.gBest.getResult().getSumRate());
			System.out.println("Median Rate: "+this.gBest.getResult().getMedianRate());
			System.out.println();

			counter++;
		}
		
		return this.gBest.getResult();
	}
}
