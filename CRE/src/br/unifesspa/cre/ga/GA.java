package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;

public class GA {

	private Integer populationSize;

	private List<Individual> population;

	private Integer generation;

	private Individual bestSolution;

	private Scenario scenario;

	public GA(Scenario scenario) {

		this.populationSize = scenario.getEnv().getPopulationSize();
		this.scenario = scenario;
		this.population = new ArrayList<Individual>();

		for (int i=0; i<this.populationSize; i++)
			this.population.add(new Individual(this.scenario));

		this.bestSolution = this.population.get(0);
	}

	public Double evaluate() {
		Double sum = 0.0;
		List<Individual> p = this.getPopulation();
		synchronized(p) {
			for (int i=0; i<p.size(); i++) {
				p.get(i).evaluate();
				sum += p.get(i).getEvaluation();
			}
		}
		Collections.sort(p);
		this.setPopulation(p);
		return sum;
	}

	public void evolve() {

		Double sum = 0.0;
		int aux = 0;
		Double mutationProbability = this.scenario.getEnv().getInitialMutationProbability();
		Double crossoverProbability = this.scenario.getEnv().getInitialCrossoverProbability();
		
		while(aux < this.scenario.getEnv().getGenerationSize()) {

			sum = this.evaluate();

			List<Individual> newPopulation = new ArrayList<Individual>();
			for (int j=0; j<this.getPopulation().size(); j++) {
				
				if (Math.random() < crossoverProbability) {
					int f1 = this.rouletteFatherSelection(sum);
					int f2 = this.rouletteFatherSelection(sum);
					Individual son = this.getPopulation().get(f1).crossover(this.getPopulation().get(f2)) ;
					if (Math.random() < mutationProbability)
						son.mutation();
					newPopulation.add(son);
				}else if(Math.random() < mutationProbability){
					int f1 = this.rouletteFatherSelection(sum);
					Individual son = this.population.get(f1);
					son.mutation();
					newPopulation.add(son);
				}
			}

			this.setPopulation(newPopulation);
			this.evaluate();
			
			if (this.getBestSolution().getEvaluation() < this.population.get(0).getEvaluation()) {
				this.setBestSolution(this.population.get(0));
				System.out.println("Generation: "+aux);
				System.out.println(this.getBestSolution().getEvaluation());
			}
			
			aux++;
		}
	}

	public int rouletteFatherSelection(Double evaluationSum) {
		int father = -1;
		Double sortedValue = Math.random() * evaluationSum;
		Double sum = 0.0;
		int i = 0;
		while (i < this.population.size() && sum < sortedValue) {
			sum += this.population.get(i).getEvaluation();
			father += 1;
			i += 1;
		}
		return father;

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

	public Integer getGeneration() {
		return generation;
	}

	public void setGeneration(Integer generation) {
		this.generation = generation;
	}

	public Individual getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(Individual bestSolution) {
		this.bestSolution = bestSolution;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
}
