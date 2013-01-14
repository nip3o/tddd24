package se.niclasolofsson.stockwatch.server;

import java.util.Random;

import se.niclasolofsson.stockwatch.client.DelistedException;
import se.niclasolofsson.stockwatch.client.Population;
import se.niclasolofsson.stockwatch.client.PopulationService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PopulationServiceImpl extends RemoteServiceServlet implements
		PopulationService {
	private static final long serialVersionUID = 1L;
	final double MAX_PRICE = 100.0; // 100.00 kr
	final double MAX_PRICE_CHANGE = 0.02; // +/- 2%

	@Override
	public Population[] getPopulations(String[] names) throws DelistedException {
		Random rnd = new Random();
		Population[] populations = new Population[names.length];
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals("Err")) {
				throw new DelistedException("Err");
			}

			double price = rnd.nextDouble() * MAX_PRICE;
			double change = price * MAX_PRICE_CHANGE
					* (rnd.nextDouble() * 2.0 - 1.0);

			populations[i] = new Population(names[i], price, change);
		}
		return populations;
	}
}
