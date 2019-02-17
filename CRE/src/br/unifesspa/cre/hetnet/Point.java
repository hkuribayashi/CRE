package br.unifesspa.cre.hetnet;

import java.io.Serializable;

public class Point implements Serializable{

	private static final long serialVersionUID = 3140977350440260235L;

	private Double x;
	
	private Double y;
	
	private Double z;
	
	public Point() {
		super();
	}

	public Point(Double x, Double y, Double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
