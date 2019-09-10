package br.unifesspa.cre.hetnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.ga.NetworkElement;
import br.unifesspa.cre.util.Util;
import br.unifesspa.cre.view.Topology;

public class Scenario implements Serializable, Cloneable, Runnable{

	private static final long serialVersionUID = -1736505791936110187L;

	private CREEnv env;

	private List<BS> allBS;

	private List<UE> ue;

	private NetworkElement[][] network;

	private Double[] bias;

	private Double sumRate;

	private Double requiredRate;

	private Double medianRate;

	private Double uesServed;

	private Double servingBSs;


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
			UE ue = new UE(point, Util.getApplicationProfile());
			this.ue.add(ue);
		}

		Point a = new Point(200.0, 200.0, this.env.getHeightMacro());
		Point b = new Point(800.0, 800.0, this.env.getHeightMacro());

		this.allBS.add(new BS(BSType.Macro, a, this.env.getPowerMacro(), this.env.getGainMacro(), 0.0));
		this.allBS.add(new BS(BSType.Macro, b, this.env.getPowerMacro(), this.env.getGainMacro(), 0.0));

		this.sumRate = 0.0;
		this.medianRate = 0.0;
		this.requiredRate = 0.0;
		this.uesServed = 0.0;
		this.servingBSs = 0.0;
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

	/**
	 * Performs the entire simulatio
	 */
	public void evaluation() {
		this.getDistance();
		this.getBiasedSINR();
		this.getCoverageMatrix();
		this.getBSLoad();
		this.getResourceBlockAllocation();
		this.getBitRate();
		this.getEvaluationMetrics();
	}


	public void run() {
		this.evaluation();
	}

	public void evaluationEvolved() {
		this.getDistance();
		this.getBiasedSINR();
		this.getCoverageMatrix();
		this.getBSLoad();
		this.getResourceBlockAllocation2(AllocationStrategy.Proportional);
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
	}

	/**
	 * Calculates the downlink SINR from all BSs to all users
	 */
	private void getBiasedSINR() {

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
		}
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
	}

	/**
	 * Performs the Resource Block Allocation Process for all BSs
	 */
	private void getResourceBlockAllocation() {

		for (int i=0; i<this.network.length; i++) {
			for (int j=0; j<this.network[0].length; j++) {
				if (this.allBS.get(j).getType().equals(BSType.Small))
					this.network[i][j].setSinr((this.network[i][j].getSinr() - this.bias[j]));
				if (this.network[i][j].getCoverageStatus().equals(true)) {
					double bitsPerOFDMSymbol = MCS.getEfficiency(this.network[i][j].getSinr());
					this.ue.get(i).setBitsPerOFDMSymbol(bitsPerOFDMSymbol);
				}
			}
		}

		double nRBsPerUE = 0.0;
		for (int j=0; j<this.network[0].length; j++) {
			BS bs = this.allBS.get(j);
			if (bs.getLoad() != 0 ) {
				nRBsPerUE = Math.floor(bs.getnRBs()/bs.getLoad());
				if (nRBsPerUE < 1.0)
					nRBsPerUE = 1.0;
				for (int i=0; i<this.network.length; i++) {
					if (this.network[i][j].getCoverageStatus().equals(true))
						this.ue.get(i).setnRB(nRBsPerUE);
				}
			}
		}
	}

	private void getResourceBlockAllocation2(AllocationStrategy strategy) {

		for (int i=0; i<this.network.length; i++) {
			for (int j=0; j<this.network[0].length; j++) {
				if (this.allBS.get(j).getType().equals(BSType.Small))
					this.network[i][j].setSinr((this.network[i][j].getSinr() - this.bias[j]));
				if (this.network[i][j].getCoverageStatus().equals(true)) {
					double bitsPerOFDMSymbol = MCS.getEfficiency(this.network[i][j].getSinr());
					this.ue.get(i).setBitsPerOFDMSymbol(bitsPerOFDMSymbol);
				}
			}
		}

		if (strategy.equals(AllocationStrategy.Uniform)) {
			double nRBsPerUE = 0.0;
			double test = 0.0;
			for (int j=0; j<this.network[0].length; j++) {
				BS bs = this.allBS.get(j);
				if (bs.getLoad() != 0 ) {
					nRBsPerUE = Math.floor(bs.getnRBs()/bs.getLoad());
					if (nRBsPerUE < 1.0)
						nRBsPerUE = 1.0;
					for (int i=0; i<this.network.length; i++) {
						if (this.network[i][j].getCoverageStatus().equals(true)) {
							this.ue.get(i).setnRB(nRBsPerUE);
							test++;
						}
					}
					if (test > 100.0) {
						System.out.println("BS Overloaded "+j+": "+test);
					}
				}
			}
		}else {
			Double[] counter = new Double[this.network[0].length];
			Util.init(counter);

			double nRBsPerUE = 0.0;
			for (int j=0; j<this.network[0].length; j++) {
				BS bs = this.allBS.get(j);
				if (bs.getLoad() != 0 ) {
					nRBsPerUE = 1.0;
					for (int i=0; i<this.network.length; i++) {
						if (this.network[i][j].getCoverageStatus().equals(true)) {
							this.ue.get(i).setnRB(nRBsPerUE);
							counter[j]++;
						}
					}
				}
			}

			for (int j=0; j<this.network[0].length; j++) {
				double test = 0.0;

				BS bs = this.allBS.get(j);
				if (bs.getLoad() != 0 || ((bs.getnRBs() - counter[j]) > 0)) {
					double availableRBs = bs.getnRBs() - counter[j];

					double requiredRate = 0.0;

					for (int i=0; i<this.network.length; i++) {
						if (this.network[i][j].getCoverageStatus().equals(true)) {
							double temp = this.ue.get(i).getProfile().getBandwidth() * this.ue.get(i).getProfile().getCompressionFactor();
							requiredRate += temp;
						}						
					}

					for (int i=0; i<this.network.length; i++) {
						if (this.network[i][j].getCoverageStatus().equals(true)) {
							double ueRequiredRate = (this.ue.get(i).getProfile().getBandwidth() * this.ue.get(i).getProfile().getCompressionFactor());
							this.ue.get(i).setnRB( this.ue.get(i).getnRB() + Math.floor(availableRBs * (ueRequiredRate/requiredRate)));

							test += this.ue.get(i).getnRB();
						}
					}

					if (test > 100.0)
						System.out.println("BS "+j+" Overloaded: "+test+" RBs");

				}
			}

		}

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
	}

	/**
	 * Calculates performance evaluation final metrics
	 */
	private void getEvaluationMetrics() {
		double sumRate = 0.0;
		List<Double> bitrate = new ArrayList<Double>();
		for (UE u : this.ue) {
			bitrate.add(u.getBitrate());
			sumRate += u.getBitrate();
		}
		this.sumRate = sumRate;
		Collections.sort(bitrate);
		this.medianRate = Util.getMedian(bitrate);

		this.requiredRate = 0.0;
		this.uesServed = 0.0;
		for (UE ue: this.ue) {
			ApplicationProfile profile = ue.getProfile();;
			double rRate = (profile.getBandwidth() * profile.getCompressionFactor());
			this.requiredRate += rRate;
			if (ue.getBitrate() >= rRate)
				this.uesServed++;
		}

		double counter = 0.0;
		for(BS bs: this.allBS) {
			if (bs.getLoad() != 0)
				counter++;
		}
		this.servingBSs = counter;
	}

	public void paitTopology() {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 1000, 1000);
		window.getContentPane().add(new Topology(this));
		window.setVisible(true);
	}

	public Scenario clone() {
		try {
			Scenario result = (Scenario) super.clone();
			NetworkElement[][] ne = new NetworkElement[this.network.length][this.network[0].length];
			for (int i=0; i<ne.length; i++) {
				ne[i] = this.network[i].clone();
			}

			result.setNetwork(ne);
			return result;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
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

	public Double getRequiredRate() {
		return requiredRate;
	}

	public void setRequiredRate(Double requiredRate) {
		this.requiredRate = requiredRate;
	}

	public Double getUesServed() {
		return uesServed;
	}

	public void setUesServed(Double uesServed) {
		this.uesServed = uesServed;
	}

	public Double getServingBSs() {
		return servingBSs;
	}

	public void setServingBSs(Double servingBSs) {
		this.servingBSs = servingBSs;
	}

	@Override
	public String toString() {
		return "Scenario [env=" + env + ", allBS=" + allBS + ", ue=" + ue + ", network=" + Arrays.toString(network)
		+ ", bias=" + Arrays.toString(bias) + ", sumRate=" + sumRate + ", requiredRate=" + requiredRate
		+ ", medianRate=" + medianRate + ", uesServed=" + uesServed + ", servingBSs=" + servingBSs + "]";
	}
}