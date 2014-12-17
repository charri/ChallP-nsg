package ch.hsr.nsg.themenrundgang.model;

public interface ItemRepository {
	void insertOrUpdate(Item item);
	
	Item[] itemsFor(Beacon beacon);

    Item[] itemsFor(Beacon beacon, Subject[] subject);

    Item[] itemsFor(Subject[] subjects);
	
	Item itemById(int id);
	
}
