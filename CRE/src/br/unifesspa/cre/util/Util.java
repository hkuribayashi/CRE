package br.unifesspa.cre.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import br.unifesspa.cre.hetnet.ApplicationProfile;
import br.unifesspa.cre.hetnet.BSType;
import br.unifesspa.cre.hetnet.Point;
import br.unifesspa.cre.model.Result;

public class Util {

	/**
	 * 
	 * @param lower
	 * @param upper
	 * @return
	 */
	public static Integer getUniformIntegerDistribution(Integer lower, Integer upper) {
		return new UniformIntegerDistribution(lower, upper).sample();
	}

	/**
	 * Returns a sample froam a Real Uniform Distribution (lower, upper)
	 * @param lower
	 * @param upper
	 * @return
	 */
	public static Double getUniformRealDistribution(Double lower, Double upper) {
		return new UniformRealDistribution(lower, upper).sample();
	}

	/**
	 * Returns euclidian distance between Points A and B
	 * @param a
	 * @param b
	 * @return
	 */
	public static Double getDistance(Point a, Point b) {

		Double term = Math.pow((b.getX() - a.getX()),2) + Math.pow((b.getY() - a.getY()),2) + Math.pow((b.getZ() - a.getZ()),2);

		return Math.sqrt(term);

	}

	/**
	 * 
	 * @param list
	 */
	public static void print(List<Result> list) {
		for (Result result : list) {
			System.out.println(result);
		}
		System.out.println();
	}

	/**
	 * 
	 * @param matrix
	 */
	public static void print(Double[][] matrix) {
		for (int i=0; i<=matrix.length-1; i++) {
			System.out.println();
			for (int j=0; j<=matrix[0].length-1; j++)
				System.out.print("["+i+"-"+j+"]: "+matrix[i][j]+ "  ");
		}
	}

	/**
	 * 
	 * @param array
	 */
	public static void print(double[] array) {
		Double sum = 0.0;
		for (int i=0; i<=array.length-1; i++) {
			sum += array[i];
			System.out.println("Element ["+i+"]: "+array[i]);
		}
	}
	
	/**
	 * 
	 * @param array
	 */
	public static void print(Double[] array) {
		Double sum = 0.0;
		for (int i=0; i<=array.length-1; i++) {
			sum += array[i];
			System.out.println("Element ["+i+"]: "+array[i]);
		}
	}

	/**
	 * 
	 * @param lambda
	 * @param area
	 * @param height
	 * @return
	 */
	public static <T> List<Point> getHPPP(Double lambda, Double area, Double height) {

		Double lower = 0.0;
		Double upper = Math.sqrt(area);
		Double nPoints = lambda * area;

		Double[] x = new Double[nPoints.intValue()];	
		Double[] y = new Double[nPoints.intValue()];
		Double[] z = new Double[nPoints.intValue()];

		for (int i=0; i<=nPoints-1; i++)
			x[i] = new UniformRealDistribution(lower, upper).sample();

		for (int i=0; i<=nPoints-1; i++)
			y[i] = new UniformRealDistribution(lower, upper).sample();

		for (int i=0; i<=nPoints-1; i++)
			z[i] = new UniformRealDistribution(lower, height).sample();

		List<Point> points = new ArrayList<Point>();
		for (int i=0; i<=nPoints-1; i++) {
			Point p = new Point(x[i], y[i], z[i]);
			points.add(p);
		}

		return points;

	}

	/**
	 * 
	 * @param type
	 * @param distance
	 * @return
	 */
	public static Double getPathLoss(BSType type, Double distance, Double txGain) {

		Double pathLoss;

		if (type.equals(BSType.Macro)) {
			pathLoss = 128.1 + 37.6 * Math.log10( (Math.max(distance, 35.0)/1000.0) ) - txGain;
		}else pathLoss = 140.7 + 36.7 * Math.log10( (Math.max(distance,10.0)/1000.0) ) - txGain;

		return pathLoss;
	}
	
	public static Result getMean(List<Result> results) {
		
		Result result = new Result();
		result.setScenario(results.get(0).getScenario());
		
		List<Double> biasValues = new ArrayList<Double>();
		List<Double> evaluationValues = new ArrayList<Double>();
		List<Double> sumRateValues = new ArrayList<Double>();
		List<Double> medianRateValues = new ArrayList<Double>();
		List<Double> uesServedValues = new ArrayList<Double>();
		List<Double> servingBSValues = new ArrayList<Double>();
		List<Double> requiredRateValues = new ArrayList<Double>();
		
		for (Result r: results) {
			biasValues.add(r.getBias());
			evaluationValues.add(r.getEvaluation());
			sumRateValues.add(r.getSumRate());
			medianRateValues.add(r.getMedianRate());
			uesServedValues.add(r.getUesServed());
			servingBSValues.add(r.getServingBSs());
			requiredRateValues.add(r.getRequiredRate());	
		}
		
		result.setBias( Util.getMeanValue( biasValues ) );
		result.setEvaluation( Util.getMeanValue(evaluationValues) );
		result.setSumRate( Util.getMeanValue(sumRateValues) );
		result.setMedianRate( Util.getMeanValue( medianRateValues ));
		result.setUesServed( Util.getMeanValue( uesServedValues ) );
		result.setServingBSs( Util.getMeanValue( servingBSValues ) );
		result.setRequiredRate( Util.getMeanValue( requiredRateValues ) );
		
		return result;
	}
	
	public static Double getMeanValue(List<Double> values) {
		
		Double[] valuesArray = new Double[values.size()];
		valuesArray = values.toArray(valuesArray);
		
		DescriptiveStatistics ds = new DescriptiveStatistics();
		for (Double v : valuesArray)
			ds.addValue(v);
	
		return ds.getMean();
	}
	
	

	public static Double getMedian(Double[] values) {
		DescriptiveStatistics ds = new DescriptiveStatistics();
		for (Double v : values)
			ds.addValue(v);
		return ds.getPercentile(50.0);
	}

	public static Double getMedian(List<Double> values) {
		Double[] valuesArray = new Double[values.size()];
		valuesArray = values.toArray(valuesArray);
		return Util.getMedian(valuesArray);
	}

	public static ApplicationProfile getApplicationProfile() {
		ApplicationProfile ap;
		Integer sample = Util.getUniformIntegerDistribution(1, 8);

		switch (sample){
		case 1: ap = ApplicationProfile.VirtualReality; break;
		case 2: ap = ApplicationProfile.FactoryAutomation; break;
		case 3: ap = ApplicationProfile.DataBackup; break;
		case 4: ap = ApplicationProfile.SmartGrid; break;
		case 5: ap = ApplicationProfile.SmartHome; break;
		case 6: ap = ApplicationProfile.Medical; break;
		case 7: ap = ApplicationProfile.EnvironmentalMonitoring; break;
		default: ap = ApplicationProfile.TactileInternet; break;
		}

		return ap;
	}
}