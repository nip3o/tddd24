package se.niclasolofsson.stockwatch.server;

import se.niclasolofsson.stockwatch.client.DelistedException;
import se.niclasolofsson.stockwatch.client.Country;
import se.niclasolofsson.stockwatch.client.PopulationService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PopulationServiceImpl extends RemoteServiceServlet implements
		PopulationService {
	private static final long serialVersionUID = 2L;
	
	private DataManager dm;
	
	@Override
	public void init() {
		dm = new DataManager();
		dm.connect();
		dm.create();
		dm.close();
	}

	@Override
	public Country[] getPopulations(String[] names) throws DelistedException {
		dm.connect();
		
		Country[] countries = new Country[names.length];
		
		for (int i = 0; i < names.length; i++) {
			int population = dm.getPopulation(names[i]);
			countries[i] = new Country(names[i], population);
		}
		
		dm.close();
		return countries;
	}

	@Override
	public void addData(String data) {
		dm.connect();
		dm.addPopulationsFromString(data);
		dm.close();
	}
}
