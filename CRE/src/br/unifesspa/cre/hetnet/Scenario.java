package br.unifesspa.cre.hetnet;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	private Double[] numberUEsPerBS;
	
	private Double[][] coverageMatrix;
	
	private Double[] bsLoadMatrix;
	
	private Double[][] bitrateMatrix;
	
	private Double[] individualBitrateMatrix;
	
	private Double sumRate;

	public Scenario(CREEnv env) {

		/* Set an id for this scenario */
		this.id = 1;

		/* Parameters for scenario */
		this.env = env;

		/* Generate Macro, User and Femto locations from a Homogeneus Poisson Point Process */
		this.macroPoints = new ArrayList<Point>();
		this.macroPoints.add(new Point(200.0, 200.0, this.env.getHeightMacro()));
		this.macroPoints.add(new Point(800.0, 800.0, this.env.getHeightMacro()));
		
		this.femtoPoints = Util.getHPPP(this.env.getLambdaFemto(), this.env.getArea(), this.env.getHeightFemto());
		this.userPoints = Util.getHPPP(this.env.getLambdaUser(), this.env.getArea(), this.env.getHeightUser());

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
		
		this.sinr = new Double [this.distanceMatrix.length][this.distanceMatrix[0].length];
		
		for (int i=0; i<sinr.length; i++) {
			
			for (int j=0; j<(sinr[0].length - this.macroPoints.size()); j++) {				
				double distance = distanceMatrix[i][j]; 						
				this.sinr[i][j] = (this.env.getPowerFemto() * Util.getChannelGain(BSType.Femto, distance, 16.0));
				
				Double aux = 0.0;
				
				for (int k=0; k<(sinr[0].length - this.macroPoints.size()); k++) {	
					if (k != j) {
						double distanceK = distanceMatrix[i][k]; 		
						aux += (this.env.getPowerFemto() * Util.getChannelGain(BSType.Femto, distanceK, 16.0));
					}
				}
				
				this.sinr[i][j] = this.sinr[i][j]/(aux + this.env.getNoisePower()) + this.env.getBias();
			}
			
			for (int j=(sinr[0].length - this.macroPoints.size()); j<this.sinr[0].length; j++) {
				double distance = distanceMatrix[i][j]; 						
				this.sinr[i][j] = (this.env.getPowerMacro() * Util.getChannelGain(BSType.Macro, distance, 36.0));
				
				Double aux = 0.0;
			
				for (int k=(sinr[0].length - this.macroPoints.size()); k<this.sinr[0].length; k++) {					
					if (k != j) {
						double distanceK = distanceMatrix[i][k]; 
						aux += (this.env.getPowerMacro() * Util.getChannelGain(BSType.Macro, distanceK, 36.0));
					}
				}
				
				this.sinr[i][j] = this.sinr[i][j]/(aux + this.env.getNoisePower()) + this.env.getBias();

			}
		}
	}
	
	public void getCoverageMatrix() {
		
		this.coverageMatrix = new Double[this.sinr.length][this.sinr[0].length];
		Util.init(this.coverageMatrix);
		
		for (int i=0; i<this.sinr.length; i++) {
			
			Double max = Double.MAX_VALUE * (-1);
			Integer index_max = -1;
						
			for (int j=0; j<this.sinr[0].length; j++) {		
				
				if (this.sinr[i][j] > max) {
					max = this.sinr[i][j];
					index_max = j;
				}
			}
			
			this.coverageMatrix[i][index_max] = 1.0;
		}
	}
	
	public void getBSLoad() {
		
		this.bsLoadMatrix = new Double[coverageMatrix[0].length];
		Util.init(bsLoadMatrix);
		
		for (int j=0; j<this.coverageMatrix[0].length; j++) {
			for (int i=0; i<this.coverageMatrix.length; i++) {				
				bsLoadMatrix[j] += this.coverageMatrix[i][j];				
			}
		}
	}
	
	public void getInitialBitRate() {
	
		this.bitrateMatrix = new Double[this.sinr.length][this.sinr[0].length];
		for (int i=0; i<this.bitrateMatrix.length; i++) {
			for (int j=0; j<this.bitrateMatrix[0].length; j++) {
				this.bitrateMatrix[i][j] = (this.env.getBandwidth() * (Math.log10(1 + this.sinr[i][j])/Math.log10(2.0)))/1000000.0;
			}
		}
	}
	
	public void getFinalBitRate() {
		this.individualBitrateMatrix = new Double[this.bitrateMatrix.length];
		for (int i=0; i<this.coverageMatrix.length; i++) {
			for (int j=0; j<this.coverageMatrix[0].length; j++) {
				if (this.coverageMatrix[i][j] == 1.0) {
					this.individualBitrateMatrix[i] = (this.bitrateMatrix[i][j]/this.bsLoadMatrix[j]);
				}
			}
		}
		
		this.sumRate = 0.0;
		for (int i=0; i<this.individualBitrateMatrix.length; i++) {
			this.sumRate += this.individualBitrateMatrix[i];
		}
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

	public List<Point> getMacroPoints() {
		return macroPoints;
	}

	public void setMacroPoints(List<Point> macroPoints) {
		this.macroPoints = macroPoints;
	}

	public List<Point> getFemtoPoints() {
		return femtoPoints;
	}

	public void setFemtoPoints(List<Point> femtoPoints) {
		this.femtoPoints = femtoPoints;
	}

	public List<Point> getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(List<Point> userPoints) {
		this.userPoints = userPoints;
	}

	public Double[][] getDistanceMatrix() {
		return distanceMatrix;
	}

	public void setDistanceMatrix(Double[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}

	public Double[][] getSinr() {
		return sinr;
	}

	public void setSinr(Double[][] sinr) {
		this.sinr = sinr;
	}

	public Double[] getNumberUEsPerBS() {
		return numberUEsPerBS;
	}

	public void setNumberUEsPerBS(Double[] numberUEsPerBS) {
		this.numberUEsPerBS = numberUEsPerBS;
	}

	public Double[] getBsLoadMatrix() {
		return bsLoadMatrix;
	}

	public void setBsLoadMatrix(Double[] bsLoadMatrix) {
		this.bsLoadMatrix = bsLoadMatrix;
	}

	public Double[][] getBitrateMatrix() {
		return bitrateMatrix;
	}

	public void setBitrateMatrix(Double[][] bitrateMatrix) {
		this.bitrateMatrix = bitrateMatrix;
	}

	public Double[] getIndividualBitrateMatrix() {
		return individualBitrateMatrix;
	}

	public void setIndividualBitrateMatrix(Double[] individualBitrateMatrix) {
		this.individualBitrateMatrix = individualBitrateMatrix;
	}

	public Double getSumRate() {
		return sumRate;
	}

	public void setSumRate(Double sumRate) {
		this.sumRate = sumRate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCoverageMatrix(Double[][] coverageMatrix) {
		this.coverageMatrix = coverageMatrix;
	}
}