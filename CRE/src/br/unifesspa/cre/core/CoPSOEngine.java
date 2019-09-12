package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.pso.CoPSO;

public class CoPSOEngine extends PSOEngine{


	public CoPSOEngine(Double alpha, Double beta, CREEnv env, Integer swarmSize) {
		super(alpha, beta, env, swarmSize);
	}

	public CoPSOEngine(Double alpha, Double beta, Scenario scenario, Integer swarmSize) {
		super(alpha, beta, scenario, swarmSize);
	}

	@Override
	public void run() {
		List<Result> results = new ArrayList<Result>();
		double targetSolution = (this.alpha * this.scenario.getUe().size()) + (this.beta * this.scenario.getAllBS().size());
		CoPSO[] pso = new CoPSO[this.env.getSimulations()];
		for (int i=0; i<pso.length; i++) {
			pso[i] = new CoPSO(this.alpha, this.beta, this.scenario.clone(), this.env.getPsoSteps(), this.swarmSize, targetSolution);
		}

		Thread[] psoThreads = new Thread[this.env.getSimulations()];
		for (int i=0; i<psoThreads.length; i++) {
			psoThreads[i] = new Thread(pso[i]);
		}

		for (int i=0; i<psoThreads.length; i++) {
			psoThreads[i].start();
		}

		try {
			for (int i=0; i<psoThreads.length; i++)
				psoThreads[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<psoThreads.length; i++) {
			results.add( pso[i].getResult() );
			
		}
		this.setResults(results);
	}
	
	public static void main(String[] args) {
		
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
		env.set(Param.workingDirectory, ""); // Working Directory
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
		env.set(Param.psoSteps, 100);
		
		Scenario scenario = new Scenario(env);
		
		CoPSO pso = new CoPSO(10.0, 1.0, scenario, 100.0, 20, 3022.0);
		pso.search();
		
		System.out.println( pso.getResult() );
		
		
		
	}
}
