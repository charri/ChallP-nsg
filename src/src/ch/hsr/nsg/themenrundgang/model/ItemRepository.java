package ch.hsr.nsg.themenrundgang.model;

public interface ItemRepository {
	void insertOrUpdate(Item item);
	
	Item[] itemsForBeacon(Beacon beacon);
	
	Item[] itemsForSubject(Beacon[] beacons, Subject[] subject);
	
	Item itemById(int id);
	
}
