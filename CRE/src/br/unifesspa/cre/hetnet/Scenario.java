package br.unifesspa.cre.hetnet;

import java.io.Serializable;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.util.Util;

public class Scenario implements Serializable{

	private static final long serialVersionUID = -1736505791936110187L;

	private Integer id;

	private CREEnv env;

	private List<Point> macroPoints;

	private List<Point> femtoPoints;

	private List<Point> userPoints;

	private Double[][] distanceMatrix;

	private Double[][] sinr;
	
	private Double[] maxSinr;
	
	private Double[] numberUEsPerBS;
	
	private Double[][] coverageMatrix;

	public Scenario(CREEnv env) {

		/* Set an id for this scenario */
		this.id = 1;

		/* Parameters for scenario */
		this.env = env;

		/* Generates Macro, User and Femto locations from a Homogeneus Poisson Point Process */
		this.macroPoints = Util.getHPPP(this.env.getLambdaMacro(), this.env.getArea(), this.env.getHeightMacro());
		this.femtoPoints = Util.getHPPP(this.env.getLambdaFemto(), this.env.getArea(), this.env.getHeightFemto());
		this.userPoints = Util.getHPPP(this.env.getLambdaUser(), this.env.getArea(), this.env.getHeightUser());

		/* Calculates the distance between Users and Femto BS's and Between Users and Macro BS's  */
		this.getDistance();
		
		/* Calculates the SINR Metric */
		this.getInitialSINR();
		
		/* Calculates the Coverage Matrix */
		this.getCoverageMatrix();

	}

	/**
	 * Calculate the distance between Users and Femto BS's and Between Users and Macro BS's
	 */
	public void getDistance() {

		this.distanceMatrix = new Double [userPoints.size()][femtoPoints.size() + macroPoints.size()];
		
		for (int i=0; i<userPoints.size(); i++) {
			Point uePoint = userPoints.get(i);
			for (int j=0; j<femtoPoints.size(); j++) {
				Point femtoPoint = femtoPoints.get(j);	
				this.distanceMatrix[i][j] = Util.getDistance(uePoint, femtoPoint);
			}
			
			for (int j=0; j<macroPoints.size(); j++) {
				Point macroPoint = macroPoints.get(j);
				this.distanceMatrix[i][j+femtoPoints.size()] = Util.getDistance(uePoint, macroPoint);
			}
		}
	}

	/**
	 * Calculate the SINR for All BS
	 */
	public void getInitialSINR() {

		double UkFemto = Math.floor(this.env.getSpatialLoadFactorFemto() * this.env.getAntennasFemto() * this.femtoPoints.size());		
		
		double UkMacro = Math.floor(this.env.getSpatialLoadFactorMacro() * this.env.getAntennasMacro() * this.macroPoints.size());
		
		double deltaKFemto = (this.env.getAntennasFemto() - UkFemto + 1.0);
		double deltaKMacro = (this.env.getAntennasMacro() - UkMacro + 1.0);
		
		double d0 = this.env.getReferenceDistance();
		
		double c = this.env.getConstant();
		
		double alpha = this.env.getAlpha();
		
		this.sinr = new Double [this.distanceMatrix.length][this.distanceMatrix[0].length];
		
		for (int i=0; i<sinr.length; i++) {
			
			for (int j=0; j<(sinr[0].length - this.macroPoints.size()); j++) {				
				double distance = distanceMatrix[i][j]; 		
				this.sinr[i][j] = (this.env.getPowerFemto() / UkFemto)* Util.getGammaDistribution(deltaKFemto, 1.0) * Util.getPathLoss(distance, d0, alpha, c);				
				this.sinr[i][j] = this.sinr[i][j] / (this.env.getNoisePower());
			}
			
			for (int j=(sinr[0].length - this.macroPoints.size()); j<this.sinr[0].length; j++) {
				double distance = distanceMatrix[i][j];
				this.sinr[i][j] = (this.env.getPowerMacro() / UkMacro)* Util.getGammaDistribution(deltaKMacro, 1.0) * Util.getPathLoss(distance, d0, alpha, c);
				this.sinr[i][j] = this.sinr[i][j] / (this.env.getNoisePower());
			}
		}	
	}
	
	public void getCoverageMatrix() {
		
		this.coverageMatrix = new Double[this.sinr.length][this.sinr[0].length];
		Util.initMatrix(this.coverageMatrix);
		
		this.maxSinr = new Double[this.sinr.length];
		
		for (int i=0; i<this.sinr.length; i++) {
			
			double max = (Double.MIN_VALUE * (-1.0));
			int max_i = -1, max_j= -1;
			
			for (int j=0; j<this.sinr[0].length; j++) {
		
				if (this.sinr[i][j] > max) {
					max = this.sinr[i][j];
					max_i = i;
					max_j = j;
				}else {
					System.out.println(this.sinr[i][j]);
					System.out.println("T");
				}
			}
			
			this.maxSinr[i] = max;
			this.coverageMatrix[max_i][max_j] = 1.0;
		}
		
		Util.print(this.coverageMatrix);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CREEnv getEnv() {
		return env;
	}

	public void setEnv(CREEnv env) {
		this.env = env;
	}

	public List<Point> getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(List<Point> userPoints) {
		this.userPoints = userPoints;
	}

	public List<Point> getFemtoPoints() {
		return femtoPoints;
	}

	public void setFemtoPoints(List<Point> femtoPoints) {
		this.femtoPoints = femtoPoints;
	}

	public List<Point> getMacroPoints() {
		return macroPoints;
	}

	public void setMacroPoints(List<Point> macroPoints) {
		this.macroPoints = macroPoints;
	}

	public Double[][] getDistanceMatrix() {
		return distanceMatrix;
	}

	public void setDistanceMatrix(Double[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double[] getNumberUEsPerBS() {
		return numberUEsPerBS;
	}

	public void setNumberUEsPerBS(Double[] numberUEsPerBS) {
		this.numberUEsPerBS = numberUEsPerBS;
	}
}