package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Individual implements Comparable<Individual>, Cloneable{

	private Double evaluation;

	private Double[] chromossome;

	private Scenario scenario;

	private Result result;
	
	private Double alpha;
	
	private Double beta;

	public Individual(Double alpha, Double beta, Scenario scenario) {

		this.scenario = scenario;
		this.evaluation = 0.0;
		this.result = null;
		this.alpha = alpha;
		this.beta = beta;

		Double qtdMacro = this.scenario.getEnv().getArea() * this.scenario.getEnv().getLambdaMacro();
		int chromossomeSize = this.scenario.getAllBS().size()-(qtdMacro.intValue());
		double lowerBound, upperBound;

		lowerBound = this.scenario.getEnv().getInitialGeneRange();
		upperBound = this.scenario.getEnv().getFinalGeneRange();	

		this.chromossome = new Double[chromossomeSize];

		for (int i=0; i<chromossomeSize; i++)
			this.chromossome[i] = Util.getUniformRealDistribution(lowerBound, upperBound);
	}

	public Individual crossover(Individual otherIndividual) {
		Individual individual = null;
		int index = Util.getUniformIntegerDistribution(0, CrossoverStrategy.values().length-1);
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
		int cut1 = (int) Math.round(Math.random() * this.chromossome.length);
		int cut2 = (int) Math.round(Math.random() * (this.chromossome.length - cut1) );

		cut2 += cut1;

		List<Double> f1 = Arrays.asList(this.getChromossome());
		List<Double> f2 = Arrays.asList(otherIndividual.getChromossome());

		List<Double> s = new ArrayList<Double>();

		if (Math.random() < 0.5) {
			s.addAll(f1.subList(0, cut1));
			s.addAll(f1.subList(cut1, cut2));
			s.addAll(f1.subList(cut2, f1.size()));	
		}else {
			s.addAll(f2.subList(0, cut1));
			s.addAll(f1.subList(cut1, cut2));
			s.addAll(f1.subList(cut2, f1.size()));
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

	private Individual uniformCrossover(Individual otherIndividual) {

		List<Double> f1 = Arrays.asList(this.getChromossome());
		List<Double> f2 = Arrays.asList(otherIndividual.getChromossome());
		List<Double> s = new ArrayList<Double>();

		for (int i=0; i < f1.size(); i++) {
			if (Math.random() < 0.5)
				s.addAll(f1.subList(i, i + 1));
			else s.addAll(f2.subList(i, i + 1));
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


	public void mutation(int currentGereneration) {

		int index = Util.getUniformIntegerDistribution(0, MutationStrategy.values().length-1);
		MutationStrategy strategy = MutationStrategy.values()[index];

		double probability = this.scenario.getEnv().getInitialMutationProbability();

		switch(strategy) {
		case Random: randomMutation(probability); break;
		case NotUniform: notUniformMutation(currentGereneration, probability); break;
		default: break;
		}
	}

	private void randomMutation(double probability) {

		double lower = Collections.min(Arrays.asList(this.chromossome));
		double upper = Collections.max(Arrays.asList(this.chromossome));

		for (int i=0; i<this.chromossome.length; i++)
			if (Math.random() < probability)
				this.chromossome[i] = Util.getUniformRealDistribution(lower, upper);
	}

	private void notUniformMutation(int currentGeneration, double probability) {

		double aux = 0.0;
		int i=0;
		int generationsSize = this.scenario.getEnv().getGenerationSize();
		double lowerBound = this.scenario.getEnv().getInitialGeneRange();
		double upperBound = this.scenario.getEnv().getFinalGeneRange();

		while (i<this.chromossome.length) {

			if (Math.random() < probability) {
				int tau = Util.getUniformIntegerDistribution(0, 1);

				if (tau == 0) 
					aux = this.chromossome[i] + this.delta(currentGeneration, (upperBound-this.chromossome[i]), generationsSize, 10);
				else aux = this.chromossome[i] - this.delta(currentGeneration, (this.chromossome[i]-lowerBound), generationsSize, 10);

				this.chromossome[i] = aux;
			}
			i++;	
		}

	}

	private Double delta(int t, double y, double gmax, double b) {
		double r = Math.random();
		double z = Math.pow(1-(t/gmax),b);
		return y * (1 - Math.pow(r, z));
	}

	public void evaluate() {
		this.scenario.setBias(this.chromossome);
		this.scenario.evaluation();
		
		this.result = new Result();
		this.result.setAlpha(this.alpha);
		this.result.setBeta(this.beta);
		this.result.setSumRate(this.scenario.getSumRate());
		this.result.setMedianRate(this.scenario.getMedianRate());
		this.result.setRequiredRate(this.scenario.getRequiredRate());
		this.result.setUesServed(this.scenario.getUesServed());
		this.result.setServingBSs(this.scenario.getServingBSs());
	
		double evaluation = (this.alpha*this.scenario.getUesServed()) + (this.beta*this.scenario.getServingBSs());
		this.result.setEvaluation(evaluation);
		
		this.evaluation = evaluation;
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Override
	public int compareTo(Individual o) {
		if (this.evaluation == o.getEvaluation()) {
			return 0;
		}else if (this.evaluation > o.getEvaluation()) {
			return 1;
		}else return 0;
	}
}