package br.unifesspa.cre.hetnet;

public class UE {

	private Point point;
	
	private ApplicationProfile profile;
	
	private Double bitrate;

	public UE() {
		super();
	}

	public UE(Point point, ApplicationProfile profile) {
		super();
		this.point = point;
		this.profile = profile;
		this.bitrate = 0.0;
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
}
