package br.unifesspa.cre.core;

import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.ga.GA;
import br.unifesspa.cre.hetnet.Scenario;

public class Main2 {

	public static void main(String[] args) {

		String path = "/Users/hugo/Desktop/data.cre";
		
		DAO<List<Double>> dao = new DAO<List<Double>>();
		
		if (dao.verifyPath(path)) {
			
			List<Double> biasSelected = dao.restore(path);

			CREEnv env = new CREEnv();
			
			env.set(Param.area, 1000000.0); 	  		// 1 km^2
			env.set(Param.lambdaFemto, 0.00002);  		// 0.00002 Femto/m^2 = 20 Femtos  
			env.set(Param.lambdaUser, 0.0001);    		// 0.0001 Users/m^2 = 100 Users 
			env.set(Param.lambdaMacro, 0.000002); 		// 0.000002 Macros/m^2 = 2 Macros

			env.set(Param.initialCrossoverProbability, 0.9);
			env.set(Param.finalCrossoverProbability, 0.6);
			env.set(Param.initialMutationProbability, 0.2);
			env.set(Param.finalMutationProbability, 0.8);

			env.set(Param.initialGeneRange, 0.9);
			env.set(Param.finalGeneRange, 1.45);

			env.set(Param.initialSugestedIndividual, Collections.min(biasSelected));
			env.set(Param.finalSugestedInvidual, Collections.max(biasSelected));
			
			env.set(Param.populationSize, 20);
			env.set(Param.generationSize, 50);
			env.set(Param.kElitism, 2);

			Scenario s = new Scenario(env);
			s.getDistance();

			GA ga = new GA(s);
			ga.evolve();
			
			
		}else System.out.println("Error: "+path+" not found.");
	}
}