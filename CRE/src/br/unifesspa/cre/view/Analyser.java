package br.unifesspa.cre.view;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.model.Result;
import br.unifesspa.cre.util.Util;

public class Analyser {

	public static void getExperiment02() {
		DAO<List<List<Result>>> dao = new DAO<List<List<Result>>>();
		String file = "/Users/hugo/Desktop/CRE/CRE/data/experiment2.data";
		if (new File(file).exists()) {
			List<List<Result>> re = dao.restore(file);
			for (List<Result> list : re) {
				double alpha = list.get(0).getAlpha();
				double beta = list.get(0).getBeta();
				Scenario scenario = Collections.max(list).getScenario();
				Util.print(scenario.getBias());
				new Topology(scenario, "e2", alpha, beta).paint();
			}
		}else throw new RuntimeException("File Not Found: "+file);
	}
	
	public static void getC1() {
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		
		String copso = "/Users/hugo/Desktop/CRE/CRE/data/e5-alpha-1.0-beta-1.0.data";
		HashMap<String, List<Result>> results = dao.restore(copso);
		
		System.out.println("Mean: "+Util.getMean(results.get("CoPSO-20")));
		System.out.println("Mean: "+Util.getMean(results.get("CoPSO-40")));
		System.out.println("Mean: "+Util.getMean(results.get("CoPSO-60")));
		System.out.println("Mean: "+Util.getMean(results.get("CoPSO-80")));
		
		String increasepso = "/Users/hugo/Desktop/CRE/CRE/data/e6-alpha-1.0-beta-1.0.data";
		results = dao.restore(increasepso);
		
		System.out.println("Mean: "+Util.getMean(results.get("IncreaseIWPSO-20")));
		System.out.println("Mean: "+Util.getMean(results.get("IncreaseIWPSO-40")));
		System.out.println("Mean: "+Util.getMean(results.get("IncreaseIWPSO-60")));
		System.out.println("Mean: "+Util.getMean(results.get("IncreaseIWPSO-80")));
		
		String vspso = "/Users/hugo/Desktop/CRE/CRE/data/e7-alpha-1.0-beta-1.0.data";
		results = dao.restore(vspso);
		
		System.out.println("Mean: "+Util.getMean(results.get("VsPSO-20")));
		System.out.println("Mean: "+Util.getMean(results.get("VsPSO-40")));
		System.out.println("Mean: "+Util.getMean(results.get("VsPSO-60")));
		System.out.println("Mean: "+Util.getMean(results.get("VsPSO-80")));
	
		String spso = "/Users/hugo/Desktop/CRE/CRE/data/e8-alpha-1.0-beta-1.0.data";
		results = dao.restore(spso);
		
		System.out.println("Mean: "+Util.getMean(results.get("StochasticIWPSO-20")));
		System.out.println("Mean: "+Util.getMean(results.get("StochasticIWPSO-40")));
		System.out.println("Mean: "+Util.getMean(results.get("StochasticIWPSO-60")));
		System.out.println("Mean: "+Util.getMean(results.get("StochasticIWPSO-80")));	
	}
	
