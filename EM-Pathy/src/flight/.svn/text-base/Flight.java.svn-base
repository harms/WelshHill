package flight;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import flight.passenger.Passenger;

public class Flight implements Flyable {
	public static final String C = "C";
	public static final String O = "O";
	
	private String no;
	private String status;
	private String dest;
	private String orig;
	private Calendar dep;
	private Calendar arr;

	private boolean hasBeenUsed;
	protected Calendar getDep() {
		return dep;
	}

	protected Calendar getArr() {
		return arr;
	}

	private boolean isFull;

	private Map<String, Passenger> passengersMap;

	public Flight(String no, String orig, String dest, String status, Calendar departureTime, Calendar arrivalTime) {
		this.no = no;
		this.orig = orig;
		this.dest = dest;
		this.status = status;
		this.dep = departureTime;
		this.arr = arrivalTime;
		
		this.hasBeenUsed = false;
		this.isFull = false;
		this.passengersMap =  new HashMap<String, Passenger>();
	}

	public void setStatus(String code) {
		this.status = code;
	}
	
	public String getStatus() {
		return status;
	}

	public Passenger getPassenger(String code) {
		return passengersMap.get(code);
	}

	public void put(String code, Passenger passenger) {
		passengersMap.put(code, passenger);
	}

	public String getNo() {
		return no;
	}

	public String getDest() {
		return dest;
	}

	public String getStart() {
		return orig;
	}

	public boolean used() {
		return hasBeenUsed;
	}
	
	public void setHasBeenUsed(boolean isNotUsedYet) {
		this.hasBeenUsed = isNotUsedYet;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public boolean isFull() {
		return isFull;
	}

	public Calendar getArrTime() {
		return arr;
	}
	
	public Calendar getDepTime() {
		return dep;
	}




}
