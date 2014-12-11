package ch.hsr.nsg.themenrundgang.model;

public interface BeaconRepository {
	void insertOrUpdate(Beacon beacon);
	
	boolean hasBeacon(String beaconId);
	
	Beacon beaconById(String beaconId);
}
