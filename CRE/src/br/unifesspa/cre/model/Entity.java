package br.unifesspa.cre.model;

public abstract class Entity {
	
	private Integer id;

	public Entity(Integer id) {
		super();
		this.id = id;
	}

	public Entity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}
}