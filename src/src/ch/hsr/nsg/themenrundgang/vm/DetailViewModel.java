package ch.hsr.nsg.themenrundgang.vm;

import ch.hsr.nsg.themenrundgang.model.ItemRepository;

public class DetailViewModel extends AbstractViewModel {

	private ItemRepository repo;
	
	public DetailViewModel(ItemRepository repo) {
		this.repo = repo;
	}
	
}
