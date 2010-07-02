package flight.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import flight.Flight;
import flight.FlightStatus;
import flight.Flyable;
import flight.Route;
import flight.passenger.Passenger;

public class FlightDataManagerAgentUtilProcessorClass {
	private Map<String, Flight> flightsMap;

	public FlightDataManagerAgentUtilProcessorClass() {
		flightsMap = new LinkedHashMap<String, Flight>();
	}

	public void put(String flightNumber, Flight flight) {
		flightsMap.put(flightNumber, flight);
	}
	
	protected List<Flyable> reRoute(Flyable original, Passenger schmuck)	{
		initializeFlightsMapToUnused();
		ArrayList<Flyable> routes = new ArrayList<Flyable>();
		Route foundRoute;
		while(true) {
			foundRoute = routeFound(original, routes);
			if (foundRoute == null) break;  
			routes.add(foundRoute);
		}
		return routes;		
	}
	 
	private void initializeFlightsMapToUnused(){
		for (Entry<String, Flight> flightEntry : flightsMap.entrySet()) {
			flightEntry.getValue().setHasBeenUsed(false);
		}
	}
	
	private Route routeFound(Flyable original, List<Flyable> routes) {
		List<Flight> legs = route(original.getStart(), original.getDest(), null);
		
		if (legs.size() > 0) {
			legs.get(nearestLegUsed(legs)).setHasBeenUsed(true);
			return new Route(legs);
		} else {
			return null;
		}
	}
	
	private int nearestLegUsed(List<Flight> legs) {
		return Math.min(3, legs.size())-1;
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
			if (isInvalid(candidate)) continue;
			
			if (candidate.getStart().equals(start)) return true;

			if (canConnect(start, candidate.getStart())) return true;
		}
		return false;
	}

	private boolean isInvalid(Flight candidate) {
		return candidate.isCancelled() || candidate.used() || candidate.isFull();
	}

	protected List<Flight> route(String start, String dest, Flight subsequent) {
		List<Flight> results = new ArrayList<Flight>();
		List<Flight> candidates = flightsTo(dest);
		Flight current;
		
		for (int i=0; i<candidates.size(); i++) {
			current = candidates.get(i);

			if (isInvalid(current)) continue;
			
			if (completeTrip(current, start, results)) break;
			
			if (hasEnoughTime(subsequent, current)){
				if (foundConnectingFlights(current, start, results)){
					break;
				} else {
					continue;
				}
			}
		}
		return results;
	}
	
	private boolean completeTrip(Flight current, String start,	List<Flight> results) {
		if (current.getStart().equals(start) ) {
			results.add(current);
			return true;
		} 
		return false;
	}


	private boolean foundConnectingFlights(Flight current, String start, List<Flight> results) {
		String newDest = current.getStart();
		List<Flight> legs = route(start, newDest, current);
		if (legs.size() > 0) {
			results.addAll(legs);
			results.add(current);
			return true;
		}
		return false;
	}


	private boolean hasEnoughTime(Flight toCatch, Flight toConnectFrom){	
		if ((toCatch == null)||(toConnectFrom == null)) return true; 
		return timeBetweenFlights(toCatch, toConnectFrom) >= 1;
	}
	
	private double timeBetweenFlights(Flight last, Flight current){
        double differenceInMs = last.getDepartureTime().getTimeInMillis() - current.getArrivalTime().getTimeInMillis();
        return (convertMsToHours(differenceInMs));
	}
	
    private double convertMsToHours(double Ms){
        return Ms / 3600000;
	}
	

}

