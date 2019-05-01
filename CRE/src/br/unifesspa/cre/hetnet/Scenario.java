package br.unifesspa.cre.hetnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.ga.NetworkElement;
import br.unifesspa.cre.util.Util;

public class Scenario implements Serializable{

	private static final long serialVersionUID = -1736505791936110187L;

	private CREEnv env;

	private List<BS> allBS;

	private List<UE> ue;

	private NetworkElement[][] network;

	private Double[] bias;

	private Double sumRate;

	private Double medianRate;

	/**
	 * Default Constructor
	 * @param env
	 */
	public Scenario(CREEnv env) {

		/* Parameters for scenario */
		this.env = env;

		/* Generate Macro, User and Femto locations from a Homogeneus Poisson Point Process */
		this.allBS = new ArrayList<BS>();

		List<Point> smallPoints = Util.getHPPP(this.env.getLambdaSmall(), this.env.getArea(), this.env.getHeightSmall());
		for (Point point : smallPoints) {
			BS small = new BS(BSType.Small, point, this.env.getPowerSmall(), this.env.getGainSmall(), 0.0);
			allBS.add(small);
		}

		this.bias = new Double[smallPoints.size()];

		this.ue = new ArrayList<UE>();
		List<Point> uePoints = Util.getHPPP(this.env.getLambdaUser(), this.env.getArea(), this.env.getHeightUser());
		for (Point point : uePoints) {
			UE ue = new UE(point, ApplicationProfile.DataBackup);
			this.ue.add(ue);
		}

		Point a = new Point(20.0, 20.0, this.env.getHeightMacro());
		Point b = new Point(80.0, 80.0, this.env.getHeightMacro());

		this.allBS.add(new BS(BSType.Macro, a, this.env.getPowerMacro(), this.env.getGainMacro(), 0.0));
		this.allBS.add(new BS(BSType.Macro, b, this.env.getPowerMacro(), this.env.getGainMacro(), 0.0));

		this.sumRate = 0.0;
		this.medianRate = 0.0;
		this.network = new NetworkElement[this.ue.size()][this.allBS.size()];
	}

	/**
	 * Initialize bias vector for Phase 2 (Static Bias)
	 * @param bias
	 */
	public void initBias(Double bias) {
		for (int i=0; i<this.bias.length; i++)
			this.bias[i] = bias;
	}
	
	public void evaluation() {
		this.getDistance();
		this.getInitialSINR();
		this.getCoverageMatrix();
		this.getBSLoad();
		this.getResourceBlockAllocation();
		this.getBitRate();
		this.getEvaluationMetrics();
	}

	/**
	 * Calculate the distance between Users and Femto BS's and Between Users and Macro BS's
	 */
	private void getDistance() {
		for (int i=0; i<this.ue.size(); i++) {
			UE u = this.ue.get(i);
			for (int j=0; j<this.allBS.size(); j++) {
				BS bs = this.allBS.get(j);	
				double d = Util.getDistance(u.getPoint(), bs.getPoint());
				NetworkElement n = new NetworkElement();
				n.setDistance(d);
				this.network[i][j] = n; 
			}
		}

		System.out.println("UES: "+this.ue.size());
		System.out.println("BSs: "+this.allBS.size());
		System.out.println("NE: ["+this.network.length+"]["+this.network[0].length+"]");

		System.out.println("Distance");
		Util.printD(this.network);
		System.out.println();

	}

	/**
	 * Calculates the downlink SINR from all BSs to all users
	 */
	private void getInitialSINR() {

		double bw = this.env.getBandwidth() * Math.pow(10.0, 6.0); 		
		double sigma2 = Math.pow(10.0,-3.0) * Math.pow(10.0, this.env.getNoisePower()/10.0);
		double totalThermalNoise = bw * sigma2;

		double sum = 0.0;

		for (int i=0; i<this.ue.size(); i++) {

			for (int j=0; j<this.allBS.size(); j++) {
				sum = 0.0;
				BS bs = this.allBS.get(j);
				double pr = bs.getPower() - Util.getPathLoss(bs.getType(), this.network[i][j].getDistance(), bs.getTxGain());
				double prLin =  Math.pow(10.0, -3.0) * Math.pow(10.0,(pr/10.0));

				for (int k=0; k<this.allBS.size(); k++) {
					BS b = this.allBS.get(k);
					if (!b.equals(bs)) {
						double prK = b.getPower() - Util.getPathLoss(b.getType(), this.network[i][k].getDistance(), b.getTxGain());
						double prLinK =  Math.pow(10.0, -3.0) * Math.pow(10.0,(prK/10.0));
						sum += prLinK;
					}
				}

				sum += totalThermalNoise;
				double bias = 0.0;
				if (bs.getType().equals(BSType.Small))
					bias = this.bias[j];
				
				double sinr = 10.0 * Math.log10(prLin/sum) + bias;
				this.network[i][j].setSinr(sinr);
			}			
		}

		System.out.println("SINR");
		Util.printS(this.network);
		System.out.println();
	}

