package se.niclasolofsson.stockwatch.client;

import java.io.Serializable;

public class DelistedException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;

	public DelistedException() {
	}

	public DelistedException(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return this.name;
	}
}
