package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;

public class GA{

	private Scenario scenario;

	private Integer populationSize;

	private List<Individual> population;

	private Integer generationsSize;

	private double bestSolution;
	
	private Individual bestIndividual;
	
	private double[] crossoverProbability;
	
	private double[] mutationProbability;
	
	private Double alpha;
	
	private Double beta;

	public GA(Double alpha, Double beta, Scenario scenario) {

		this.scenario = scenario;
		this.populationSize = this.scenario.getEnv().getPopulationSize();
		this.generationsSize = this.scenario.getEnv().getGenerationSize();
		this.alpha = alpha;
		this.beta = beta;

		this.population = new ArrayList<Individual>();
		for (int i=0; i<populationSize; i++)
			this.population.add(new Individual(this.alpha, this.beta, this.scenario));

		this.bestSolution = 0.0;
		this.bestIndividual = null;
		
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
		Comparator<Individual> c = Collections.reverseOrder(); 
		Collections.sort(this.population, c);
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
			
			geneticOperators(currentGeneration, sum, newPopulation);
			
			newPopulation.addAll(kElitism);
			this.setPopulation(newPopulation);
			this.evaluate();
			
			if (bestEvaluation < this.getPopulation().get(0).getEvaluation()) {
				
				this.setBestSolution(this.getPopulation().get(0).getEvaluation());
				this.setBestIndividual(this.getPopulation().get(0));
				bestEvaluation = this.getBestSolution();
				
				System.out.println("Generation: "+currentGeneration);
				System.out.println("Solution: "+this.getBestIndividual().getEvaluation());
				System.out.println("Alpha: "+this.getBestIndividual().getResult().getAlpha());
				System.out.println("Beta: "+this.getBestIndividual().getResult().getBeta());
				System.out.println("UEs served: "+this.getBestIndividual().getResult().getUesServed());
				System.out.println("Serving BSs: "+this.getBestIndividual().getResult().getServingBSs());
				System.out.println("Sum Rate: "+this.getBestIndividual().getResult().getSumRate());
				System.out.println("Median Rate: "+this.getBestIndividual().getResult().getMedianRate());
				System.out.println();
				
			}
			currentGeneration++;
		}

	}

	private void geneticOperators(int currentGeneration, Double sum, List<Individual> newPopulation) {
		double crossoverProbability = this.crossoverProbability[currentGeneration]; 
		double mutationProbability = this.mutationProbability[currentGeneration];
		
		for (int i = 0; i < this.populationSize; i++) {
			
			int f1 = this.roulette(sum);
			int f2 = this.roulette(sum);
			
			while (f1 == f2) 
				f2 = this.roulette(sum);
			
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

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public void setBestIndividual(Individual bestIndividual) {
		this.bestIndividual = bestIndividual;
	}

	public double[] getCrossoverProbability() {
		return crossoverProbability;
	}

	public void setCrossoverProbability(double[] crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	public double[] getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double[] mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public void setBestSolution(double bestSolution) {
		this.bestSolution = bestSolution;
	}

	public Double getAlpha() {
		return alpha;
	}

	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public Double getBeta() {
		return beta;
	}

	public void setBeta(Double beta) {
		this.beta = beta;
	}
}