package se.niclasolofsson.stockwatch.server;

import java.util.Random;

import se.niclasolofsson.stockwatch.client.DelistedException;
import se.niclasolofsson.stockwatch.client.Country;
import se.niclasolofsson.stockwatch.client.PopulationService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PopulationServiceImpl extends RemoteServiceServlet implements
		PopulationService {
	private static final long serialVersionUID = 1L;
	final int MAX_PRICE = 10000;

	@Override
	public Country[] getPopulations(String[] names) throws DelistedException {
		Random rnd = new Random();
		Country[] populations = new Country[names.length];
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals("Err")) {
				throw new DelistedException("Err");
			}

			int population = rnd.nextInt() * MAX_PRICE;

			populations[i] = new Country(names[i], population);
		}
		return populations;
	}
}
