package br.unifesspa.cre.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
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
		
		int i;

		for (i = 0; i < f1.size(); i++) {

			if (Math.random() < 0.5) {

				s.addAll(f1.subList(i, i + 1));
			} else {

				s.addAll(f2.subList(i, i + 1));
			}
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
		double r = Util.getUniformRealDistribution(0.0, 1.0);
		double z = Math.pow(1-(t/gmax),b);
		return y * (1 - Math.pow(r, z));
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

	
	//Metodo main apenas como exemplo
	
	public static void main(String[] args) {


		CREEnv env = new CREEnv();

		env.set(Param.area, 1000000.0); 	  		// 1 km^2
		env.set(Param.lambdaFemto, 0.00002);  		// 0.00002 Femto/m^2 = 20 Femtos  
		env.set(Param.lambdaUser, 0.0001);    		// 0.0001 Users/m^2 = 100 Users 
		env.set(Param.lambdaMacro, 0.000002); 		// 0.000002 Macros/m^2 = 2 Macros

		env.set(Param.initialCrossoverProbability, 0.9);
		env.set(Param.finalCrossoverProbability, 0.5);
		env.set(Param.initialMutationProbability, 0.2);
		env.set(Param.finalMutationProbability, 0.8);

		env.set(Param.initialGeneRange, -3.0);
		env.set(Param.finalGeneRange, 3.0);

		env.set(Param.initialSugestedIndividual, 0.9);
		env.set(Param.finalSugestedInvidual, 1.85);

		env.set(Param.populationSize, 20);
		env.set(Param.generationSize, 50);
		env.set(Param.kElitism, 2);

		Scenario s = new Scenario(env);
		s.getDistance();

		Individual i1 = new Individual(s, true);
		i1.evaluate();
		System.out.println(i1.getEvaluation());

		System.out.println();

		Individual i2 = new Individual(s, true);
		i2.evaluate();
		System.out.println(i2.getEvaluation());

		Individual i3 = i1.crossover(i2);
		i3.mutation(0);
		i3.evaluate();
		System.out.println(i3.getEvaluation());





	}

}