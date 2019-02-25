package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.List;

public class Individual {

	private Integer id;

	private List<Double> chromosome;

	private Double evaluation;

	private Double generation;

	public Individual() {

		int numberOfGens = 100;

		this.chromosome = new ArrayList<Double>();

		for (int i = 0; i < numberOfGens; i++) {
			if (Math.random() < 0.5) {
				this.chromosome.add(0.0);    
			} else {
				this.chromosome.add(1.0);
			}
		}
	}

	public void execEvaluation() {

	}

	public List<Individual> crossover(Individual otherIndividual){

		int cut = (int) Math.round(Math.random() * this.chromosome.size());

		List<Double> firstChromosome = new ArrayList<Double>();
		firstChromosome.addAll(otherIndividual.getChromosome().subList(0, cut));
		firstChromosome.addAll(this.chromosome.subList(cut, this.chromosome.size()));

		List<Double> secondChromosome = new ArrayList<Double>();
		secondChromosome.addAll(this.chromosome.subList(0, cut));
		secondChromosome.addAll(otherIndividual.getChromosome().subList(cut, this.chromosome.size()));

		List<Individual> sons = new ArrayList<Individual>();

		sons.add(new Individual());
		sons.add(new Individual());

		sons.get(0).setChromosome(firstChromosome);
		sons.get(0).setGeneration(this.generation++);

		sons.get(1).setChromosome(secondChromosome);
		sons.get(1).setGeneration(this.generation++);

		return sons;
	}

	public Individual execMutation(Double mutationRate) {
		
		for (int i = 0; i < this.chromosome.size(); i++) {
			if (Math.random() < mutationRate) {
				
				if (this.chromosome.get(i).equals((1.0)))
					
					this.chromosome.set(i, 0.0);
					
				 else this.chromosome.set(i, 1.0);
			}
		}
		
		return this;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Double> getChromosome() {
		return chromosome;
	}

	public void setChromosome(List<Double> chromosome) {
		this.chromosome = chromosome;
	}

	public Double getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Double evaluation) {
		this.evaluation = evaluation;
	}

	public Double getGeneration() {
		return generation;
	}

	public void setGeneration(Double generation) {
		this.generation = generation;
	}
}