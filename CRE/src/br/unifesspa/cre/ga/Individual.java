package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.util.Util;

public class Individual implements Comparable<Individual>, Cloneable {

	private Double[] chromosome;

	private Double evaluation;

	private Scenario scenario;

	public Individual(Scenario scenario) {

		this.scenario = scenario;
		int numberOfGens = this.scenario.getFemtoPoints().size();
		double lower = this.scenario.getEnv().getInitialGeneRange();
		double upper = this.scenario.getEnv().getFinalGeneRange();
		this.chromosome = new Double[numberOfGens];

		for (int i = 0; i < numberOfGens; i++)
			this.chromosome[i] = Util.getUniformRealDistribution(lower, upper);
	}

	public void evaluate() {

		this.scenario.setBias(this.chromosome);
		this.scenario.getInitialSINR();
		this.scenario.getCoverageMatrix();
		this.scenario.getBSLoad();
		this.scenario.getInitialBitRate();
		this.scenario.getFinalBitRate();

		this.setEvaluation(this.scenario.getSumRate());
	}

	public Individual crossover(Individual otherIndividual){

		int index = Util.getUniformIntegerDistribution(0, 2);
		CrossoverStrategy strategy = CrossoverStrategy.values()[index];
		Individual i = null;

		switch(strategy) {
			case OnePoint: i = this.onePointCrossover(otherIndividual); break;
			case TwoPoint: i = this.twoPointCrossover(otherIndividual); break;
			case Uniform:  i = this.uniformCrossover(otherIndividual);	break;
			default: break;
		}
		
		return i;
	}
	
	private Individual uniformCrossover(Individual otherIndividual) {
		return onePointCrossover(otherIndividual);
	}
	
	private Individual twoPointCrossover(Individual otherIndividual) {
		return this;
	}

	private Individual onePointCrossover(Individual otherIndividual){

		int cut = (int) Math.round(Math.random() * this.chromosome.length);

		List<Double> son = null;
		List<Double> pai1 = new ArrayList<Double>(Arrays.asList(this.getChromosome()));
		List<Double> pai2 = new ArrayList<Double>(Arrays.asList(otherIndividual.getChromosome()));
		
		if (Math.random() < 0.5) {
			son = pai1.subList(0, cut);
			son.addAll(pai2.subList(cut, pai1.size()));	
		}else {
			son = pai2.subList(0, cut);
			son.addAll(pai1.subList(cut, pai2.size()));
		}

		Double[] c = new Double[this.chromosome.length];
		c = son.toArray(c);

		Individual i = null;
		
		try {
			i = (Individual) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		i.setChromosome(c);
		
		return i;
	}

	public Individual mutation() {
		return this.getNotUniformMutation();
	}

	private Individual getNotUniformMutation() {
		return this;
	}

	public Double[] getChromosome() {
		return chromosome;
	}

	public void setChromosome(Double[] chromosome) {
		this.chromosome = chromosome;
	}

	public Double getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Double evaluation) {
		this.evaluation = evaluation;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	@Override
	public int compareTo(Individual o) {
		if (this.getEvaluation() > o.getEvaluation())
			return -1;
		else if (this.getEvaluation() == o.getEvaluation()) {
			return 0;
		} else return -1;
	}
}