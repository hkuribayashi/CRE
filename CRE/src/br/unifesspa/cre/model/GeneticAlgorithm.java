package br.unifesspa.cre.model;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {

	private Integer populationSize;

	private Integer geracao;

	private List<Individual> population;

	private Individual bestSolution;

	public GeneticAlgorithm (Integer populationSize) {

		this.populationSize = populationSize;
		
		this.population = new ArrayList<Individual>();

	}

	public void initPopulation() {
		
		for (int i = 0; i < this.populationSize; i++) {
			this.population.add(new Individual());
		}
		this.bestSolution = this.population.get(0);
	}

	public Integer getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(Integer populationSize) {
		this.populationSize = populationSize;
	}

	public Integer getGeracao() {
		return geracao;
	}

	public void setGeracao(Integer geracao) {
		this.geracao = geracao;
	}

	public List<Individual> getPopulation() {
		return population;
	}

	public void setPopulation(List<Individual> population) {
		this.population = population;
	}

	public Individual getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(Individual bestSolution) {
		this.bestSolution = bestSolution;
	}
}
