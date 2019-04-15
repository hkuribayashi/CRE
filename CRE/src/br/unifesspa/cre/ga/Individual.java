package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.util.Util;

public class Individual implements Comparable<Individual>, Cloneable{

	private Double evaluation;

	private Double[] chromossome;

	private Scenario scenario;


	public Individual(Scenario scenario, Boolean flag) {

		this.scenario = scenario;
		this.evaluation = 0.0;

		int chromossomeSize = this.scenario.getFemtoPoints().size();
		double lowerBound, upperBound;

		if (!flag) {
			lowerBound = this.scenario.getEnv().getInitialGeneRange();
			upperBound = this.scenario.getEnv().getFinalGeneRange();	
		}else {
			lowerBound = this.scenario.getEnv().getInitialSugestedIndividual();
			upperBound = this.scenario.getEnv().getFinalSugestedIndividual();	
		}

		this.chromossome = new Double[chromossomeSize];

		for (int i=0; i<chromossomeSize; i++)
			this.chromossome[i] = Util.getUniformRealDistribution(lowerBound, upperBound);
	}

	public Individual crossover(Individual otherIndividual) {
		Individual individual = null;
		int index = Util.getUniformIntegerDistribution(0, 2);
		CrossoverStrategy strategy = CrossoverStrategy.values()[index];
		switch(strategy) {
			case OnePoint: individual = onePointCrossover(otherIndividual); break;
			case TwoPoint: individual = twoPointCrossover(otherIndividual); break;
			case Uniform: individual = uniformCrossover(otherIndividual);   break;
			default: individual = this; break;
		}
		return individual;
	}

	private Individual onePointCrossover(Individual otherIndividual) {
		int cut = (int) Math.round(Math.random() * this.chromossome.length);
		
		List<Double> f1 = Arrays.asList(this.getChromossome());
		List<Double> f2 = Arrays.asList(otherIndividual.getChromossome());
		
		List<Double> s = new ArrayList<Double>();
		
		if (Math.random() < 0.5) {
			s.addAll(f1.subList(0, cut));
			s.addAll(f2.subList(cut, f2.size()));	
		}else {
			s.addAll(f2.subList(0, cut));
			s.addAll(f1.subList(cut, f1.size()));
		}
		
		Double[] son = new Double[s.size()];
		son = s.toArray(son);
		
		Individual individual = null;
		try {
			individual = (Individual) this.clone();
			individual.setChromossome(son);
		} catch (CloneNotSupportedException e) {
			individual = this;
		}
		individual.setChromossome(son);

		return individual;
	}
	
	private Individual twoPointCrossover(Individual otherIndividual) {
		return onePointCrossover(otherIndividual);
	}
	
	private Individual uniformCrossover(Individual otherIndividual) {
		return onePointCrossover(otherIndividual);
	}
	
	public Individual mutation() {
		Individual individual = null;
		int index = Util.getUniformIntegerDistribution(0, MutationStrategy.values().length-1);
		MutationStrategy strategy = MutationStrategy.values()[index];
		switch(strategy) {
			case Flat: individual = flatMutation(); break;
			case NotUniform: individual = notUniformMutation(); break;
			default: individual = this; break;
		}
		return individual;
	}

	public Individual flatMutation() {
		return null;
	}
	
	public Individual notUniformMutation() {
		return null;
	}

	public void evaluate() {
		this.scenario.setBias(this.chromossome);
		this.scenario.getInitialSINR();
		this.scenario.getCoverageMatrix();
		this.scenario.getBSLoad();
		this.scenario.getInitialBitRate();
		this.scenario.getFinalBitRate();
		this.evaluation = this.scenario.getSumRate();
	}

	public Double getEvaluation() {
		return evaluation;
	}


	public void setEvaluation(Double evaluation) {
		this.evaluation = evaluation;
	}


	public Double[] getChromossome() {
		return chromossome;
	}


	public void setChromossome(Double[] chromossome) {
		this.chromossome = chromossome;
	}


	public Scenario getScenario() {
		return scenario;
	}


	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

    @Override
    public int compareTo(Individual o) {
        if (this.evaluation > o.getEvaluation()) {
            return -1;
        }
        if (this.evaluation < o.getEvaluation()) {
            return 1;
        }
        return 0;
    }
}