	public static void getC2() {
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		
		String copso = "/Users/hugo/Desktop/CRE/CRE/data/e5-alpha-1.0-beta-1.0.data";
		HashMap<String, List<Result>> results = dao.restore(copso);
		
		System.out.println("Max: "+Collections.max(results.get("CoPSO-20")));
		System.out.println("Max: "+Collections.max(results.get("CoPSO-40")));
		System.out.println("Max: "+Collections.max(results.get("CoPSO-60")));
		System.out.println("Max: "+Collections.max(results.get("CoPSO-80")));
		
		String increasepso = "/Users/hugo/Desktop/CRE/CRE/data/e6-alpha-1.0-beta-1.0.data";
		results = dao.restore(increasepso);
		
		System.out.println("Max: "+Collections.max(results.get("IncreaseIWPSO-20")));
		System.out.println("Max: "+Collections.max(results.get("IncreaseIWPSO-40")));
		System.out.println("Max: "+Collections.max(results.get("IncreaseIWPSO-60")));
		System.out.println("Max: "+Collections.max(results.get("IncreaseIWPSO-80")));
		
		String vspso = "/Users/hugo/Desktop/CRE/CRE/data/e7-alpha-1.0-beta-1.0.data";
		results = dao.restore(vspso);
		
		System.out.println("Max: "+Collections.max(results.get("VsPSO-20")));
		System.out.println("Max: "+Collections.max(results.get("VsPSO-40")));
		System.out.println("Max: "+Collections.max(results.get("VsPSO-60")));
		System.out.println("Max: "+Collections.max(results.get("VsPSO-80")));
	
		String spso = "/Users/hugo/Desktop/CRE/CRE/data/e8-alpha-1.0-beta-1.0.data";
		results = dao.restore(spso);
		
		System.out.println("Max: "+Collections.max(results.get("StochasticIWPSO-20")));
		System.out.println("Max: "+Collections.max(results.get("StochasticIWPSO-40")));
		System.out.println("Max: "+Collections.max(results.get("StochasticIWPSO-60")));
		System.out.println("Max: "+Collections.max(results.get("StochasticIWPSO-80")));
		
	}
	
	public static void getBoxPlotCoPSO() {
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		
		String copso = "/Users/hugo/Desktop/CRE/CRE/data/e5-alpha-1.0-beta-1.0.data";
		HashMap<String, List<Result>> results = dao.restore(copso);
		
		System.out.println("CoPSO-20");
		for (Result r : results.get("CoPSO-20")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("CoPSO-40");
		for (Result r : results.get("CoPSO-40")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("CoPSO-60");
		for (Result r : results.get("CoPSO-60")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("CoPSO-80");
		for (Result r : results.get("CoPSO-80")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
	}
	
	public static void getBoxPlotIncreasePSO() {
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		
		String pso = "/Users/hugo/Desktop/CRE/CRE/data/e6-alpha-1.0-beta-1.0.data";
		HashMap<String, List<Result>> results = dao.restore(pso);
		
		System.out.println("IncreaseIWPSO-20");
		for (Result r : results.get("IncreaseIWPSO-20")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("IncreaseIWPSO-40");
		for (Result r : results.get("IncreaseIWPSO-40")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("IncreaseIWPSO-60");
		for (Result r : results.get("IncreaseIWPSO-60")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("IncreaseIWPSO");
		for (Result r : results.get("IncreaseIWPSO-80")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
	}
	
	public static void getBoxPlotVsPSO() {
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		
		String pso = "/Users/hugo/Desktop/CRE/CRE/data/e7-alpha-1.0-beta-1.0.data";
		HashMap<String, List<Result>> results = dao.restore(pso);
		
		System.out.println("VsPSO-20");
		for (Result r : results.get("VsPSO-20")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("VsPSO-40");
		for (Result r : results.get("VsPSO-40")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("VsPSO-60");
		for (Result r : results.get("VsPSO-60")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("VsPSO");
		for (Result r : results.get("VsPSO-80")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
	}
	
	public static void getBoxPlotStochasticPSO() {
		DAO<HashMap<String, List<Result>>> dao = new DAO<HashMap<String, List<Result>>>();
		
		String pso = "/Users/hugo/Desktop/CRE/CRE/data/e8-alpha-1.0-beta-1.0.data";
		HashMap<String, List<Result>> results = dao.restore(pso);
		
		System.out.println("StochasticIWPSO-20");
		for (Result r : results.get("StochasticIWPSO-20")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("StochasticIWPSO-40");
		for (Result r : results.get("StochasticIWPSO-40")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("StochasticIWPSO-60");
		for (Result r : results.get("StochasticIWPSO-60")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
		System.out.println("StochasticIWPSO-80");
		for (Result r : results.get("StochasticIWPSO-80")) {
			System.out.print(r.getUesServed()+", ");
		}
		System.out.println();
		
	}
	
	public static void main(String[] args) {
		Analyser.getC2();
	}
}
