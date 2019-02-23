package br.unifesspa.cre.core;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.hetnet.Scenario;

/**
 * 
 * @author hugo
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		//String path = "C:/Users/hugo/Desktop/CRE/cre1.data";
		
		CREEnv env;
		Scenario s;

		env = new CREEnv();
		env.set(Param.area, 1000000.0); 	// 1 km^2
		env.set(Param.lambdaFemto, 0.00002); // 0.00002 Femto/m^2 = 20 Femtos  
		env.set(Param.lambdaUser, 0.0001); // 0.0001 Users/m^2 = 100 Users 
		env.set(Param.lambdaMacro, 0.000002); // 0.000002 Macros/m^2 = 2 Macros
		env.set(Param.bias, 0.0); //CRE Bias

		s = new Scenario(env);
		s.getDistance(); 
		s.getInitialSINR();
		s.getCoverageMatrix();
		s.getBSLoad();
		s.getInitialBitRate();
		s.getFinalBitRate();
		System.out.println(s.getSumRate());
		
		env = new CREEnv();
		env.set(Param.area, 1000000.0); 	// 1 km^2
		env.set(Param.lambdaFemto, 0.00002); // 0.00002 Femto/m^2 = 20 Femtos  
		env.set(Param.lambdaUser, 0.0001); // 0.0001 Users/m^2 = 100 Users 
		env.set(Param.lambdaMacro, 0.000002); // 0.000002 Macros/m^2 = 2 Macros
		env.set(Param.bias, 3.0); //CRE Bias

		s = new Scenario(env);
		s.getDistance(); 
		s.getInitialSINR();
		s.getCoverageMatrix();
		s.getBSLoad();
		s.getInitialBitRate();
		s.getFinalBitRate();
		System.out.println(s.getSumRate());

		//JFrame window = new JFrame();
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setBounds(30, 30, 1000, 1000);
		//window.getContentPane().add(new Topology(s));
		//window.setVisible(true);	
	}
}