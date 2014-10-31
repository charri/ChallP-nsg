package ch.hsr.nsg.themenrundgang.test.repo;

import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;

public class MoqItemRepository implements ItemRepository {

	@Override
	public Item getItemByTitle(String title) {
		
		return null;
	}

}
