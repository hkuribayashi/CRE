package br.unifesspa.cre.hetnet;

public abstract class Entity {
	
	protected Double id;
	
	protected Point point;

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
}