	/**
	 * Calculates the coverege matrix
	 */
	private void getCoverageMatrix() {

		for (int i=0; i<this.network.length; i++) {
			NetworkElement[] nea = this.network[i];
			List<NetworkElement> nel = Arrays.asList(nea);
			NetworkElement max = Collections.max(nel);
			int indexMax = nel.indexOf(max);
			this.network[i][indexMax].setCoverageStatus(true);
			double sinr = this.network[i][indexMax].getSinr();
			this.ue.get(i).setBitsPerOFDMSymbol( MCS.getEfficiency(sinr) );
		}

		System.out.println("Coverage");
		Util.printC(this.network);
		System.out.println();
	}

	/**
	 * Calculates de BS Load
	 */
	private void getBSLoad() {
		for (int j=0; j<this.network[0].length; j++) {
			double counter = 0.0;
			for (int i=0; i<this.network.length; i++) {				
				if (this.network[i][j].getCoverageStatus().equals(true))
					counter++;
			}
			this.allBS.get(j).setLoad(counter);
		}

		System.out.println("LOAD");
		for (BS bs : allBS) {
			System.out.print(bs.getLoad()+" ");
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Performs the Resource Block Allocation Process for all BSs
	 */
	private void getResourceBlockAllocation() {

		double nRBsPerUE = 0.0;

		for (int j=0; j<this.network[0].length; j++) {
			BS bs = this.allBS.get(j);
			if (bs.getLoad() != 0 ) {
				nRBsPerUE = Math.floor(bs.getnRBs()/bs.getLoad());
				for (int i=0; i<this.network.length; i++) {
					if (this.network[i][j].getCoverageStatus().equals(true))
						this.ue.get(i).setnRB(nRBsPerUE);
				}
			}
		}
		
		System.out.println("RB Allocation");
		for (UE u: this.ue) {
			System.out.println( u.getnRB() );
		}
		System.out.println();
		
	}

	/**
	 * Calculates the UE birate in Mbps
	 */
	private void getBitRate() {
		
		double bitrate = (this.env.getnSubcarriers() * this.env.getnOFDMSymbols() * this.env.getSubframeDuration() * 1000.0);
		
		for (int i=0; i<this.ue.size(); i++) {
			double nRB = this.ue.get(i).getnRB();
			double bitsPerOFDMSymbol = this.ue.get(i).getBitsPerOFDMSymbol();
			this.ue.get(i).setBitrate((nRB * bitrate * bitsPerOFDMSymbol)/1000000.0);
		}
		
		System.out.println("bits per OFDM Symbol");
		for (UE u: this.ue) {
			System.out.println( u.getBitsPerOFDMSymbol() );
		}
		System.out.println();
		
		
		System.out.println("Bitrate");
		for (UE u: this.ue) {
			System.out.println( u.getBitrate() );
		}
		System.out.println();
		System.exit(0);
	}

	/**
	 * Calculates performance evaluation final metrics
	 */
	private void getEvaluationMetrics() {
		double sumRate = 0.0;
		double size = (double) this.ue.size();
		List<Double> bitrate = new ArrayList<Double>();
		for (UE u : this.ue) {
			bitrate.add(u.getBitrate());
			sumRate += u.getBitrate();
		}
		this.sumRate = sumRate;
		Collections.sort(bitrate);
		
		int index1, index2;
		if (size % 2 == 0) {
			index1 = (int) size/2;
			index2 = index1 - 1;
			this.medianRate = (bitrate.get(index1) + bitrate.get(index2))/2.0;
		}else {
			index1 = (int) Math.ceil(size/2.0);
			this.medianRate = bitrate.get(index1);
		}
	}

	public CREEnv getEnv() {
		return env;
	}

	public void setEnv(CREEnv env) {
		this.env = env;
	}

	public List<BS> getAllBS() {
		return allBS;
	}

	public void setAllBS(List<BS> allBS) {
		this.allBS = allBS;
	}

	public List<UE> getUe() {
		return ue;
	}

	public void setUe(List<UE> ue) {
		this.ue = ue;
	}

	public NetworkElement[][] getNetwork() {
		return network;
	}

	public void setNetwork(NetworkElement[][] network) {
		this.network = network;
	}

	public Double[] getBias() {
		return bias;
	}

	public void setBias(Double[] bias) {
		this.bias = bias;
	}

	public Double getSumRate() {
		return sumRate;
	}

	public void setSumRate(Double sumRate) {
		this.sumRate = sumRate;
	}

	public Double getMedianRate() {
		return medianRate;
	}

	public void setMedianRate(Double medianRate) {
		this.medianRate = medianRate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Scenario [env=" + env + ", allBS=" + allBS + ", ue=" + ue + ", network=" + Arrays.toString(network)
				+ ", bias=" + Arrays.toString(bias) + ", sumRate=" + sumRate + ", medianRate=" + medianRate + "]";
	}
}