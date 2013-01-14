package se.niclasolofsson.stockwatch.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("populations")
public interface PopulationService extends RemoteService {
	Population[] getPopulations(String[] names) throws DelistedException;
}
