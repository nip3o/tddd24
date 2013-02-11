package se.niclasolofsson.stockwatch.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CountryDraggable extends Label {
	private Country country;
	
	CountryDraggable(String text, Country country) {
		super(text);
		this.country = country;
	}
	
	public Country getCountry() {
		return this.country;
	}
	
	public Widget getWidget() {
		// JDK 1.5 String.format FTW...
		return new HTML(this.country.getName() + " (" + this.country.getPopulation() + " inhabitants)");
	}
}
