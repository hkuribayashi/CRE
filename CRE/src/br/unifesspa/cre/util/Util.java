package br.unifesspa.cre.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import br.unifesspa.cre.hetnet.Point;

public class Util {

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

	/**
	 * 
	 * @param lambda
	 * @param area
	 * @param height
	 * @return
	 */
	public static List<Point> getHPPP(Double lambda, Double area, Double height) {

		Double lower = 0.0;
		Double upper = Math.sqrt(area);
		Double nPoints = lambda * area;

		Double[] x = new Double[nPoints.intValue()];	
		Double[] y = new Double[nPoints.intValue()];	

		for (int i=0; i<=nPoints-1; i++)
			x[i] = new UniformRealDistribution(lower, upper).sample();

		for (int i=0; i<=nPoints-1; i++)
			y[i] = new UniformRealDistribution(lower, upper).sample();

		List<Point> points = new ArrayList<Point>();
		for (int i=0; i<=nPoints-1; i++) {
			Point p = new Point(x[i], y[i], height);
			points.add(p);
		}

		return points;

	}

	/**
	 * 
	 * @param distance distance between a user and a BS
	 * @param d0 reference distance
	 * @param alpha path-loss exponent
	 * @param c constant
	 * @return path-loss
	 */
	public static Double getPathLoss(Double distance, Double d0, Double alpha, Double c) {
		
		 Double aux = c * Math.pow(Math.max(d0, distance), alpha) ;
		 
		 return Math.pow(aux, -1);
		
	}
	
	public static void initMatrix(Double[][] matrix) {
		
		for (int i=0; i<matrix.length; i++)
			for (int j=0; j<matrix[0].length; j++)
				matrix[i][j] = 0.0;
		
	}
	
	

}