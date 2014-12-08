package ch.hsr.nsg.themenrundgang.model;

public interface AdditionRepository {
	void insertOrUpdate(Addition addition);
	
	Addition[] additionByItem(Item item);
}
