package br.unifesspa.cre.hetnet;

public class UE {

	private Point point;
	
	private ApplicationProfile profile;
	
	private Double bitrate;
	
	private Double nRB;
	
	private Double bitsPerOFDMSymbol;

	public UE() {
		super();
	}

	public UE(Point point, ApplicationProfile profile) {
		super();
		this.point = point;
		this.profile = profile;
		this.bitrate = 0.0;
		this.nRB = 0.0;
		this.bitsPerOFDMSymbol = 2.0;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public ApplicationProfile getProfile() {
		return profile;
	}

	public void setProfile(ApplicationProfile profile) {
		this.profile = profile;
	}

	public Double getBitrate() {
		return bitrate;
	}

	public void setBitrate(Double bitrate) {
		this.bitrate = bitrate;
	}

	public Double getnRB() {
		return nRB;
	}

	public void setnRB(Double nRB) {
		this.nRB = nRB;
	}

	public Double getBitsPerOFDMSymbol() {
		return bitsPerOFDMSymbol;
	}

	public void setBitsPerOFDMSymbol(Double bitsPerOFDMSymbol) {
		this.bitsPerOFDMSymbol = bitsPerOFDMSymbol;
	}
}
