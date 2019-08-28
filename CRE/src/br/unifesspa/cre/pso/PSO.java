package br.unifesspa.cre.pso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

/**
 * Classical PSO
 * 
 * @author hugo
 *
 */
public class PSO {

	private Scenario scenario;

	private List<Particle> swarm;

	private Double steps;

	private Particle gBest;

	private Double alpha;

	private Double beta;
	
	public static final Double cognitiveCoeffcient = 2.05;
	
	public static final Double socialCoeffcient = 2.05;
	
	public static final Double initialInertialWeight = 0.9;
	
	public static final Double finalInertialWeight = 0.6;
	
	private Double[] inertiaWeight;

	public PSO(Double alpha, Double beta, Scenario scenario, Double steps, Integer swarmSize, Double targetSolution) {
		this.alpha = alpha;
		this.beta = beta;
		this.scenario = scenario;
		this.steps = steps;
		
		Double qtdMacro = this.scenario.getEnv().getArea() * this.scenario.getEnv().getLambdaMacro();
		int particleDimension = this.scenario.getAllBS().size()-(qtdMacro.intValue());
		
		this.inertiaWeight = new Double[this.steps.intValue()];
		
		this.swarm = new ArrayList<Particle>();
		for (int i=0; i<swarmSize; i++)
			this.swarm.add(new Particle(this.alpha, this.beta, this.scenario, particleDimension));
		
		this.gBest = Collections.max(this.swarm);
		
		double b = PSO.initialInertialWeight;
		double a = (PSO.finalInertialWeight - b)/this.steps;
		
		for (int i=0; i<inertiaWeight.length; i++)
			this.inertiaWeight[i] = (a * i) + b;
		
	}
	
	public void updateVelocity(Particle p, Integer step) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, PSO.cognitiveCoeffcient);
		
		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, PSO.socialCoeffcient);
		
		p.setVelocity( Util.product(Util.sum(p.getVelocity(), Util.sum(cognitiveComponent, socialComponent)), this.inertiaWeight[step]) );
	}
	
	public void updatePosition(Particle p) {
		p.setPosition( Util.sum(p.getPosition(), p.getVelocity()) );
		p.evaluation();
		p.updateBestPosition();
	}
	
	public void updateGlobalBestPosition() {
		Particle gBestCandidate = Collections.max(this.swarm);
		if (gBestCandidate.getEvaluation() > this.gBest.getEvaluation()) {
			this.gBest = (Particle) gBestCandidate.clone(); 
		}
	}
	
	public Result search() {
		int counter = 0;
		Double meanEvaluation = 0.0;
		
		while (counter < this.steps) {
			for (int i=0; i<this.swarm.size(); i++) {
				updateVelocity(this.swarm.get(i), counter);
				updatePosition(this.swarm.get(i));
				updateGlobalBestPosition();
				meanEvaluation += this.swarm.get(i).getEvaluation();
			}
			counter++;
			
			System.out.println("Step: "+counter);
			System.out.println(this.gBest.getEvaluation());
			System.out.println("Mean Evaluation: "+(meanEvaluation/this.swarm.size()));
			System.out.println();
			meanEvaluation = 0.0;
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
		
		return r;
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
		env.set(Param.psoSteps, 100);
		
		Scenario scenario = new Scenario(env);
		
		PSO pso = new PSO(1.0, 1.0, scenario, 150.0, 60, 320.0);
		pso.search();
	}
	
}
