package br.unifesspa.cre.hetnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BS implements Serializable{

	private static final long serialVersionUID = -4238233917654767955L;

	private Point point;
	
	private Double power;
	
	private Double txGain;
	
	private Double rxGain;
	
	private Double load;
	
	private BSType type;
	
	private List<UE> servedUsers;

	public BS(BSType type, Point point, Double power, Double txGain, Double rxGain) {
		super();
		this.type = type;
		this.point = point;
		this.power = power;
		this.txGain = txGain;
		this.rxGain = rxGain;
		this.servedUsers = new ArrayList<UE>();
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Double getTxGain() {
		return txGain;
	}

	public void setTxGain(Double txGain) {
		this.txGain = txGain;
	}

	public Double getRxGain() {
		return rxGain;
	}

	public void setRxGain(Double rxGain) {
		this.rxGain = rxGain;
	}

	public Double getLoad() {
		return load;
	}

	public void setLoad(Double load) {
		this.load = load;
	}

	public List<UE> getServedUsers() {
		return servedUsers;
	}

	public void setServedUsers(List<UE> servedUsers) {
		this.servedUsers = servedUsers;
	}

	public BSType getType() {
		return type;
	}

	public void setType(BSType type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
