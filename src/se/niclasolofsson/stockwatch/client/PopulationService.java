package se.niclasolofsson.stockwatch.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("populations")
public interface PopulationService extends RemoteService {
	Country[] getPopulations(String[] names) throws DelistedException;
	void addData(String data);
	void init();
}
