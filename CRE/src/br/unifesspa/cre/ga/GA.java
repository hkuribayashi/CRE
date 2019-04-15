package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;

public class GA{

	private Scenario scenario;

	private Integer populationSize;

	private List<Individual> population;

	private Integer generationsSize;

	private double bestSolution;

	public GA(Scenario scenario) {

		this.scenario = scenario;
		this.populationSize = this.scenario.getEnv().getPopulationSize();
		this.generationsSize = this.scenario.getEnv().getGenerationSize();
		int kElitism = this.scenario.getEnv().getkElitism();

		this.population = new ArrayList<Individual>();
		for (int i=0; i<kElitism; i++)
			this.population.add(new Individual(this.scenario, true));
		for (int i=kElitism; i<populationSize; i++)
			this.population.add(new Individual(this.scenario, false));

		this.bestSolution = 0.0;

	}

	public Double evaluate() {
		Double sum = 0.0;
		int i=0;
		while (i<this.population.size()) {
			this.population.get(i).evaluate();
			sum += this.population.get(i).getEvaluation();
			i++;
		}
		Collections.sort(this.population);
		return sum;
	}

	public void evolve() {
		int aux = 0;
		double bestEvaluation = this.bestSolution; 
				
		while (aux < this.scenario.getEnv().getGenerationSize()) {
			Double sum = this.evaluate();
			List<Individual> newPopulation = new ArrayList<Individual>();
			for (int i = 0; i < this.populationSize; i++) {
				int f1 = this.roulette(sum);
				int f2 = this.roulette(sum);

				Individual individual = this.population.get(f1).crossover(this.population.get(f2));
				individual.mutation();
				newPopulation.add(individual);
			}
			this.setPopulation(newPopulation);
			this.evaluate();
			if (bestEvaluation < this.getPopulation().get(0).getEvaluation()) {
				this.setBestSolution(this.getPopulation().get(0).getEvaluation());
				bestEvaluation = this.getBestSolution();
				System.out.println("Genaration: "+aux);
				System.out.println(this.getBestSolution());
			}
			aux++;
		}

	}

	private int roulette(Double evaluationSum) {
		int father = -1;
		Double value = Math.random() * evaluationSum;
		Double sum = 0.0;
		int i = 0;
		while (i < this.population.size() && sum < value) {
			sum += this.population.get(i).getEvaluation();
			father += 1;
			i += 1;
		}
		return father;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Integer getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(Integer populationSize) {
		this.populationSize = populationSize;
	}

	public List<Individual> getPopulation() {
		return population;
	}

	public void setPopulation(List<Individual> population) {
		this.population = population;
	}

	public Integer getGenerationsSize() {
		return generationsSize;
	}

	public void setGenerationsSize(Integer generationsSize) {
		this.generationsSize = generationsSize;
	}

	public Double getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(Double bestSolution) {
		this.bestSolution = bestSolution;
	}
}