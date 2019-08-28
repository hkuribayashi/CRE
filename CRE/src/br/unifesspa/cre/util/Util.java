package br.unifesspa.cre.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.opencsv.CSVWriter;

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

	/**
	 * Returns the mean value of an array
	 * @param values
	 * @return
	 */
	public static Double getMean(Double[] values) {
		DescriptiveStatistics ds = new DescriptiveStatistics();
		for (Double v : values)
			ds.addValue(v);
		return ds.getMean();
	}

	/**
	 * Returns the median valeu of an array
	 * @param values
	 * @return
	 */
	public static Double getMedian(Double[] values) {
		DescriptiveStatistics ds = new DescriptiveStatistics();
		for (Double v : values)
			ds.addValue(v);
		return ds.getPercentile(50.0);
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public static Double getMedian(List<Double> values) {
		Double[] valuesArray = new Double[values.size()];
		valuesArray = values.toArray(valuesArray);
		return Util.getMedian(valuesArray);
	}

	/**
	 * Returns a random Application Profile
	 * @return
	 */
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

	public static Double[] getPhi(int size, double constant) {
		Double[] array = new Double[size];
		for (int i = 0; i < array.length; i++)
			array[i] = Math.random() * constant;

		return array;
	}

	public static Double[] getBoxPlotData(List<Result> results) {

		List<Double> evaluationValues = new ArrayList<Double>();

		for (Result r: results)
			evaluationValues.add(r.getEvaluation());

		Double[] valuesArray = new Double[evaluationValues.size()];
		valuesArray = evaluationValues.toArray(valuesArray);

		return valuesArray;
	}

	public static Result getMean(List<Result> results) {

		Result result = new Result();
		result.setAlpha(results.get(0).getAlpha());
		result.setBeta(results.get(0).getBeta());
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

	public static void writeToCSV(String filePath, Double[] boxplotValues, String header){ 
		File file = new File(filePath); 

		try {  
			FileWriter outputfile = new FileWriter(file);  
			CSVWriter writer = new CSVWriter(outputfile);  
			List<String[]> data = new ArrayList<String[]>(); 
			data.add( new String[] {"Bias "+header} );

			for (int i=0; i<boxplotValues.length; i++)
				data.add(new String[] {String.valueOf(boxplotValues[i])});

			writer.writeAll(data); 
			writer.close(); 
		}catch (IOException e) { 
			e.printStackTrace(); 
		} 
	}
	
	public static void init(Double[] array) {
		for (int i=0; i<array.length; i++)
			array[i] = 0.0;
	}
	
	public static Double[] minus(Double[] a, Double[] b) {
		Double[] c = new Double[a.length];
		for (int i=0; i<c.length; i++) {
			c[i] = a[i] - b[i];
		}
		return c;
	}
	
	public static Double[] product(Double[] a, Double number) {
		for (int i=0; i<a.length; i++) {
			a[i] = a[i] * number * Math.random(); 
		}
		return a;
	}
	
	public static Double[] sum(Double[] a, Double[] b) {
		Double[] c = new Double[a.length];
		for (int i=0; i<a.length; i++) {
			c[i] = a[i] + b[i];
		}
		return c;
	}
	
	public static Double[] sum(Double[] a, Double[] b, Double[] c) {
		Double[] d = new Double[a.length];
		for (int i=0; i<a.length; i++) {
			d[i] = a[i] + b[i] + c[i];
		}
		return d;
	}
}