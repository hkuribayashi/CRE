package br.unifesspa.cre.hetnet;

import java.io.Serializable;

public class BS implements Serializable, Cloneable{

	private static final long serialVersionUID = -4238233917654767955L;

	private Point point;
	
	private Double power;
	
	private Double txGain;
	
	private Double rxGain;
	
	private Double load;
	
	private BSType type;
	
	private Double nRBs;

	public BS(BSType type, Point point, Double power, Double txGain, Double rxGain) {
		super();
		this.type = type;
		this.point = point;
		this.power = power;
		this.txGain = txGain;
		this.rxGain = rxGain;
		this.nRBs = 100.0;
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

	public BSType getType() {
		return type;
	}

	public void setType(BSType type) {
		this.type = type;
	}

	public Double getnRBs() {
		return nRBs;
	}

	public void setnRBs(Double nRBs) {
		this.nRBs = nRBs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public Object clone() {
		try {
			BS test = (BS) super.clone();
			test.setPoint(new Point(this.point.getX(), this.point.getY(), this.point.getZ()));
			return test;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return this;
		}
	}
	
}
