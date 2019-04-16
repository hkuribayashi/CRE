package br.unifesspa.cre.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

/**
 * 
 * @author hugo
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		int totalBias = 121;
		Double initialBias = -3.0;
		Double[] biasOffset = new Double[totalBias];
		for (int i=0; i<biasOffset.length; i++)
			biasOffset[i] = initialBias + (0.05 * i);
		
		Util.print(biasOffset);
		
		int kBestResult = 6;		
		Long simulations = 2L;
		Double sum = 0.0;
		Scenario s= null;
		List<Result> results = new ArrayList<Result>();

		for (int i=0; i<biasOffset.length; i++) {
			
			long startTime = System.nanoTime();

			for (int j=0; j<simulations; j++) {

				CREEnv env = new CREEnv();
				env.set(Param.area, 1000000.0); 	  // 1 km^2
				env.set(Param.lambdaFemto, 0.00002);  // 0.00002 Femto/m^2 = 20 Femtos  
				env.set(Param.lambdaUser, 0.0001);    // 0.0001 Users/m^2 = 100 Users 
				env.set(Param.lambdaMacro, 0.000002); // 0.000002 Macros/m^2 = 2 Macros

				s = new Scenario(env);
				
				s.initBias(biasOffset[i]);
				s.getDistance(); 
				s.getInitialSINR();
				s.getCoverageMatrix();
				s.getBSLoad();
				s.getInitialBitRate();
				s.getFinalBitRate();
				sum += s.getSumRate();
			}
			
			long endTime = System.nanoTime();
			long elapsedTime = endTime - startTime;
		
			TimeUnit time = TimeUnit.MILLISECONDS;
			
			Result r = new Result();
			r.setExecutionTime((time.convert(elapsedTime, TimeUnit.NANOSECONDS))/simulations);
			r.setBias(biasOffset[i]);
			r.setSumRate((sum/simulations));
			r.setMedianRate(0.0);
			results.add(r);
			
			System.out.println(r);
			
			sum = 0.0;
		}
		
		Collections.sort(results);
		List<Double> biasSelected = new ArrayList<Double>();
		for (int i=0; i<kBestResult; i++)
			biasSelected.add(results.get(i).getBias());
		
		String path = "/Users/hugo/Desktop/data.cre";
		DAO<List<Double>> dao = new DAO<List<Double>>();
		dao.save(biasSelected, path);
			
		/*JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 1000, 1000);
		window.getContentPane().add(new Topology(s));
		window.setVisible(true);*/	
	}
}