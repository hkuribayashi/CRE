package br.unifesspa.cre.hetnet;

public enum ApplicationProfile {
	
	VirtualReality(10.0,100.0,0.6), 
	FactoryAutomation(20.0,1.0,0.8), 
	DataBackup(1000.0,1.0,1.0), 
	SmartGrid(50.0,0.4,0.03), 
	SmartHome(60.0,0.001,1.0), 
	Medical(40.0,2.0,0.2), 
	EnvironmentalMonitoring(1000.0,1.0,0.1), 
	TactileInternet(1.0,120.0,0.8);

	private Double latency;
	
	private Double bandwidth;
	
	private Double compressionFactor;
    
    ApplicationProfile(Double latency, Double bandwidth, Double compressionFactor) {
        this.latency = latency;
        this.bandwidth = bandwidth;
        this.compressionFactor = compressionFactor;
    }

	public Double getLatency() {
		return latency;
	}

	public void setLatency(Double latency) {
		this.latency = latency;
	}

	public Double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public Double getCompressionFactor() {
		return compressionFactor;
	}

	public void setCompressionFactor(Double compressionFactor) {
		this.compressionFactor = compressionFactor;
	}
}