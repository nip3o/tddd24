package se.niclasolofsson.stockwatch.client;

import java.io.Serializable;

public class Population implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private double amount;
	private double old_amount;

	public Population() {
	}

	public Population(String name, double price, double change) {
		this.name = name;
		this.amount = price;
		this.old_amount = change;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public double getChange() {
		return old_amount - amount;
	}

	public void setAmount(double amount) {
		this.old_amount = this.amount;
		this.amount = amount;
	}

	public double getChangePercent() {
		return 100.0 * this.getChange() / this.getAmount();
	}
}
