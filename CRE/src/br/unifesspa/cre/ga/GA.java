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
	
	private double[] crossoverProbability;
	
	private double[] mutationProbability;

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
		
		double bC = this.scenario.getEnv().getInitialCrossoverProbability();
		double aC = (this.scenario.getEnv().getFinalCrossoverProbability() - bC)/this.generationsSize;
		
		double bM = this.scenario.getEnv().getInitialMutationProbability();
		double aM = (this.scenario.getEnv().getFinalMutationProbability() - bM)/this.generationsSize;
		
		this.crossoverProbability = new double[this.generationsSize];
		this.mutationProbability = new double[this.generationsSize];
		
		for (int i=0; i<crossoverProbability.length; i++) {
			this.crossoverProbability[i] = (aC*i)+bC;
			this.mutationProbability[i] = (aM*i)+bM;
		}
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
		int currentGeneration = 0;
		double bestEvaluation = this.bestSolution;
		int kElitismSize = this.scenario.getEnv().getkElitism();
		List<Individual> kElitism = new ArrayList<Individual>();
				
		while (currentGeneration < this.scenario.getEnv().getGenerationSize()) {
			Double sum = this.evaluate();
	
			List<Individual> newPopulation = new ArrayList<Individual>();
			for (int k=0; k<kElitismSize; k++) {
				kElitism.add(this.population.get(k));
				this.population.remove(k);
			}
			
			sum = this.evaluate();
			
			double crossoverProbability = this.crossoverProbability[currentGeneration]; 
			double mutationProbability = this.mutationProbability[currentGeneration];
			
			for (int i = 0; i < this.populationSize; i++) {
				int f1 = this.roulette(sum);
				int f2 = this.roulette(sum);
				
				Individual individual = null;
				
				if (Math.random() < crossoverProbability ) {
					individual = this.population.get(f1).crossover(this.population.get(f2));
					if (Math.random() < mutationProbability)
						individual.mutation(currentGeneration);
				} else if (Math.random() < mutationProbability ) {
					individual = this.population.get(f1);
					individual.mutation(currentGeneration);
				}else individual = this.population.get(f1);
				
				newPopulation.add(individual);
			}
			newPopulation.addAll(kElitism);
			this.setPopulation(newPopulation);
			this.evaluate();
			if (bestEvaluation < this.getPopulation().get(0).getEvaluation()) {
				this.setBestSolution(this.getPopulation().get(0).getEvaluation());
				bestEvaluation = this.getBestSolution();
				System.out.println("Genaration: "+currentGeneration);
				System.out.println(this.getBestSolution());
			}
			currentGeneration++;
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