package se.niclasolofsson.stockwatch.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PopulationServiceAsync {
	void getPopulations(String[] names,  AsyncCallback<Population[]> callback);
}
