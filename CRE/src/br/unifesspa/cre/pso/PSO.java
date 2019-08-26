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

	private Double alpha;

	private Double beta;

	private Double targetSolution;

	public PSO(Double alpha, Double beta, Scenario scenario, Double targetSolution, Integer groupSize, Integer steps) {

		this.scenario = scenario;
		this.steps = steps;
		this.alpha = alpha;
		this.beta = beta;
		this.targetSolution = targetSolution;

		this.population = new ArrayList<Particule>();
		for (int i=0; i<groupSize; i++)
			this.population.add(new Particule(this.alpha, this.beta, this.scenario));

		this.gBest = null;
	}

	public void evaluate() {
		for (int i=0; i<this.population.size(); i++)
			this.population.get(i).evaluate(this.targetSolution);
		this.gBest = Collections.max(this.population);
	}

	public Result search() {
		
		int counter = 0, counter2 = 0;
		Double temp = 0.0;

		while (counter < this.steps) {
			this.evaluate();
			for (int i=0; i<this.population.size(); i++) 
				this.population.get(i).updateVelocity(this.gBest.getBestConfiguration());

			
			if ( temp != this.gBest.getBestPosition() ) {
				temp = this.gBest.getBestPosition();
				System.out.println("Step: "+counter);
				System.out.println("Value: "+temp);
			}else counter2++;
			
			if (counter2++ >=20)
				break;
			
			counter++;
		}

		return this.gBest.getResult();
	}
}
