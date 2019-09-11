package br.unifesspa.cre.pso;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class VsPSO extends PSO {
	
	protected static Double cognitiveCoeffcient = 2.05;
	
	protected static Double socialCoeffcient = 2.05;
	
	public static Double initialInertialWeight = 0.9;

	public static Double finalInertialWeight = 0.4;
	
	public Double[] inertiaWeight;

	public VsPSO(double alpha, double beta, Scenario scenario, Double steps, int swarmSize, double targetSolution) {
		super(alpha, beta, scenario, steps, swarmSize, targetSolution);
		
		double b = DecreaseIWPSO.initialInertialWeight;
		double a = (DecreaseIWPSO.finalInertialWeight - b)/this.steps;
		
		this.inertiaWeight = new Double[steps.intValue()];
		for (int i=0; i<inertiaWeight.length; i++)
			this.inertiaWeight[i] = (a * i) + b;
	}
	
	public void updateVelocity(Particle p, Integer step) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, VsPSO.cognitiveCoeffcient);
		
		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, VsPSO.socialCoeffcient);
		
		p.setVelocity( Util.product(Util.sum(p.getVelocity(), Util.sum(cognitiveComponent, socialComponent)), this.inertiaWeight[step]) );
	}
	
	@Override
	public void search() {
		int counter = 0;
		Double lastMeanEvaluation = 0.0;
		Double currentMeanEvaluation = 0.0;
		Double convergencyCounter = 0.0;
		
		while (counter < this.steps) {
			for (int i=0; i<this.swarm.size(); i++) {
				updateVelocity(this.swarm.get(i), counter);
				updatePosition(this.swarm.get(i));
				updateGlobalBestPosition();
				currentMeanEvaluation += this.swarm.get(i).getEvaluation();
			}
			counter++;
			
			double dif = Math.abs((lastMeanEvaluation - currentMeanEvaluation)/this.swarm.size());
			
			System.out.println("Step: "+counter);
			System.out.println("Mean Evaluation: "+(currentMeanEvaluation/this.swarm.size()));
			System.out.println("Diference from last Evaluation: "+dif);
			System.out.println("gBest Evaluation: "+this.gBest.getBestEvaluation());
			for (Particle particle : swarm) {
				System.out.println(particle.getEvaluation());
			}
			System.out.println();
			
			if (dif <= 0.5)
				convergencyCounter++;
			if (convergencyCounter > 9) {
				this.updateSwarm(currentMeanEvaluation);
				convergencyCounter = -10.0;
			}
	
			lastMeanEvaluation = currentMeanEvaluation;
			currentMeanEvaluation = 0.0;
		}
		
		Result r = new Result();
		
		r.setAlpha(this.gBest.getAlpha());
		r.setBeta(this.gBest.getBeta());
		r.setBias(1.0);
		r.setEvaluation(this.gBest.getEvaluation());
		r.setMedianRate(this.gBest.getScenario().getMedianRate());
		r.setScenario(this.gBest.getScenario());
		r.setRequiredRate(this.scenario.getRequiredRate());
		r.setServingBSs(this.scenario.getServingBSs());
		r.setUesServed(this.scenario.getUesServed());
		r.setSumRate(this.scenario.getSumRate());
		r.setSolution(this.gBest.getPosition());
		
		this.setResult(r);
	}
	
	public void updateSwarm(Double currentEvaluation) {
		
		int maxUpdates = (int) (0.5 * this.swarm.size());
		
		Double qtdMacro = this.scenario.getEnv().getArea() * this.scenario.getEnv().getLambdaMacro();
		int particleDimension = this.scenario.getAllBS().size()-(qtdMacro.intValue());
		
		for (int i=0; i<this.swarm.size(); i++) {
			if (this.swarm.get(i).getEvaluation() < currentEvaluation && maxUpdates > 0) {
				this.swarm.set(i, new Particle(this.alpha, this.beta, this.scenario.clone(), particleDimension));
				maxUpdates--;
			}
		}
			
	}

	@Override
	public void run() {
		this.search();
	}
	
	public static void main(String[] args) {
		String path = "/Users/hugo/Desktop/CRE/CREV1/";
		if (args.length != 0)
			path = args[0];

		CREEnv env = new CREEnv();

		//Setting general simulations parameters
		env.set(Param.area, 1000000.0); 	   // 1 km^2
		env.set(Param.lambdaFemto, 0.00002);   // 0.00002 Femto/m^2 = 20 Femtos  
		env.set(Param.lambdaUser, 0.0003);     // 0.0003 Users/m^2 = 300 Users 
		env.set(Param.lambdaMacro, 0.000002);  // 0.000002 Macros/m^2 = 2 Macros
		env.set(Param.powerMacro, 46.0);	   // dBm
		env.set(Param.powerSmall, 30.0);	   // dBm
		env.set(Param.noisePower, -174.0);	   // dBm/Hz
		env.set(Param.gainMacro, 15.0);		   // dBi
		env.set(Param.gainSmall, 5.0);		   // dBi
		env.set(Param.nSubcarriers, 12);	   // 12 Sub-carriers per Resource Block
		env.set(Param.nOFDMSymbols, 14);	   // 14 OFDM Symbols per subframe
		env.set(Param.subframeDuration, 1.0);  // 1 ms
		env.set(Param.workingDirectory, path); // Working Directory
		env.set(Param.simulations, 50.0);	   // Number of Simulations

		//Setting Parameters to Phase 1: Static Bias		
		env.set(Param.totalBias, 100);
		env.set(Param.biasStep, 1.0);
		env.set(Param.initialBias, -10.0);

		//Setting GA Parameters
		env.set(Param.initialCrossoverProbability, 0.9);
		env.set(Param.finalCrossoverProbability, 0.6);
		env.set(Param.initialMutationProbability, 0.5);
		env.set(Param.finalMutationProbability, 0.9);
		env.set(Param.populationSize, (env.getLambdaSmall() * env.getArea()));
		env.set(Param.generationSize, 100);
		env.set(Param.kElitism, 1);
		env.set(Param.initialGeneRange, -10.0);
		env.set(Param.finalGeneRange, 80.0);

		//Setting PSO Parameters
		env.set(Param.psoSteps, 1200);
		
		Scenario s = new Scenario(env);
		
		VsPSO pso = new VsPSO(1.0, 1.0, s, 150.0, 20, 322);
		pso.search();
	}
}
