package ch.hsr.nsg.themenrundgang.monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FakeBeaconMonitor implements BeaconMonitor{
	
	private final static int SCAN_INTERVALL = 2500;
	private final static int MIN_SCANS_FOR_BEACONS_UPDATE = 1;
	private final static int MAX_SCANS_FOR_BEACONS_UPDATE = 2;
	private final static int MIN_BEACONS_UPDATES_FOR_LOCATION_CHANGE = 2;
	private final static int MAX_BEACONS_UPDATES_FOR_LOCATION_CHANGE = 3;
	
	private final static int TOTAL_NOF_LOCATIONS = 6;
	private final static int MIN_NOF_LOCATIONS = 1;
	private final static int MAX_NOF_LOCATIONS = 2;
	private final static int MIN_NOF_BEACONS = 1;
	private final static int MAX_NOF_BEACONS = 5;
	private final static double ENTERED_LOCATION_WEIGHT	= 0.6;
	
	private ArrayList<Beacon> monitoredBeacons = new ArrayList<Beacon>();
	private ArrayList<Integer> monitoredLocationIds = new ArrayList<Integer>();
	private MonitoringListenerCallback listener;
	private boolean running = false;
	
	public static void main(String[] args) {
		new FakeBeaconMonitor(1, 1).startMonitoring();
	}
	
	public FakeBeaconMonitor(Integer startLocationId, Integer startBeaconId) {
		if (startLocationId != null) {
			if (startBeaconId == null) {
				monitoredLocationIds.add(startLocationId);
			} else {
				monitoredBeacons.add(new Beacon(startLocationId, startBeaconId));
				monitoredLocationIds.add(startLocationId);
			}
		}
	}
	
	@Override
	public void setMonitoringListener(MonitoringListenerCallback listener) {
		this.listener = listener;
	}
	
	@Override
	public void startMonitoring() {
		running = true;
		monitor();
	}
	
	@Override
	public void stop() {
		running = false;
	}
	
	private void monitor() {
		int scansForBeaconsUpdate;
		int scansForLocationUpdate;
		
		while (running) {
			scansForBeaconsUpdate = getRandomNumberInRange(MIN_SCANS_FOR_BEACONS_UPDATE, MAX_SCANS_FOR_BEACONS_UPDATE);
			scansForLocationUpdate = scansForBeaconsUpdate * 
					Math.max(MIN_BEACONS_UPDATES_FOR_LOCATION_CHANGE, MAX_BEACONS_UPDATES_FOR_LOCATION_CHANGE);
			monitoredLocationIds = getMonitoredLocationIds();
			
			for (int i = 1; (running && i <= scansForLocationUpdate); i++) {
				if ((i % scansForLocationUpdate) == 0)
					updateLocations();
				if ((i % scansForBeaconsUpdate) == 0)
					updateBeacons();
				
				try {
					Thread.sleep(SCAN_INTERVALL);
				} catch (InterruptedException e) {
					e.printStackTrace();
					monitor();
				}
			}
		}
	}
	
	private void updateLocations() {
		// ArrayList detectedLocationIds mit den neuen Locations
		// detectedLocationIds auf monitoredBeacons anwenden
		// monitoredLocationIds = getMonitoredLocationIds();
		int nofDetectedLocations = getRandomNumberInRange(MIN_NOF_LOCATIONS, MAX_NOF_LOCATIONS);
		ArrayList<Integer> detectedLocationIds = new ArrayList<Integer>();
		
		if (nofDetectedLocations > monitoredLocationIds.size())
		
		while (detectedLocationIds.size() < nofDetectedLocations) {
			int locationId = getRandomNumberInRange(1, TOTAL_NOF_LOCATIONS);
			if (!detectedLocationIds.contains(locationId))
				detectedLocationIds.add(locationId);
		}
	}
	
	private void updateBeacons() {
		int nofDetectedBeacons = getRandomNumberInRange(MIN_NOF_BEACONS, MAX_NOF_BEACONS);
		if (nofDetectedBeacons == 0) {
			monitoredBeacons = new ArrayList<Beacon>();
		} else if (monitoredLocationIds.size() == 1) {
			updateBeaconsInEnteredLocation(nofDetectedBeacons);
		} else if (monitoredLocationIds.size() > 1) {
			int beaconsInEnteredLocation = (int) Math.ceil(nofDetectedBeacons * ENTERED_LOCATION_WEIGHT);
			updateBeaconsInEnteredLocation(beaconsInEnteredLocation);
			updateBeaconsOutsideLocation(nofDetectedBeacons - beaconsInEnteredLocation);
		}
	}
	
	private void updateBeaconsInEnteredLocation(int beaconsInEnteredLocation) {
		int enteredLocationId = monitoredLocationIds.get(0);
		int beaconId;
		
		while (getNofBeaconsInLocation(enteredLocationId) != beaconsInEnteredLocation) {
			beaconId = getRandomNumberInRange(0, MAX_NOF_BEACONS - 1);
			Beacon beacon = new Beacon(enteredLocationId, beaconId);
			if (getNofBeaconsInLocation(enteredLocationId) < beaconsInEnteredLocation) {
				if (!monitoredBeacons.contains(beacon)) {
					monitoredBeacons.add(new Beacon(enteredLocationId, beaconId));
					System.out.println("new Beacon: " + enteredLocationId + "-" + beaconId);
				}
			} else if (getNofBeaconsInLocation(enteredLocationId) > beaconsInEnteredLocation) {
				if (monitoredBeacons.contains(beacon)) {
					monitoredBeacons.remove(monitoredBeacons.indexOf(beacon));
					System.out.println("new Beacon: " + enteredLocationId + "-" + beaconId);
				} 
			}
		}
	}
	
	private void updateBeaconsOutsideLocation(int nofDetectedBeacons) {
		int locationId;
		int beaconId;
		
		int locationIndex = 0;
		while (nofDetectedBeacons != monitoredBeacons.size()) {
			locationId = monitoredLocationIds.get(locationIndex + 1);
			beaconId = getRandomNumberInRange(0, MAX_NOF_BEACONS - 1);
			Beacon beacon = new Beacon(locationId, beaconId);
			if (nofDetectedBeacons < monitoredBeacons.size()) {	
				while (monitoredBeacons.contains(beacon)) {
					beaconId = getRandomNumberInRange(0, MAX_NOF_BEACONS - 1);
					beacon = new Beacon(locationId, beaconId);
				}
				monitoredBeacons.add(beacon);
				System.out.println("new Beacon: " + locationId + "-" + beaconId);
			} else if (nofDetectedBeacons < monitoredBeacons.size()) {
				while (!monitoredBeacons.contains(beacon)) {
					beaconId = getRandomNumberInRange(0, MAX_NOF_BEACONS - 1);
					beacon = new Beacon(locationId, beaconId);
				}
				monitoredBeacons.remove(monitoredBeacons.indexOf(beacon));
				System.out.println("lost Beacon: " + locationId + "-" + beaconId);
			}
			locationIndex++;
			locationIndex = (locationIndex % (monitoredLocationIds.size() - 1));
		}
	}
	
	private ArrayList<Integer> getMonitoredLocationIds() {
		ArrayList<Integer> monitoredLocationIds = new ArrayList<Integer>();
		for (Beacon b : monitoredBeacons) {
			if (!monitoredLocationIds.contains(b.major))
				monitoredLocationIds.add(b.major);
		}
		Collections.sort(monitoredLocationIds, new Comparator<Integer>(){
		     public int compare(Integer l1, Integer l2){
		    	 int amountL1 = 0;
		    	 int amountL2 = 0;
		    	 for (int i = 0; i < monitoredBeacons.size(); i++) {
		    		 if (monitoredBeacons.get(i).major == l1)
		    			 amountL1++;
		    		 if (monitoredBeacons.get(i).major == l2)
		    			 amountL2++;
		    	 }
		         if(amountL1 == amountL2)
		             return 0;
		         return amountL1 < amountL2 ? -1 : 1;
		     }
		});
		return monitoredLocationIds;
	}
	
	private int getNofBeaconsInLocation(int locationId) {
		int nofBeacons = 0;
		for (int i = 0; i < monitoredBeacons.size(); i++) {
			if (monitoredBeacons.get(i).major == locationId)
				nofBeacons++;
   	 	}
		return nofBeacons;
	}
	
	private int getRandomNumberInRange(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	private Integer getRandomNumberInList(ArrayList<Integer> set) {
		return set.get((int) (Math.random() * set.size()));
	}
	
	private class Beacon {
		private Integer major, minor;
		
		Beacon(Integer major, Integer minor) {
			this.major = major;
			this.minor = minor;
		}
		
		@Override
		public boolean equals(Object other) {
			if (getClass() == other.getClass()) {
				return (this.major == ((Beacon) other).major
						&& this.minor == ((Beacon) other).minor);
		    }
			return false;
		}
	}
}
