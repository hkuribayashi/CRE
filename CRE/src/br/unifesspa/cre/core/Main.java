package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.util.Util;

/**
 * 
 * @author hugo
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		Double[] biasOffset = {-1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0};
		Double simulations = 1000.0;
		CREEnv env;
		Scenario s;
		Double[] results = new Double[biasOffset.length];
		Double sum = 0.0; 

		for (int i=0; i<biasOffset.length; i++) {

			for (int j=0; j<simulations; j++) {

				env = new CREEnv();
				env.set(Param.area, 1000000.0); 	// 1 km^2
				env.set(Param.lambdaFemto, 0.00002); // 0.00002 Femto/m^2 = 20 Femtos  
				env.set(Param.lambdaUser, 0.0001); // 0.0001 Users/m^2 = 100 Users 
				env.set(Param.lambdaMacro, 0.000002); // 0.000002 Macros/m^2 = 2 Macros
				env.set(Param.bias, biasOffset[i]); //CRE Bias

				s = new Scenario(env);
				s.getDistance(); 
				s.getInitialSINR();
				s.getCoverageMatrix();
				s.getBSLoad();
				s.getInitialBitRate();
				s.getFinalBitRate();
				sum += s.getSumRate();
			}
			
			results[i] = (sum/simulations);

		}
		
		Util.print(results);

		//JFrame window = new JFrame();
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setBounds(30, 30, 1000, 1000);
		//window.getContentPane().add(new Topology(s));
		//window.setVisible(true);	
	}
}