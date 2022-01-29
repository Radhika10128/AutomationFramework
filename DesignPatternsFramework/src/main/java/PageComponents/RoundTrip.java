package PageComponents;

import AbstractComponents.SearchFlightAvail;

public class RoundTrip implements SearchFlightAvail {

	@Override
	public void checkAvailibility(String origin, String destination) {
		System.out.println("I am inside roundTrip");
	}

}
