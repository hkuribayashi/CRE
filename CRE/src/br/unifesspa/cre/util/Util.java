package br.unifesspa.cre.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import br.unifesspa.cre.ga.NetworkElement;
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
	 * Returns a sample from a Gamma Distribuition (shape, scale)
	 * @param shape
	 * @param scale
	 * @return
	 */
	public static Double getGammaDistribution(Double shape, Double scale) {

		if (shape <= 0.0)
			shape = 1.0;

		return new GammaDistribution(shape, scale).sample();
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
	 * Returns a sample from a Normal Distribuition (mean, variance)
	 * @param mean
	 * @param variance
	 * @return
	 */
	public static Double getNormalDistribution(Double mean, Double variance) {

		return new NormalDistribution(mean, Math.sqrt(variance)).sample();
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
	public static void print(Double[] array) {
		Double sum = 0.0;
		for (int i=0; i<=array.length-1; i++) {
			sum += array[i];
			System.out.println("Element ["+i+"]: "+array[i]);
		}
	}

	public static void printD(NetworkElement[][] network) {

		for (int i=0; i<network.length; i++) {
			for (int j=0; j<network[0].length; j++) {
				System.out.print(network[i][j].getDistance()+" ");
			}
			System.out.print("\n");
		}
	}

	public static void printS(NetworkElement[][] network) {

		for (int i=0; i<network.length; i++) {
			for (int j=0; j<network[0].length; j++) {
				System.out.print(network[i][j].getSinr()+" ");
			}
			System.out.print("\n");
		}
	}

	public static void printC(NetworkElement[][] network) {

		for (int i=0; i<network.length; i++) {
			for (int j=0; j<network[0].length; j++) {
				System.out.print(network[i][j].getCoverageStatus()+" ");
			}
			System.out.print("\n");
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

	/**
	 * 
	 * @param matrix
	 */
	public static void init(Double[][] matrix) {
		for (int i=0; i<matrix.length; i++)
			for (int j=0; j<matrix[0].length; j++)
				matrix[i][j] = 0.0;
	}

	public static void init(Double[] array) {
		for (int i=0; i<array.length; i++)			
			array[i] = 0.0;
	}

	public static HashMap<String, Double> getChromossomeRange(List<Result> results) {
		List<Double> biasValues = new ArrayList<Double>();
		for (Result rs : results)
			biasValues.add(rs.getBias());
		Collections.sort(biasValues);
		Double max = Collections.max(biasValues);
		Double min = Collections.min(biasValues);

		HashMap<String, Double> hm = new HashMap<String, Double>();
		hm.put("maxBias", max);
		hm.put("minBias", min);

		return hm;
	}

	public static List<Double> getConfidenceInterval(Double[] values, Double level) {

		if (values.length == 0)
			throw new RuntimeException("Aqui");

		SummaryStatistics s = new SummaryStatistics();
		for (Double value : values)
			s.addValue(value);

		TDistribution tDist = new TDistribution(s.getN() - 1);
		double critVal = tDist.inverseCumulativeProbability(1.0 - (1 - level) / 2);
		Double ci = critVal * s.getStandardDeviation() / Math.sqrt(s.getN());

		double lower = s.getMean() - ci;
		double upper = s.getMean() + ci;

		List<Double> finalValues = new ArrayList<Double>();
		for (Double value : values) {
			if (value >= lower && value <= upper)
				finalValues.add(value);
		}

		if (finalValues.size() == 0)
			finalValues = Arrays.asList(values);

		return finalValues;
	}
}