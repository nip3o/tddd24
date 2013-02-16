package se.niclasolofsson.stockwatch.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PopulationServiceAsync {
	void getPopulations(String[] names,  AsyncCallback<Country[]> callback);
	void addData(String data, AsyncCallback<Void> callback);
	void init(AsyncCallback<Void> callback);
}
