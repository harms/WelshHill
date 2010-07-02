package flight;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import flight.passenger.Passenger;

public class Flight implements Flyable {
	
	private String number;
	private FlightStatus status;
	private String destination;
	private String origin;
	private Calendar departure;
	private Calendar arrival;

	private boolean hasBeenUsed;

	private boolean isFull;

	private Map<String, Passenger> passengersMap;

	public Flight(String flightIdentifier, String departureAirport, String arrivalAirport, FlightStatus status, Calendar departureTime, Calendar arrivalTime) {
		this.number = flightIdentifier;
		this.origin = departureAirport;
		this.destination = arrivalAirport;
		this.status = status;
		this.departure = departureTime;
		this.arrival = arrivalTime;
		
		this.hasBeenUsed = false;
		this.isFull = false;
		this.passengersMap =  new HashMap<String, Passenger>();
	}

	public void setStatus(FlightStatus code) {
		this.status = code;
	}
	
	public FlightStatus getStatus() {
		return status;
	}
	
	public boolean isCancelled(){
		return this.status.equals(FlightStatus.CANCELLED);
	}

	public Passenger getPassenger(String code) {
		return passengersMap.get(code);
	}

	public void put(String code, Passenger passenger) {
		passengersMap.put(code, passenger);
	}

	public String getNo() {
		return number;
	}

	public boolean used() {
		return hasBeenUsed;
	}
	
	public void setHasBeenUsed(boolean whetherUsed) {
		this.hasBeenUsed = whetherUsed;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public boolean isFull() {
		return isFull;
	}

	public Calendar getArrivalTime() {
		return arrival;
	}
	
	public Calendar getDepartureTime() {
		return departure;
	}

	public String getStart() {
		return origin;
	}

	public String getDest() {
		return destination;
	}


}
