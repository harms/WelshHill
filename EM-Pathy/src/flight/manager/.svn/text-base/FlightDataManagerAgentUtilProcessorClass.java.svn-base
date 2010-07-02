package flight.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import flight.Flight;
import flight.Flyable;
import flight.Route;
import flight.passenger.Passenger;

public class FlightDataManagerAgentUtilProcessorClass {
	private Map<String, Flight> flightsMap;

	public FlightDataManagerAgentUtilProcessorClass() {
		flightsMap = new LinkedHashMap<String, Flight>();
	}

	public void put(String no, Flight flight) {
		flightsMap.put(no, flight);
	}
	
	public List<Flyable> reRoute(Flyable original, Passenger schmuck)
	{
		for (Entry<String, Flight> flightEntry : flightsMap.entrySet()) {
			flightEntry.getValue().setHasBeenUsed(false);
		}
		
		ArrayList<Flyable> routes = new ArrayList<Flyable>();
		boolean available = true;
		
		while(available) {
			String finalDestination = original.getDest();
			String start = original.getStart();
			Route result = null;
			
			List<Flight> legs = route(start, finalDestination, null);
			
			if (legs.size() > 0) {
				if (legs.size() == 1) legs.get(0).setHasBeenUsed(true); 
				if (legs.size() == 2) legs.get(1).setHasBeenUsed(true);
				if (legs.size() >  2) legs.get(2).setHasBeenUsed(true);     
				
				result = new Route(legs);
				routes.add(result);
			} else {
				available = false;
			}
		}
		
		return routes;		
	}

	private List<Flight> flightsTo(String destination) {
		List<Flight> flightsWithCorrectDestination = new ArrayList<Flight>();

		for (Entry<String, Flight> flightEntry : flightsMap.entrySet()) {
			if (flightEntry.getValue().getDest().equals(destination)) flightsWithCorrectDestination.add(flightEntry.getValue());
		}

		return flightsWithCorrectDestination;
	}

	protected boolean canConnect(String start, String destination) {
		List<Flight> candidates = flightsTo(destination);

		for (Flight candidate : candidates) {
			if (candidate.getStatus().equals("C") || candidate.used() || candidate.isFull()) continue;
			if (candidate.getStart().equals(start)) return true;

			if (canConnect(start, candidate.getStart())) return true;
		}

		return false;
	}

	protected List<Flight> route(String start, String dest, Flight last) {
		List<Flight> results = new ArrayList<Flight>();
		List<Flight> candidates = flightsTo(dest);
		
		for (int i=0; i<candidates.size(); i++) {
			Flight current = candidates.get(i);

			if (current.getStatus().equals("C") || current.used() || current.isFull()) continue;

			if (current.getStart().equals(start) ) {
				results.add(current);
				return results;
			}
			
			String newDest = current.getStart();
			
			boolean notEnoughTime = false;
			if (last != null) { 
				double diffMs = ((last.getDepTime().getTimeInMillis() - current.getArrTime().getTimeInMillis()));
				double diffHours = diffMs/3600000;
				
				notEnoughTime = diffHours < 1;
			}
			
			if (notEnoughTime) continue;
			
			List<Flight> legs = route(start, newDest, current);
			if (legs.size() > 0) {
				results.addAll(legs);
				results.add(current);
				
				return results;
			}
		}
		
		return results;

	}

}
