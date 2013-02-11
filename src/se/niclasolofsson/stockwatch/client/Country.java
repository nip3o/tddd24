package se.niclasolofsson.stockwatch.client;

import java.io.Serializable;

public class Country implements Serializable {
	private static final long serialVersionUID = 2L;
	private String name;
	private int population;

	public Country() {
	}

	public Country(String name, int population) {
		this.name = name;
		this.population = population;
	}

	public Country(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}
